package cadastro.ativos.api.interfaces;

import cadastro.ativos.api.models.Ativo;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public interface PublishMessageService{
    void publishMessage(Ativo ativo, HttpMethod httpMethod);
}
