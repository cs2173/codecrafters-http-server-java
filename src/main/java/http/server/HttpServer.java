package http.server;

import http.env.Environment;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class HttpServer {

    public HttpServer() {
    }

    public void start() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
             ServerSocket serverSocket = new ServerSocket(Environment.getInstance().getPort())) {
            serverSocket.setReuseAddress(true);
            while (true) {
                Socket socket = serverSocket.accept();
                executor.submit(new ConnectionHandler(socket));
            }
        } catch (IOException e) {
            System.out.printf("IOException: %s%n", e.getMessage());
        }
    }

}