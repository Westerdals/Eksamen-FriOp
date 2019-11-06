package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpClient {
    private final String hostName;
    private final int port;
    private final String requestTarget;
    private String body;
    private Map<String, String> headers = new HashMap<>();

    public HttpClient(String hostName, int port, String requestTarget) {
        this.hostName = hostName;
        this.port = port;
        this.requestTarget = requestTarget;
        setRequestHeader("host",hostName);
        setRequestHeader("Connection","close");
    }

    public HttpClientResponse execute(String httpMethod) throws IOException {
        Socket socket = new Socket(hostName, port);

        if (body != null) {
            setRequestHeader("Content-length",String.valueOf(body.length()));
        }

        String headerString = headers.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("\r\n"));


        socket.getOutputStream().write((httpMethod + " " + requestTarget + " HTTP/1.1\r\n" +
                headerString +
                "\r\n\r\n" + body).getBytes());
        return new HttpClientResponse(socket.getInputStream());
    }

    public void setRequestHeader(String headerName, String headerValue){
        headers.put(headerName, headerValue);

    }
    public void setBody(String body){
        this.body = body;
    }
}
