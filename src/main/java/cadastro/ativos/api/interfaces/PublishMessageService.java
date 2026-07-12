package cadastro.ativos.api.interfaces;

import cadastro.ativos.api.models.Ativo;
import org.springframework.http.HttpMethod;

public interface PublishMessageService{
    void publishMessage(Ativo ativo, HttpMethod httpMethod);
}
