package no.kristiania.http;

import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
public class HttpServer {

    private ServerSocket serverSocket;
    private String assertRoot;

    private HttpController defaultController = new FileHttpController(this);

    private Map<String, HttpController> controllers = new HashMap<>();

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        controllers.put("/echo", new EchoHttpController());
    }

    public static void main(String[] args) throws IOException {
        new HttpServer(8080).start();
    }

    public void start() {
        new Thread(() -> run()).start();
    }

    private static final Logger Logger = LoggerFactory.getLogger(HttpServer.class);

    private void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();

                String requestLine = HttpMessage.readLine(socket.getInputStream());
                // Logger.info("Handling request {}", requestLine);
                while (!HttpMessage.readLine(socket.getInputStream()).isBlank()) {
                }

                String requestTarget = requestLine.split(" ")[1];
                int questionPost = requestTarget.indexOf('?');
                String requestPath = questionPost == -1 ? requestTarget : requestTarget.substring(0, questionPost);
                Map<String, String> query = getQueryParameters(requestTarget);

                controllers
                        .getOrDefault(requestPath, defaultController)
                        .handle(requestPath, query, socket.getOutputStream());

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
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

    public void setAssertRoot(String assertRoot) {
        this.assertRoot = assertRoot;
    }

    public void addController(String requestPath, HttpController controller){
        controllers.put(requestPath, controller);
    }
}
