package no.kristiania.webshop.assignTests;

import no.kristiania.webshop.AssignedProjects.Assign;
import no.kristiania.webshop.AssignedProjects.AssignController;
import no.kristiania.webshop.AssignedProjects.AssignDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class JoinControllerTest {

    /*
    @Test
    void shouldReturnProductsFromDatabase() throws SQLException {
        AssignDao dao = new AssignDao(JoinDaoTest.createDataSource());

        Assign assign1 = JoinDaoTest.sampleProduct();
        dao.insert(assign1);

        AssignController controller = new AssignController(dao);
        assertThat(controller.getBody())
                .contains(String.format("<option id='%s %s'>Project: %s -> %s</option>", assign1.getMemberName(), assign1.getProjectName(), assign1.getProjectName(), assign1.getMemberName()));
    }


     */
}