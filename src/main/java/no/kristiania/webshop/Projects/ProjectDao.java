package no.kristiania.webshop.Projects;


import no.kristiania.webshop.AbstractDao;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectDao extends AbstractDao<Project> {

    public ProjectDao(DataSource dataSource){
        super(dataSource);
    }

    public void insert(Project project) throws SQLException {
        long id = insert(project, "insert into PROJECTS (name, pStatus) values (?, ?)");
         project.setId(id);
    }
        @Override
    protected void mapToStatement(Project project, PreparedStatement statement) throws SQLException {
            statement.setString(1, project.getName());
            statement.setString(2, project.getPStatus());
    }

    public List<Project> listAll() throws SQLException {
        return listAll("select * from PROJECTS");
    }

   @Override
    public Project mapFromResultSet(ResultSet rs) throws SQLException {
       Project project = new Project();
       project.setId(rs.getLong("id"));
       project.setName(rs.getString("name"));
       project.setPStatus(rs.getString("pStatus"));
        return project;
    }

}
