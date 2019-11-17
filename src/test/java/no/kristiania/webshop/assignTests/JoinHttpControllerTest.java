package no.kristiania.webshop.assignTests;

import no.kristiania.webshop.AssignedProjects.Assign;
import no.kristiania.webshop.AssignedProjects.AssignController;
import no.kristiania.webshop.AssignedProjects.AssignDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class JoinHttpControllerTest {

    @Test
    void shouldReturnAllData() throws SQLException {
        AssignDao assignDao = new AssignDao(JoinDaoTest.createDataSource());
        Assign assign = JoinDaoTest.sampleData();
        assignDao.insert(assign);

        AssignController controller = new AssignController(assignDao);
        assertThat(controller.getBody())
                .contains("<option id='" + assign.getMemberName()  + " " + assign.getProjectName() + "'>Project: " + assign.getProjectName() + " -> " + assign.getMemberName() + "</option>");
    }
}


