package http.server;

import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConnectionHandler implements Runnable {

    private final Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Scanner in = new Scanner(socket.getInputStream());
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8)) {
            System.out.println("accepted new connection");
            HttpRequestParser parser = new HttpRequestParser(in);
            HttpRequest request = parser.parseRequest();
            String response = HttpResponse.getResponse(request);
            out.write(response);
            out.flush();
        } catch (IOException e) {
            System.out.printf("IOException: %s%n", e.getMessage());
        }
    }

}
