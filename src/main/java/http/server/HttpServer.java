package http.server;

import http.env.Environment;

import java.io.IOException;
import java.net.ServerSocket;

public final class HttpServer {

    public HttpServer() { }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(Environment.PORT)) {
            serverSocket.setReuseAddress(true);
            serverSocket.accept();
            System.out.println("accepted new connection");
        } catch (IOException e) {
            System.out.printf("IOException: %s%n", e.getMessage());
        }
    }

}