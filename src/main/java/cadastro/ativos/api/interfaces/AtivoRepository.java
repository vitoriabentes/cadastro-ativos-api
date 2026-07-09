package cadastro.ativos.api.interfaces;

import cadastro.ativos.api.models.Ativo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface AtivoRepository{
    Optional<Ativo> findByCode(String codigo);
    List<Ativo> findAll();
    Ativo update(Ativo ativo);
    Ativo save(Ativo ativo);
    void delete(Ativo ativo);
}
