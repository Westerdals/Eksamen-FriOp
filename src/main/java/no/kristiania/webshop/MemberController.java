package no.kristiania.webshop;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class MemberController implements HttpController {

    private final MemberDao memberDao;

    public MemberController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }



    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> query, String requestBody, OutputStream outputStream) throws IOException, SQLException {
        try {
            if (requestAction.equals("POST")) {
              query = HttpServer.parseQueryString(requestBody);
                Member member = new Member();

                member.setName(query.get("name"));
                member.setLName(query.get("lName"));
                memberDao.insert(member);
                outputStream.write(("HTTP/1.1 302 Redirect\r\n" +
                        "Location: http://localhost:8080/AddMemberToOrder.html\r\n" +
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
        String body = memberDao.listAll().stream()
                .map(p -> String.format("<option value='%s'>%s %s</option>", p.getId(), p.getName(), p.getLName()))
                .collect(Collectors.joining(""));
        return body;
    }
}
