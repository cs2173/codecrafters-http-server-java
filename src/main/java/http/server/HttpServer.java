package http.server;

import http.env.Environment;
import http.env.HttpMethod;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

                String regex = "^%s\\s(\\/[\\S]*).*".formatted(HttpMethod.getMethods());
                Pattern pattern = Pattern.compile(regex);

                String line, path = null;
                while (in.hasNextLine()) {
                    line = in.nextLine();
                    System.out.println(line);
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        path = matcher.group(1);
                        break;
                    }
                }

                if ("/".equals(path)) {
                    out.write("HTTP/1.1 200 OK\r\n\r\n");
                } else {
                    out.write("HTTP/1.1 404 Not Found\r\n\r\n");
                }

                out.flush();
            }
        } catch (IOException e) {
            System.out.printf("IOException: %s%n", e.getMessage());
        }
    }

}