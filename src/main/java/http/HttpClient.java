package http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {
    private final String hostName;
    private final int port;
    private final String requestTarget;

    public HttpClient(String hostName, int port, String requestTarget) {
        this.hostName = hostName;
        this.port = port;
        this.requestTarget = requestTarget;
    }

    public HttpClientResponse execute() throws IOException {
        Socket socket = new Socket(hostName, port);
        socket.getOutputStream().write(("GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + hostName + "\r\n" +
                "\r\n").getBytes());
        socket.getOutputStream().flush();


        return new HttpClientResponse(socket.getInputStream());
    }
}
