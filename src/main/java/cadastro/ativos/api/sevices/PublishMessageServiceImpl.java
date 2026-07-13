package cadastro.ativos.api.sevices;

import cadastro.ativos.api.interfaces.PublishMessageService;
import cadastro.ativos.api.models.Ativo;
import cadastro.ativos.api.models.MessageAtivoAlterado;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PublishMessageServiceImpl implements PublishMessageService{
    private Logger log = LoggerFactory.getLogger(PublishMessageServiceImpl.class);

    @Value("${spring.cloud.aws.sqs.queue.url}")
    private String queueUrl;

    @Autowired
    private SqsTemplate sqsTemplate;

    @Override
    public void publishMessage(Ativo ativo, HttpMethod httpMethod) {
        MessageAtivoAlterado message = createMessage(ativo);
        log.info("Enviando mensagem para fila Ativo Alterado após um {} do ativo {}", httpMethod, ativo.getCodigo());
        try{
            sqsTemplate.send(to -> to
                    .queue(queueUrl)
                    .payload(message)
                    .header("event-type", httpMethod.name())
                    .header("message-group-id", ativo.getCodigo())
                    .header("message-deduplication-id", UUID.randomUUID().toString())
            );
        } catch (Exception e) {
            String messageError = String.format("Erro ao enviar mensagem para fila Ativo Alterado: %s", e.getMessage());
            log.error(messageError);
            throw new RuntimeException(messageError);
        }
    }

    private MessageAtivoAlterado createMessage(Ativo ativo) {
        return new MessageAtivoAlterado(
                ativo.getCodigo(),
                ativo.getNome(),
                ativo.getValorBase(),
                ativo.getAptaNegociacao()
        );
    }
}
