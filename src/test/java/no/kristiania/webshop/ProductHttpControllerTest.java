package no.kristiania.webshop;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductHttpControllerTest {

    @Test
    void shouldReturnAllProducts() throws SQLException {
        ProductDao productDao = new ProductDao(ProductDaoTest.createDataSource());
        Product product = ProductDaoTest.sampleProduct();
        productDao.insert(product);

        ProductController controller = new ProductController(productDao);
        assertThat(controller.getBody())
                .contains("<option value='" + product.getId() + "'>" + product.getName() + "</option>");
    }
}


