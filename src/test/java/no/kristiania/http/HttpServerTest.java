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


    @Test
    void shouldReturn404OnUnknownPath() throws IOException {
        HttpClientResponse response = executeLocalRequest("/no/such/file");
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    private HttpClientResponse executeLocalRequest(String s) throws IOException{
        HttpClient client = new HttpClient("localhost", server.getPort(), s);
        return client.execute("GET");
    }

    @Test
    void shouldParseMultipleParameters() throws IOException {
        HttpClientResponse response = executeLocalRequest("/echo?content-type=text/html&body=foobar");
        assertThat(response.getHeader("content-type")).isEqualTo("text/html");
        assertThat(response.getBody()).isEqualTo("foobar");
    }

    @Test
    void shouldParsePostParameters() throws IOException {
        String formBody = "content-type=text/html&body=foobar";
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo");
        client.setRequestHeader("content-type", "application/x-www-form-urlencoded");
        client.setBody(formBody);
        HttpClientResponse response = client.execute("POST");
        assertThat(response.getHeader("content-type")).isEqualTo("foobar");
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
