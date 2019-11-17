package no.kristiania.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServerTest {

    private HttpServer server;
    @BeforeEach
    void setUp() throws IOException{
        server = new HttpServer(0);
        server.start();
    }

    private HttpClientResponse executeLocalRequest(String s) throws IOException{
        HttpClient client = new HttpClient("localhost", server.getPort(), s);
        return client.execute("GET");
    }


    @Test
    void shouldReadFile() throws IOException {
        server.setAssertRoot("target/");
        String fileContent = "Some random String " + System.currentTimeMillis();
        Files.writeString(Paths.get("target", "somefile.txt"), fileContent);

        HttpClientResponse response = executeLocalRequest("/somefile.txt");
        assertThat(response.getBody()).isEqualTo(fileContent);
    }
}
