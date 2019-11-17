package no.kristiania.webshop.projectTests;

import no.kristiania.webshop.Projects.Project;
import no.kristiania.webshop.Projects.ProjectController;
import no.kristiania.webshop.Projects.ProjectDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectControllerTest {

    @Test
    void shouldReturnProductsFromDatabase() throws SQLException {
        ProjectDao dao = new ProjectDao(ProjectDaoTest.createDataSource());

        Project project1 = ProjectDaoTest.sampleProject();
        dao.insert(project1);

        ProjectController controller = new ProjectController(dao);
        assertThat(controller.getBody())
                .contains(String.format("<option id='%s'>%s ->S %s</option>", project1.getId(), project1.getName(), project1.getPStatus()));
    }

}