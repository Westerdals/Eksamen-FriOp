package no.kristiania.http;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class HttpClientTest {

    private HttpServer server;

    @BeforeEach
    void setUp() throws IOException{
        server = new HttpServer(0);
        server.start();
    }
/*
    @Test
    void shouldReturn200Ok() throws NullPointerException {
        HttpClientResponse response = execute("/echo");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

/*
    @Test
    void shouldReadReturnCode() throws IOException {
        HttpClientResponse response = makeEchoRequest("/echo?content-type=text/css");
        assertThat(response.getHeader()).containsEntry("content-type", "text/css; charset=utf-8");
    }

    @Test
    void shouldReadBody() throws IOException {
        HttpClientResponse response = makeEchoRequest("/echo?body=hello+world");
        assertThat(response.getHeaders()).containsEntry("content-length", "11");
        assertThat(response.getContentLength()).isEqualTo(11);
        assertThat(response.getBody()).isEqualTo("hello world");
    }

 */




}
