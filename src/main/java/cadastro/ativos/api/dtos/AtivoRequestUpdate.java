package cadastro.ativos.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AtivoRequestUpdate(
        @NotBlank(message = "Nome do ativo é obrigatório")
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        String nome,

        @NotNull(message = "Valor Base é obrigatório")
        @Positive(message = "Valor Base deve ser positivo")
        BigDecimal valorBase,

        @NotNull(message = "Indexador é obrigatório")
        BigDecimal indexador,

        @NotNull(message = "Apta à Negociação é obrigatória")
        Boolean aptaNegociacao,

        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser positiva")
        int quantidade
) {}
