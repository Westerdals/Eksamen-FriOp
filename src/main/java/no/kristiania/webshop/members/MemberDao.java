package no.kristiania.webshop.members;
import no.kristiania.webshop.AbstractDao;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberDao extends AbstractDao<Member> {

    public MemberDao(DataSource dataSource){
        super(dataSource);
    }

    public void insert(Member member) throws SQLException {
        long id = insert(member, "insert into MEMBERS (name, lName) values (?, ?)");
     member.setId(id);
    }

    @Override
    protected void mapToStatement(Member member, PreparedStatement statement) throws SQLException {
        statement.setString(1, member.getName());
        statement.setString(2, member.getLName());
    }

    public List<Member> listAll() throws SQLException {
        return listAll("select * from MEMBERS");
    }

   @Override
    public Member mapFromResultSet(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getLong("id"));
        member.setName(rs.getString("name"));
        member.setLName(rs.getString("lName"));
        return member;
    }

}
