package no.kristiania.webshop.assignTests;

import no.kristiania.webshop.members.Member;
import no.kristiania.webshop.members.MemberController;
import no.kristiania.webshop.members.MemberDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class AssignControllerTest {

    @Test
    void shouldReturnProductsFromDatabase() throws SQLException {
        MemberDao dao = new MemberDao(AssignDaoTest.createDataSource());

        Member member1 = AssignDaoTest.sampleProduct();
        dao.insert(member1);

        MemberController controller = new MemberController(dao);
        assertThat(controller.getBody())
                .contains(String.format("<option value='%s'>%s %s</option>", member1.getId(), member1.getName(), member1.getLName()));
    }

}