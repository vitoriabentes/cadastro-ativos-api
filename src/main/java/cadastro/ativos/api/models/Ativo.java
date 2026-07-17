package cadastro.ativos.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ativo {
    private String codigo;
    private String nome;
    private BigDecimal valorBase;
    private BigDecimal indexador;
    private Boolean aptaNegociacao;
    private int quantidade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Ativo(String codigo, String nome, BigDecimal valorBase, BigDecimal indexador, Boolean aptaNegociacao, int quantidade) {
        this.codigo = codigo;
        this.nome = nome;
        this.valorBase = valorBase;
        this.indexador = indexador;
        this.aptaNegociacao = aptaNegociacao;
        this.quantidade = quantidade;
    }
}
