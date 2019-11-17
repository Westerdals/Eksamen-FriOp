package no.kristiania.webshop.projectTests;

import no.kristiania.webshop.Projects.Project;
import no.kristiania.webshop.Projects.ProjectController;
import no.kristiania.webshop.Projects.ProjectDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectHttpControllerTest {

    @Test
    void shouldReturnAllProducts() throws SQLException {
        ProjectDao projectDao = new ProjectDao(ProjectDaoTest.createDataSource());
        Project project = ProjectDaoTest.sampleProject();
        projectDao.insert(project);

        ProjectController controller = new ProjectController(projectDao);
        assertThat(controller.getBody())
                .contains("<option value='" + project.getId() + "'>" + project.getName() + "</option>");
    }
}


