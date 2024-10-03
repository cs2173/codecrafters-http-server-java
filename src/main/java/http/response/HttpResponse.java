package http.response;

import http.env.Environment;
import http.env.HttpStatus;
import http.util.Strings;

public class HttpResponse {

    public static String getResponse(String path) {
        return switch (path) {
            case "/" -> "%s %d %s\r\n\r\n".formatted(Environment.VERSION, HttpStatus.OK.getCode(), HttpStatus.OK.getStatus());
            case String p when p.startsWith("/echo") -> {
                String word = Strings.afterLast(p, "/echo/");
                String status = "%s %d %s\r\n".formatted(Environment.VERSION, HttpStatus.OK.getCode(), HttpStatus.OK.getStatus());
                String body = "Content-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s".formatted(word.length(), word);
                yield "%s%s".formatted(status, body);
            }
            default -> "%s %d %s\r\n\r\n".formatted(Environment.VERSION, HttpStatus.NOT_FOUND.getCode(), HttpStatus.NOT_FOUND.getStatus());
        };
    }

}
