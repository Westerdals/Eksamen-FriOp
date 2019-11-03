package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
public class HttpServer {

    private ServerSocket serverSocket;
    private String assertRoot;

    private HttpController defaultController = new fileHttpController(this);

    private Map<String, HttpController> controllers = new HashMap<>();

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        controllers.put("/echo", new EchoHttpController());
    }

    public static void main(String[] args) throws IOException {
        new HttpServer(8080).start();
    }

    private void start() {
        new Thread(() -> run()).start();
    }

    private void run()  {
        try {
            Socket socket = serverSocket.accept();

            String requestLine = HttpMessage.readLine(socket.getInputStream());
            while (!HttpMessage.readLine(socket.getInputStream()).isBlank()){
            }

            String requestTarget = requestLine.split(" ")[1];
            int questionPost = requestTarget.indexOf('?');
            String requestPath = questionPost == -1 ? requestTarget : requestTarget.substring(0, questionPost);
            Map<String, String> query = getQueryParameters(requestTarget);

            controllers
                    .getOrDefault(requestPath, defaultController)
                    .handle(requestPath, query, socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getQueryParameters(String requestTarget) {
        Map<String, String> parameters = new HashMap<>();
        int questionPos = requestTarget.indexOf('?');
        if(questionPos > 0) {
            String queryString = requestTarget.substring(questionPos + 1);
            for(String parameter : queryString.split("&")){
                int equalsPos = parameter.indexOf('=');
                String parameterName = parameter.substring(0, equalsPos);
                String parameterValue = parameter.substring(equalsPos + 1);
                parameters.put(parameterName, parameterValue);
            }
        }
        return parameters;
    }

    public int getPort(){
        return serverSocket.getLocalPort();
    }

    public String getAssertRoot() {
        return assertRoot;
    }
}
