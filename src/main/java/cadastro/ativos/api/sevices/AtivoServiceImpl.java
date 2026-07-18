package cadastro.ativos.api.sevices;

import cadastro.ativos.api.dtos.AtivoRequest;
import cadastro.ativos.api.dtos.AtivoRequestUpdate;
import cadastro.ativos.api.dtos.AtivoResponse;
import cadastro.ativos.api.exceptions.AtivoNotFoundException;
import cadastro.ativos.api.interfaces.AtivoRepository;
import cadastro.ativos.api.interfaces.AtivoService;
import cadastro.ativos.api.interfaces.PublishMessageService;
import cadastro.ativos.api.models.Ativo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtivoServiceImpl implements AtivoService {
    private static final Logger log = LoggerFactory.getLogger(AtivoServiceImpl.class);

    @Autowired
    private AtivoRepository ativoRepository;

    @Autowired
    private PublishMessageService publishMessageService;

    @Override
    public AtivoResponse create(AtivoRequest request) {
        if(ativoRepository.findByCode(request.codigo()).isPresent()){
            log.warn("Tentativa de criação de ativo com código duplicado: {}", request.codigo());
            throw new RuntimeException("Ativo já cadastrado na base de dados.");
        }

        Ativo ativo = ativoRepository.save( mapToAtivo(request));
        publishMessageService.publishMessage(ativo, HttpMethod.POST);
        return mapToAtivoResponse(ativo);
    }

    @Override
    public List<AtivoResponse> findAll() {
        return ativoRepository.findAll().stream()
                .map(this::mapToAtivoResponse).toList();
    }

    @Override
    public AtivoResponse findByCode(String code) {
        return ativoRepository.findByCode(code).map(this::mapToAtivoResponse).orElseThrow(() -> new AtivoNotFoundException("Ativo não encontrado."));
    }

    @Override
    public AtivoResponse update(String codigo, AtivoRequestUpdate request) {
        Ativo ativo = ativoRepository.findByCode(codigo).orElseThrow(() -> new AtivoNotFoundException("Ativo não encontrado."));
        ativo.setNome(request.nome());
        ativo.setValorBase(request.valorBase());
        ativo.setIndexador(request.indexador());
        ativo.setAptaNegociacao(request.aptaNegociacao());
        ativo.setQuantidade(request.quantidade());

        Ativo updatedAtivo = ativoRepository.update(ativo);
        publishMessageService.publishMessage(updatedAtivo, HttpMethod.PUT);
        return mapToAtivoResponse(updatedAtivo);
    }

    @Override
    public void deactivate(String codigo) {
        Ativo ativo = ativoRepository.findByCode(codigo).orElseThrow(() -> new AtivoNotFoundException("Ativo não encontrado."));
        ativo = ativoRepository.deactivate(ativo);
        publishMessageService.publishMessage(ativo, HttpMethod.DELETE);
    }

    private Ativo mapToAtivo(AtivoRequest request) {
        return new Ativo(
                request.codigo(),
                request.nome(),
                request.valorBase(),
                request.indexador(),
                request.aptaNegociacao(),
                request.quantidade()
        );
    }

    private AtivoResponse mapToAtivoResponse(Ativo ativo) {
        return new AtivoResponse(
                ativo.getCodigo(),
                ativo.getNome(),
                ativo.getValorBase(),
                ativo.getIndexador(),
                ativo.getAptaNegociacao(),
                ativo.getQuantidade(),
                ativo.getDataCriacao(),
                ativo.getDataAtualizacao()
        );
    }
}
