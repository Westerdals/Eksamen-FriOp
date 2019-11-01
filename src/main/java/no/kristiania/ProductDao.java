package no.kristiania;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao{

    private ArrayList<Product> products = new ArrayList<>();
    private DataSource dataSource;

    public ProductDao(DataSource dataSource){
    this.dataSource = dataSource;
    }


/*
    @Override
    protected void insertObject(Product product, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, product.getName());
    }

 */
/*
    @Override
    protected Product readObject(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setName(rs.getString("project_name"));
        return product;
    }


 */
    public void insert(Product product) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("insert into PRODUCTS (name) values (?)")) {
                statement.setString(1, product.getName());
                statement.executeUpdate();
            }
        }
    products.add(product);
    }

    public List<Product> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("Select * from PRODUCTS")) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<Product> result = new ArrayList<>();
                    while(rs.next()){
                        result.add(new Product());
                    }
                }
            }
        }
       return products;

        // return listAll("select * from products");
    }
}
