package no.kristiania;
import javax.sql.DataSource;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.List;

public class ProductDao extends AbstractDao<Product>{

    public ProductDao(DataSource dataSource){
        super(dataSource);
    }

    @Override
    protected void insertObject(Product product, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, product.getName());
    }

    @Override
    protected Product readObject(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setName(rs.getString("project_name"));
        return product;
    }

    public void insert(Product product) throws SQLException {
        insert(product,"insert into products (project_name) values (?)");
    }

    public List<Product> listAll() throws SQLException {
        return listAll("select * from products");
    }
}
