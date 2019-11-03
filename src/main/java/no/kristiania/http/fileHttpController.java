package no.kristiania.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

class fileHttpController implements HttpController {

    private HttpServer httpServer;

    public fileHttpController(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    public void handle(String requestPath, Map<String, String> query, OutputStream outputStream) throws IOException {
        File file = new File(httpServer.getAssertRoot() + requestPath);
        if(file.isDirectory()){
            file = new File(file,"index.html");
        }
        if(file.exists()){
            long length = file.length();
            outputStream.write(("HTTP//1.1 200 Not found\r\n" +
                    "Content-length: " + length + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n").getBytes());
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                fileInputStream.transferTo(outputStream);
            }
        } else {
            outputStream.write(("HTTP//1.1 200 Not found\r\n" +
                    "Content-type: text/plain\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    "Not found").getBytes());
        }
    }
}