package no.kristiania.webshop.memberTests;

import no.kristiania.webshop.members.Member;
import no.kristiania.webshop.members.MemberDao;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberDaoTest {

    private static Random random = new Random();
    private MemberDao dao;

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = createDataSource();
        dao = new MemberDao(dataSource);
    }

    static JdbcDataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:productTest;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @Test
    void shouldListSavedProducts() throws SQLException {
        Member member = sampleProduct();
        dao.insert(member, "insert into MEMBERS (name, lName) values (?, ?)");
        assertThat(dao.listAll("Select * from MEMBERS"))
                .extracting(Member::getName)
                .contains(member.getName());
    }
    @Test
    void shouldRetrieveSavedProduct() throws SQLException {
        Member member = sampleProduct();
        dao.insert(member);
        assertThat(member).hasNoNullFieldsOrProperties();
        assertThat(dao.retrieve(member.getId(), "select * from MEMBERS where id = ?"))
                .isEqualToComparingFieldByField(member);
    }


    static Member sampleProduct() {
        Member member = new Member();
        member.setName(PickOne(new String [] {"apple", "banana", "coconut", "dried apples", "excitement", "Sadness"}));
        member.setLName(PickOne(new String [] {"apple", "banana", "coconut", "dried apples", "excitement", "Sadness"}));
        return member;
    }

    private static String PickOne(String[] alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }


}// end MemberDaoTest
