package no.kristiania.webshop.AssignedProjects;
import no.kristiania.webshop.AbstractDao;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AssignDao extends AbstractDao<Assign> {

    public AssignDao(DataSource dataSource){
        super(dataSource);
    }

public void insert(Assign assign) throws SQLException {
    jonTables(assign, "insert into JOINTABELS (memberName, projectName) values (?, ?)");
}
    @Override
    protected void mapToStatement(Assign assign, PreparedStatement statement) throws SQLException {
        statement.setString(1, assign.getMemberName());
        statement.setString(2, assign.getProjectName());
    }

    public List<Assign> listAll() throws SQLException {
        return listAll("select * from JOINTABELS");
    }

   @Override
    public Assign mapFromResultSet(ResultSet rs) throws SQLException {
       Assign assign = new Assign();
       assign.setMemberName(rs.getString("memberName"));
       assign.setProjectName(rs.getString("projectName"));
        return assign;
    }
}
