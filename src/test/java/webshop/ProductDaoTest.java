package webshop;

import no.kristiania.Product;
import no.kristiania.ProductDao;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {

    private Random random = new Random();

    @Test
    void shouldListSavedProducts() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:productTest");

        ProductDao dao = new ProductDao(dataSource);
        Product product = sampleProduct();

        dao.insert(product);
        assertThat(dao.listAll())
                .extracting(Product::getName)
                .contains(product.getName());
    }

    private Product sampleProduct() {
        Product product = new Product();
        product.setName(PickOne(new String [] {"apple", "banana", "coconut", "dried apples", "excitement", "Sadness"}));
        return product;
    }

    private String PickOne(String[] alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }


}// end ProductDaoTest
