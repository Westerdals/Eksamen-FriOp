package no.kristiania;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;




public abstract class AbstractDao<ENTITY> {
    protected DataSource dataSource;

    public AbstractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(ENTITY c, String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement(sql)){
                mapToStatement(c, statement);
                statement.executeUpdate();
            }
        }
    }

    protected abstract void mapToStatement(ENTITY c, PreparedStatement statement)throws SQLException;

    public List<ENTITY> listAll(String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<ENTITY> result = new ArrayList<>();
                    while(rs.next()) {
                        result.add(mapFromResultSet(rs));
                    }
                    return result;
                }
            }
        }
    }

    protected abstract ENTITY mapFromResultSet(ResultSet resultSet) throws SQLException;
}






