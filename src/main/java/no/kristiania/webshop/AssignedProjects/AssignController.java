package no.kristiania.webshop.AssignedProjects;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class AssignController implements HttpController {

    private final AssignDao assignDao;

    public AssignController(AssignDao assignDao) {
        this.assignDao = assignDao;
    }

    private static final Logger logger = LoggerFactory.getLogger(AssignController.class);

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> query, String requestBody, OutputStream outputStream) throws IOException, SQLException {
        try {
            if (requestAction.equals("POST")) {
              query = HttpServer.parseQueryString(requestBody);
                Assign assign = new Assign();

                String tmpMemberName = Assign.decodeValue(query.get("memberName"));
                String tmpProjectName = Assign.decodeValue(query.get("projectName"));
                assign.setMemberName(tmpMemberName);
                assign.setProjectName(tmpProjectName);
                assignDao.insert(assign);
                outputStream.write(("HTTP/1.1 302 Redirect\r\n" +
                        "Location: http://localhost:8080/JoinTables.html\r\n" +
                        "Connection: close\r\n" +
                        "\r\n").getBytes());

                return;
            }
            String status = "200";
            String contentType = "text/html";
            String body = getBody();
            int contentLength = body.length();
            outputStream.write(("HTTP/1.1 "+ status + " OK\r\n" +
                    "Content-type: " + contentType + "\r\n" +
                    "Content-length: " + contentLength + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    body).getBytes());

        } catch (SQLException e) {
            logger.error("While handling request {}", requestPath, e);
            String message = e.toString();
            outputStream.write(("HTTP/1.1 500 Internal serverError\r\n" +
                    "Content-type: text/plain\r\n" +
                    "Content-length: " + message.length() + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    message).getBytes());
        }
    }

    public String getBody() throws SQLException {
        String body = assignDao.listAll().stream()
                .map(p -> String.format("<option value='MemberID: %s ProjectID: %s'></option>", p.getMemberName(), p.getProjectName()))
                .collect(Collectors.joining(""));
        return body;
    }
}
