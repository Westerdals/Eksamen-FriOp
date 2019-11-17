package no.kristiania.webshop.assignTests;

import no.kristiania.webshop.members.Member;
import no.kristiania.webshop.members.MemberController;
import no.kristiania.webshop.members.MemberDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AssignHttpControllerTest {

    @Test
    void shouldReturnAllProducts() throws SQLException {
        MemberDao memberDao = new MemberDao(AssignDaoTest.createDataSource());
        Member member = AssignDaoTest.sampleProduct();
        memberDao.insert(member);

        MemberController controller = new MemberController(memberDao);
        assertThat(controller.getBody())
                .contains("<option value='" + member.getId() + "'>" + member.getName() + " " + member.getLName() + "</option>");
    }
}


