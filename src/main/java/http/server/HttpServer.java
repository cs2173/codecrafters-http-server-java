package http.server;

import http.env.Environment;
import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class HttpServer {

    public HttpServer() {
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(Environment.PORT)) {
            serverSocket.setReuseAddress(true);
            try (Socket incoming = serverSocket.accept();
                 Scanner in = new Scanner(incoming.getInputStream());
                 PrintWriter out = new PrintWriter(incoming.getOutputStream(), true, StandardCharsets.UTF_8)) {
                System.out.println("accepted new connection");
                HttpRequestParser parser = new HttpRequestParser(in);
                HttpRequest request = parser.parseRequest();
                String response = HttpResponse.getResponse(request);
                out.write(response);
                out.flush();
            }
        } catch (IOException e) {
            System.out.printf("IOException: %s%n", e.getMessage());
        }
    }

}