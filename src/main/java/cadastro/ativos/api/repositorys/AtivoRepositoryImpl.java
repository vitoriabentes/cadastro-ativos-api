package cadastro.ativos.api.repositorys;

import cadastro.ativos.api.interfaces.AtivoRepository;
import cadastro.ativos.api.models.Ativo;
import cadastro.ativos.api.rowMappers.AtivoRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AtivoRepositoryImpl implements AtivoRepository {
    private static final Logger log = LoggerFactory.getLogger(AtivoRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AtivoRowMapper ativoRowMapper;


    @Override
    public Optional<Ativo> findByCode(String codigo) {
        String query = """
                SELECT * FROM CADASTROS.ATIVO WHERE CODIGO = ?
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, ativoRowMapper, codigo));
        }catch (RuntimeException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Ativo> findAll() {
        String query = """
                SELECT * FROM CADASTROS.ATIVO
                """;
        return jdbcTemplate.query(query, ativoRowMapper);
    }

    @Override
    public Ativo save(Ativo ativo) {
        String query = """
            INSERT INTO CADASTROS.ATIVO (CODIGO, NOME, VALOR_BASE, QUANTIDADE, INDEXADOR, APTA_NEGOCIACAO)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING DATA_CRIACAO, DATA_ATUALIZACAO
            """;

        var returned = jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new Object[]{
                        rs.getTimestamp("DATA_CRIACAO").toLocalDateTime(),
                        rs.getTimestamp("DATA_ATUALIZACAO").toLocalDateTime()
                },
                ativo.getCodigo().toUpperCase(),
                ativo.getNome(),
                ativo.getValorBase(),
                ativo.getQuantidade(),
                ativo.getIndexador(),
                ativo.getAptaNegociacao()
        );
        if (returned != null) {
            ativo.setDataCriacao((LocalDateTime) returned[0]);
            ativo.setDataAtualizacao((LocalDateTime) returned[1]);
        }

        log.info("Ativo {} salvo com sucesso.", ativo.getCodigo());
        return ativo;
    }

    @Override
    public void deactivate(Ativo ativo) {
        String query = """
            UPDATE CADASTROS.ATIVO
            SET ATIVO = FALSE
            WHERE CODIGO = ?
            """;
        jdbcTemplate.update(query, ativo.getCodigo().toUpperCase());
        log.info("Ativo {} desativado com sucesso.", ativo.getCodigo());
    }

    @Override
    public Ativo update(Ativo ativo) {
        String query = """
            UPDATE CADASTROS.ATIVO
            SET NOME = ?, VALOR_BASE = ?, QUANTIDADE = ?, INDEXADOR = ?, APTA_NEGOCIACAO = ?, DATA_ATUALIZACAO = NOW()
            WHERE CODIGO = ?
            RETURNING DATA_ATUALIZACAO
            """;

        var returned = jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> rs.getTimestamp("DATA_ATUALIZACAO").toLocalDateTime(),
                ativo.getNome(),
                ativo.getValorBase(),
                ativo.getQuantidade(),
                ativo.getIndexador(),
                ativo.getAptaNegociacao(),
                ativo.getCodigo().toUpperCase()
        );
        if (returned != null) {
            ativo.setDataAtualizacao(returned);
        }

        log.info("Ativo {} atualizado com sucesso.", ativo.getCodigo());
        return ativo;
    }

}
