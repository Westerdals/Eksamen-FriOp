package no.kristiania.webshop;

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
                .contains("<option value='" + member.getId() + "'>" + member.getName() + "</option>");
    }
}


