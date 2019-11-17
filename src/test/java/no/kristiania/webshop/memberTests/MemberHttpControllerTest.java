package no.kristiania.webshop.memberTests;

import no.kristiania.webshop.members.Member;
import no.kristiania.webshop.members.MemberController;
import no.kristiania.webshop.members.MemberDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberHttpControllerTest {

    @Test
    void shouldReturnAllProducts() throws SQLException {
        MemberDao memberDao = new MemberDao(MemberDaoTest.createDataSource());
        Member member = MemberDaoTest.sampleProduct();
        memberDao.insert(member);

        MemberController controller = new MemberController(memberDao);
        assertThat(controller.getBody())
                .contains("<option id='" + member.getId() + "'>" + member.getName() + " " + member.getLName() + "</option>");
    }
}

