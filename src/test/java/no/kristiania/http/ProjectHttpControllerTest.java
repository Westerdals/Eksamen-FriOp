package no.kristiania.http;

import no.kristiania.webshop.Projects.Project;
import no.kristiania.webshop.Projects.ProjectController;
import no.kristiania.webshop.Projects.ProjectDao;
import no.kristiania.webshop.projectTests.ProjectDaoTest;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectHttpControllerTest {

    @Test
    void shouldReturnAllProjects() throws SQLException {
        ProjectDao dao = new ProjectDao(ProjectDaoTest.createDataSource());
        Project project = ProjectDaoTest.sampleProject();
        dao.insert(project);

        ProjectController controller = new ProjectController(dao);
        assertThat(controller.getBody())
                .contains("<option id='" + project.getId() +"'>" + project.getName() + " ->S " + project.getPStatus() + "</option>");
    }

    /*
    @Test
    void shouldCreateNewProject() throws IOException, SQLException {
        ProjectDao dao = new ProjectDao(ProjectDaoTest.createDataSource());

        ProjectController controller = new ProjectController(dao);

        String requestBody = "name=NewTestProject&ok";
        controller.handle("post", "/api/project", null, requestBody, new ByteArrayOutputStream());

        assertThat(dao.listAll())
                .extracting(Project::getName)
                .contains("NewTestProject");
    }

     */

}
