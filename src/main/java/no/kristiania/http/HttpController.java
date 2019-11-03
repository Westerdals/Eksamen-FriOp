package no.kristiania.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public interface HttpController {

    void handle(String requestPath, Map<String, String> query, OutputStream outputStream) throws IOException;
}
