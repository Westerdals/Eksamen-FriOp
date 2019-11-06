package no.kristiania.webshop;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    @Test
    void shouldReturnProductsFromDatabase() throws SQLException {
        ProductDao dao = new ProductDao(ProductDaoTest.createDataSource());

        Product product1 = ProductDaoTest.sampleProduct();
        Product product2 = ProductDaoTest.sampleProduct();
        dao.insert(product1);
        dao.insert(product2);

        ProductController controller = new ProductController(dao);
        assertThat(controller.getBody())
                .contains(String.format("<option id='%s'>%s</option>", product1.getId(),product1.getName()))
                .contains(String.format("<option id='%s'>%s</option>", product2.getId(),product1.getName()));
    }

}