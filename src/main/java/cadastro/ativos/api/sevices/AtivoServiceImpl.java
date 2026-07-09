package cadastro.ativos.api.sevices;

import cadastro.ativos.api.dtos.AtivoRequest;
import cadastro.ativos.api.dtos.AtivoResponse;
import cadastro.ativos.api.exceptions.AtivoNotFoundException;
import cadastro.ativos.api.interfaces.AtivoRepository;
import cadastro.ativos.api.interfaces.AtivoService;
import cadastro.ativos.api.models.Ativo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtivoServiceImpl implements AtivoService {
    private static final Logger log = LoggerFactory.getLogger(AtivoServiceImpl.class);

    @Autowired
    private AtivoRepository ativoRepository;

    @Override
    public AtivoResponse create(AtivoRequest request) {
        if(ativoRepository.findByCode(request.codigo()).isPresent()){
            log.warn("Tentativa de criação de ativo com código duplicado: {}", request.codigo());
            throw new RuntimeException("Ativo já cadastrado na base de dados.");
        }

        Ativo ativo = mapToAtivo(request);
        return mapToAtivoResponse(ativoRepository.save(ativo));
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
    public AtivoResponse update(String codigo, AtivoRequest request) {
        Ativo ativo = ativoRepository.findByCode(codigo).orElseThrow(() -> new AtivoNotFoundException("Ativo não encontrado."));
        ativo.setNome(request.nome());
        ativo.setValorBase(request.valorBase());
        ativo.setIndexador(request.indexador());
        ativo.setAptaNegociacao(request.aptaNegociacao());
        ativo.setQuantidade(request.quantidade());

        return mapToAtivoResponse(ativoRepository.update(ativo));
    }

    @Override
    public AtivoResponse delete(String codigo) {
        Ativo ativo = ativoRepository.findByCode(codigo).orElseThrow(() -> new AtivoNotFoundException("Ativo não encontrado."));
        ativoRepository.delete(ativo);
        return mapToAtivoResponse(ativo);
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
