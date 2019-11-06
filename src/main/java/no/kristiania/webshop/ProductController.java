package no.kristiania.webshop;

import no.kristiania.http.HttpController;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductController implements HttpController {

    private final ProductDao dao;

    public ProductController(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    public void handle(String requestPath, Map<String, String> query, OutputStream outputStream) throws IOException {
        try {
            int status = 200;
            String body = getBody();
            outputStream.write(("HTTP/1.1 "+ status + " OK\r\n" +
                    "Content-type: " + "text/html" + "\r\n" +
                    "Content-length: " + body.length() + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    body).getBytes());

        } catch (SQLException e) {
            int status = 500;
            String body = e.toString();
            outputStream.write(("HTTP/1.1 "+ status + " OK\r\n" +
                    "Content-type: " + "text/html" + "\r\n" +
                    "Content-length: " + body.length() + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    body).getBytes());
        }


    }

    String getBody() throws SQLException {
        return dao.listAll().stream()
                .map(p -> String.format("<option id='%s'>%s</option>",p.getId(),p.getName()))
                .collect(Collectors.joining(""));
    }
}
