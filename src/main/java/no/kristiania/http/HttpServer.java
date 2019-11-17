package no.kristiania.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static no.kristiania.http.HttpMessage.readHeaders;

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

    private static Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private void run() {
        while (true) {
            try(Socket socket = serverSocket.accept()) {

                String requestLine = HttpMessage.readLine(socket.getInputStream());
                if(requestLine.isBlank()) continue;

                Map<String, String> headers = readHeaders(socket.getInputStream());
                String body = HttpMessage.readBody(headers, socket.getInputStream());

                String requestAction = requestLine.split(" ")[0];
                String requestTarget = requestLine.split(" ")[1];
                int questionPost = requestTarget.indexOf('?');
                String requestPath = questionPost == -1 ? requestTarget : requestTarget.substring(0, questionPost);
                Map<String, String> query = getQueryParameters(requestTarget);

                controllers
                        .getOrDefault(requestPath, defaultController)
                        .handle(requestAction, requestPath, query, body, socket.getOutputStream());
                logger.info("handling request {} {}", requestAction, requestPath);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

     public static Map<String, String> getQueryParameters(String requestLine) {
       String requestTarget;

        //int questionPos = requestTarget.indexOf('?');
        if(requestLine.split(" ").length > 1) {
            requestTarget = requestLine.split(" ")[1];
        } else {
            requestTarget = "/";
        }
        int questionPos = requestTarget.indexOf('?');
        if(questionPos != -1){
            String queryString = requestTarget.substring(questionPos + 1);
            return parseQueryString(queryString);
        }
            //String queryString = requestTarget.substring(questionPos + 1);
            //return parseQueryString(queryString);
        //}
        return new HashMap<>();
    }

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> parameters = new HashMap<>();
        for(String parameter : queryString.split("&")){
            int equalsPos = parameter.indexOf('=');
            String parameterName = parameter.substring(0, equalsPos);
            String parameterValue = parameter.substring(equalsPos + 1);
            parameters.put(parameterName, parameterValue);
        }
        return parameters;
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
