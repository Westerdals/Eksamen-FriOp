package no.kristiania.webshop;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {

    private static Random random = new Random();
    private ProductDao dao;

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = createDataSource();
        dao = new ProductDao(dataSource);
    }

    static JdbcDataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:productTest;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @Test
    void shouldListSavedProducts() throws SQLException {
        Product product = sampleProduct();
        dao.insert(product, "insert into PRODUCTS (name) values (?)");
        assertThat(dao.listAll("Select * from PRODUCTS"))
                .extracting(Product::getName)
                .contains(product.getName());
    }
    @Test
    void shouldRetrieveSavedProduct() throws SQLException {
        Product product = sampleProduct();
        dao.insert(product);
        assertThat(product).hasNoNullFieldsOrProperties();
        assertThat(dao.retrieve(product.getId(), "select * from PRODUCTS where id = ?"))
                .isEqualToComparingFieldByField(product);
    }


    static Product sampleProduct() {
        Product product = new Product();
        product.setName(PickOne(new String [] {"apple", "banana", "coconut", "dried apples", "excitement", "Sadness"}));
        return product;
    }

    private static String PickOne(String[] alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }


}// end ProductDaoTest
