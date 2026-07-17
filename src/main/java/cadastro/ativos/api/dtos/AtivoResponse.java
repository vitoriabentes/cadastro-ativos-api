package cadastro.ativos.api.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AtivoResponse(
        String codigo,
        String nome,
        BigDecimal valorBase,
        BigDecimal indexador,
        Boolean aptaNegociacao,
        int quantidade,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
){}
