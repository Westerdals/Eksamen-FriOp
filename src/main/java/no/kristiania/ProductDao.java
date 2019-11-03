package no.kristiania;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDao extends AbstractDao<Product> {

    public ProductDao(DataSource dataSource){
        super(dataSource);
    }

public void insert(Product product) throws SQLException {
    long id = insert(product, "insert into PRODUCTS (name) values (?)");
    product.setId(id);
}

    @Override
    protected void mapToStatement(Product product, PreparedStatement statement) throws SQLException {
        statement.setString(1, product.getName());
    }

    public List<Product> listAll() throws SQLException {
        return listAll("select * from PRODUCTS");
    }

   @Override
    public Product mapFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        return product;
    }

    public Product retrieve(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from PRODUCTS where id = ?")) {
                statement.setLong(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    if(rs.next()){
                        return mapFromResultSet(rs);
                    } else {
                        return null;
                    }
                }
            }
        }
    }
}
