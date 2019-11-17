package no.kristiania.webshop.JoinProjects;

import no.kristiania.webshop.AbstractDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JoinDao extends AbstractDao<Join> {

    public JoinDao(DataSource dataSource){
        super(dataSource);
    }

    private static Logger logger = LoggerFactory.getLogger(JoinDao.class);

public void insert(Join join) throws SQLException {


    try {
        jonTables(join, "insert into JOINTABELS (memberName, projectName) values (?, ?)");
    } catch (SQLException e) {

        logger.info("handling sql Insert from JoinDao ");

    }

}
    @Override
    protected void mapToStatement(Join join, PreparedStatement statement) throws SQLException {
        statement.setString(1, join.getMemberName());
        statement.setString(2, join.getProjectName());
    }

    public List<Join> listAll() throws SQLException {
        return listAll("select * from JOINTABELS");
    }

   @Override
    public Join mapFromResultSet(ResultSet rs) throws SQLException {
       Join join = new Join();
       join.setMemberName(rs.getString("memberName"));
       join.setProjectName(rs.getString("projectName"));
        return join;
    }

}
