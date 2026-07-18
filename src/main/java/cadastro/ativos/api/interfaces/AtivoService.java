package cadastro.ativos.api.interfaces;

import cadastro.ativos.api.dtos.AtivoRequest;
import cadastro.ativos.api.dtos.AtivoRequestUpdate;
import cadastro.ativos.api.dtos.AtivoResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AtivoService {
    AtivoResponse create(AtivoRequest request);
    AtivoResponse update(String codigo, AtivoRequestUpdate request);
    void deactivate(String codigo);
    List<AtivoResponse> findAll();
    AtivoResponse findByCode(String code);
}
