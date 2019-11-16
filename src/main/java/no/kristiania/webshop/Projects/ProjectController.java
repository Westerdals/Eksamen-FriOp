package no.kristiania.webshop.Projects;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectController implements HttpController {

    private final ProjectDao projectDao;

    public ProjectController(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> query, String requestBody, OutputStream outputStream) throws IOException, SQLException {
        try {
            if (requestAction.equals("POST")) {
              query = HttpServer.parseQueryString(requestBody);
                Project project = new Project();

                project.setName(query.get("name"));
                projectDao.insert(project);
                outputStream.write(("HTTP/1.1 302 Redirect\r\n" +
                        "Location: http://localhost:8080/AddProject.html\r\n" +
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
        String body = projectDao.listAll().stream()
                .map(p -> String.format("<option value='%s'>%s</option>", p.getId(), p.getName()))
                .collect(Collectors.joining(""));
        return body;
    }
}
