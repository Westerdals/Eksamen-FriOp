package no.kristiania.webshop.assignTests;


import no.kristiania.webshop.JoinProjects.Join;
import no.kristiania.webshop.JoinProjects.JoinDao;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class JoinDaoTest {

    private static Random random = new Random();
    private JoinDao dao;

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = createDataSource();
        dao = new JoinDao(dataSource);
    }

    static JdbcDataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:productTest;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @Test
    void shouldListSavedProducts() throws SQLException {
        Join join = sampleProduct();
        dao.jonTables(join, "insert into JOINTABELS (membername, projectname) values (?, ?)");
        assertThat(dao.listAll("Select * from JOINTABELS"))
                .extracting(Join::getMemberName)
                .contains(join.getMemberName());
    }
    @Test
    void shouldRetrieveSavedProduct() throws SQLException {
        Join join = sampleProduct();
        dao.insert(join);
        assertThat(join).hasNoNullFieldsOrProperties();
        assertThat(dao.retrieveJoin(join.getProjectName(), "select * from JOINTABELS where projectName = ?"))
                .isEqualToComparingFieldByField(join);
    }


    static Join sampleProduct() {
        Join join = new Join();
        join.setMemberName(PickOne(new String [] {"apple", "banana", "coconut", "dried apples", "excitement", "Sadness"}));
        join.setProjectName(PickOne(new String [] {"apple", "banana", "coconut", "dried apples", "excitement", "Sadness"}));
        return join;
    }

    private static String PickOne(String[] alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }


}// end ProjectDaoTest
