package no.kristiania.webshop.JoinTests;

import no.kristiania.webshop.JoinProjects.Join;
import no.kristiania.webshop.JoinProjects.JoinController;
import no.kristiania.webshop.JoinProjects.JoinDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class JoinHttpControllerTest {

    @Test
    void shouldReturnAllData() throws SQLException {
        AssignDao assignDao = new AssignDao(JoinDaoTest.createDataSource());
        Assign assign = JoinDaoTest.sampleData();
        assignDao.insert(assign);
    void shouldReturnAllProducts() throws SQLException {
        JoinDao joinDao = new JoinDao(JoinDaoTest.createDataSource());
        Join join = JoinDaoTest.sampleProduct();
        joinDao.insert(join);

        JoinController controller = new JoinController(joinDao);
        assertThat(controller.getBody())
                .contains("<option id='" + join.getMemberName()  + " " + join.getProjectName() + "'>Project: " + join.getProjectName() + " -> " + join.getMemberName() + "</option>");
    }
}


