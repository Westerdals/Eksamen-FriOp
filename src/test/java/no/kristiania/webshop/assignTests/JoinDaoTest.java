package no.kristiania.webshop.assignTests;


import no.kristiania.webshop.AssignedProjects.Assign;
import no.kristiania.webshop.AssignedProjects.AssignDao;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class JoinDaoTest {

    private static Random random = new Random();
    private AssignDao dao;

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = createDataSource();
        dao = new AssignDao(dataSource);
    }

    static JdbcDataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:productTest;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @Test
    void shouldListSavedData() throws SQLException {
        Assign assign = sampleData();
        dao.jonTables(assign, "insert into JOINTABELS (membername, projectname) values (?, ?)");
        assertThat(dao.listAll("Select * from JOINTABELS"))
                .extracting(Assign::getMemberName)
                .contains(assign.getMemberName());
    }
    @Test
    void shouldRetrieveSavedData() throws SQLException {
        Assign assign = sampleData();
        dao.insert(assign);
        assertThat(assign).hasNoNullFieldsOrProperties();
        assertThat(dao.retrieveJoin(assign.getProjectName(), "select * from JOINTABELS where projectName = ?"))
                .isEqualToComparingFieldByField(assign);
    }


    static Assign sampleData() {
        Assign assign = new Assign();
        assign.setMemberName(PickOne(new String [] {"apple", "banana", "coconut", "dried apples", "excitement", "Sadness"}));
        assign.setProjectName(PickOne(new String [] {"apple", "banana", "coconut", "dried apples", "excitement", "Sadness"}));
        return assign;
    }

    private static String PickOne(String[] alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }


}
