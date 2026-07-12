package cadastro.ativos.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageAtivoAlterado {
    private String codigo;

    private String nome;

    @JsonProperty("valor_base")
    private BigDecimal valorBase;

    @JsonProperty("apta_negociacao")
    private boolean aptaNegociacao;

}
