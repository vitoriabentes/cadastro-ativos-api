package cadastro.ativos.api.rowMappers;
import cadastro.ativos.api.models.Ativo;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AtivoRowMapper implements RowMapper<Ativo> {

    @Override
    public Ativo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Ativo(
                rs.getString("CODIGO"),
                rs.getString("NOME"),
                rs.getBigDecimal("VALOR_BASE"),
                rs.getInt("INDEXADOR"),
                rs.getBoolean("APTA_NEGOCIACAO"),
                rs.getInt("QUANTIDADE"),
                rs.getTimestamp("DATA_CRIACAO").toLocalDateTime(),
                rs.getTimestamp("DATA_ATUALIZACAO").toLocalDateTime()
        );
    }
}