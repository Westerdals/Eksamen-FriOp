package http;

import org.logevents.observers.HttpPostJsonLogEventObserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;




public class HttpServer {

    private ServerSocket serverSocket;
    private String assertRoot;

    //private HttpController defaultController = new HttpController() {

   // };

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        new HttpServer(8080).start();
    }

    private void start() {
        //new Thread(()-> run().start());
        new Thread(this::run).start();
    }

    private void run()  {
        try {
            Socket socket = serverSocket.accept();

            HttpServerRequest request = new HttpServerRequest(socket.getInputStream());
            String requestLine = request.getStartLine();


            String requestTarget = requestLine.split(" ")[1];
            Map<String, String> requestParameters = parseRequestParameters(requestTarget);

            String statusCode = requestParameters.getOrDefault("status", "200");
            String location = requestParameters.get("location");
            String body = requestParameters.getOrDefault("body","Hello World!");

            socket.getOutputStream().write(("HTTP/1.0 " + statusCode + " OK\r\n" +
                    "Content-length: " + body.length() + "\r\n" +
                    (location != null ? "Location: " + location + "\r\n" : "") +
                    "\r\n" +
                    body).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> parseRequestParameters(String requestTarget) {
        Map<String, String> requestParameters = new HashMap<>();
        int questionPos = requestTarget.indexOf('?');
        if(questionPos != -1) {
            String query = requestTarget.substring(questionPos+1);
            for (String parameter : query.split("&")) {
                int equalsPos = parameter.indexOf('=');
                String parameterValue = parameter.substring(equalsPos+1);
                String parameterName = parameter.substring(0, equalsPos);
                requestParameters.put(parameterName, parameterValue);
            }
        }
        return requestParameters;
    }
/*
    private void handleEchoRequest(Socket socket, Map<String, String> query) throws IOException {
        String status = query.getOrDefault('status', '200');
        String contentType = query.getOrDefault("content-type", "text/plain");
        String body = query.getOrDefault("body", "Hello World!");
        int contentLength = body.length();
        socket.getOutputStream().write(("HTTP/1.1 " + status + " OK\r\n" +
                "Content-length: " + contentLength + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                body).getBytes());
    }

 */

    private Map<String, String> getQueryParameters(String requestTargets) {
        Map<String, String> parameters = new HashMap<>();
        int questionPos = requestTargets.indexOf('?');
        if(questionPos > 0) {
            String quesryString = requestTargets.substring(questionPos + 1);
            for(String parameter : quesryString.split("&")){
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
}
