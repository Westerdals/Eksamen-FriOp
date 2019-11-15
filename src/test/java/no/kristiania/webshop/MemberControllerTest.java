package no.kristiania.webshop;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class MemberControllerTest {

    @Test
    void shouldReturnProductsFromDatabase() throws SQLException {
        MemberDao dao = new MemberDao(MemberDaoTest.createDataSource());

        Member member1 = MemberDaoTest.sampleProduct();
        dao.insert(member1);

        MemberController controller = new MemberController(dao);
        assertThat(controller.getBody())
                .contains(String.format("<option value='%s'>%s</option>", member1.getId(), member1.getName()));
    }

}