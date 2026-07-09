package cadastro.ativos.api.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record AtivoRequest(
        @NotBlank(message = "Código do ativo é obrigatório")
        @Pattern(
                regexp = "^[A-Z]{4,5}[0-9]$",
                message = "Código deve seguir o padrão de ações brasileiras (ex: PETR3, VALE3, BBAS3)"
        )
        String codigo,

        @NotBlank(message = "Nome do ativo é obrigatório")
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        String nome,

        @NotNull(message = "Valor Base é obrigatório")
        @Positive(message = "Valor Base deve ser positivo")
        BigDecimal valorBase,

        @NotNull(message = "Indexador é obrigatório")
        int indexador,

        @NotNull(message = "Apta à Negociação é obrigatória")
        Boolean aptaNegociacao,

        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser positiva")
        int quantidade
) {}
