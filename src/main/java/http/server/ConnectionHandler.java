package http.server;

import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

    private final Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {
            System.out.println("accepted new connection");
            HttpRequestParser parser = new HttpRequestParser(in);
            HttpRequest request = parser.parseRequest();
            HttpResponse response = HttpResponseFactory.getResponse(request);
            out.write(response.getStatus());
            out.write(response.getHeaders());
            out.write(response.getBody());
            out.flush();
        } catch (IOException e) {
            System.out.printf("IOException: %s%n", e.getMessage());
        }
    }

}
