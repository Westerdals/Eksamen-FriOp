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
    long id = insert(assign, "insert into JOINTABELS (name, pName) values (?, ?)");
    assign.setId(id);
}
    @Override
    protected void mapToStatement(Assign assign, PreparedStatement statement) throws SQLException {
        statement.setString(1, assign.getName());
        statement.setString(2, assign.getPName());
    }

    public List<Assign> listAll() throws SQLException {
        return listAll("select * from JOINTABELS");
    }

   @Override
    public Assign mapFromResultSet(ResultSet rs) throws SQLException {
       Assign assign = new Assign();
       assign.setId(rs.getLong("id"));
       assign.setName(rs.getString("name"));
       assign.setPName(rs.getString("pName"));
        return assign;
    }

}
