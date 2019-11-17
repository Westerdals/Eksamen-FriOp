package no.kristiania.webshop.assignTests;

import no.kristiania.webshop.AssignedProjects.Assign;
import no.kristiania.webshop.AssignedProjects.AssignController;
import no.kristiania.webshop.AssignedProjects.AssignDao;
import no.kristiania.webshop.members.Member;
import no.kristiania.webshop.members.MemberController;
import no.kristiania.webshop.members.MemberDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AssignHttpControllerTest {

    @Test
    void shouldReturnAllProducts() throws SQLException {
        AssignDao assignDao = new AssignDao(AssignDaoTest.createDataSource());
        Assign assign = AssignDaoTest.sampleProduct();
        assignDao.insert(assign);

        AssignController controller = new AssignController(assignDao);
        assertThat(controller.getBody())
                .contains("<option id='" + assign.getMemberName()  + " " + assign.getProjectName() + "'>Project: " + assign.getProjectName() + " -> " + assign.getMemberName() + "</option>");
    }
}


