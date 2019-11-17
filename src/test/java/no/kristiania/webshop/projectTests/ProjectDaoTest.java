package no.kristiania.webshop.projectTests;

import no.kristiania.webshop.Projects.Project;
import no.kristiania.webshop.Projects.ProjectDao;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectDaoTest {

    private static Random random = new Random();
    private ProjectDao dao;

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = createDataSource();
        dao = new ProjectDao(dataSource);
    }

    public static JdbcDataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:productTest;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @Test
    void shouldListSavedProducts() throws SQLException {
        Project project = sampleProject();
        dao.insert(project, "insert into PROJECTS (name) values (?)");
        assertThat(dao.listAll("Select * from PROJECTS"))
                .extracting(Project::getName)
                .contains(project.getName());
    }
    @Test
    void shouldRetrieveSavedProduct() throws SQLException {
        Project project = sampleProject();
        dao.insert(project);
        assertThat(project).hasNoNullFieldsOrProperties();
        assertThat(dao.retrieve(project.getId(), "select * from PROJECTS where id = ?"))
                .isEqualToComparingFieldByField(project);
    }


    public static Project sampleProject() {
        Project member = new Project();
        member.setName(PickOne(new String [] {"apple", "banana", "coconut", "dried apples", "excitement", "Sadness"}));
        member.setPStatus(PickOne(new String [] {"Done", "Finish", "On Going", "On Hold", "excitement", "Invalid"}));
        return member;
    }

    private static String PickOne(String[] alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }


}// end ProjectDaoTest
