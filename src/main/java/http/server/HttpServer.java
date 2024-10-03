package http.server;

import http.env.Environment;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public final class HttpServer {

    public HttpServer() {
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(Environment.PORT)) {
            serverSocket.setReuseAddress(true);
            try (Socket incoming = serverSocket.accept();
                 OutputStream os = incoming.getOutputStream();
                 PrintWriter out = new PrintWriter(os, true, StandardCharsets.UTF_8)) {
                System.out.println("accepted new connection");
                out.write("HTTP/1.1 200 OK\r\n\r\n");
                out.flush();
            }
        } catch (IOException e) {
            System.out.printf("IOException: %s%n", e.getMessage());
        }
    }

}