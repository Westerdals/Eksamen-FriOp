package no.kristiania.webshop;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class ProductControllerTest {

    @Test
    void shouldReturnProductsFromDatabase() throws SQLException {
        ProductDao dao = new ProductDao(ProductDaoTest.createDataSource());

        Product product1 = ProductDaoTest.sampleProduct();
        dao.insert(product1);

        ProductController controller = new ProductController(dao);
        assertThat(controller.getBody())
                .contains(String.format("<option value='%s'>%s</option>", product1.getId(),product1.getName()));
    }

}