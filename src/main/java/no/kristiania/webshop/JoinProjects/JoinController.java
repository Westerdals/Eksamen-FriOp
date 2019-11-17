package no.kristiania.webshop.JoinProjects;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class JoinController implements HttpController {

    private final JoinDao joinDao;

    public JoinController(JoinDao joinDao) {
        this.joinDao = joinDao;
    }

    private static final Logger logger = LoggerFactory.getLogger(JoinController.class);

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> query, String requestBody, OutputStream outputStream) throws IOException, SQLException {
        try {
            if (requestAction.equals("POST")) {
              query = HttpServer.parseQueryString(requestBody);
                Join join = new Join();

                String tmpMemberName = Join.decodeValue(query.get("memberName"));
                String tmpProjectName = Join.decodeValue(query.get("projectName"));
                join.setMemberName(tmpMemberName);
                join.setProjectName(tmpProjectName);
                joinDao.insert(join);
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
        String body = joinDao.listAll().stream()
                .map(p -> String.format("<option id='%s %s'>Project: %s -> %s</option>", p.getMemberName(), p.getProjectName(), p.getProjectName(), p.getMemberName()))
                .collect(Collectors.joining(""));
        return body;
    }
}
