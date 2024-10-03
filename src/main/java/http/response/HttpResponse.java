package http.response;

import http.env.Environment;
import http.env.HttpHeader;
import http.env.HttpStatus;
import http.request.HttpRequest;
import http.util.Strings;

public class HttpResponse {

    public static String getResponse(HttpRequest request) {
        return switch (request.getPath()) {
            case "/" -> "%s %d %s\r\n\r\n".formatted(Environment.VERSION, HttpStatus.OK.getCode(), HttpStatus.OK.getStatus());
            case String p when p.startsWith("/echo") -> {
                String value = Strings.afterLast(p, "/echo/");
                String status = "%s %d %s\r\n".formatted(Environment.VERSION, HttpStatus.OK.getCode(), HttpStatus.OK.getStatus());
                String body = "Content-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s".formatted(value.length(), value);
                yield "%s%s".formatted(status, body);
            }
            case String p when p.startsWith("/user-agent") && request.getHeaders().containsKey(HttpHeader.USER_AGENT) -> {
                String value = request.getHeaders().get(HttpHeader.USER_AGENT);
                String status = "%s %d %s\r\n".formatted(Environment.VERSION, HttpStatus.OK.getCode(), HttpStatus.OK.getStatus());
                String body = "Content-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s".formatted(value.length(), value);
                yield "%s%s".formatted(status, body);
            }
            default -> "%s %d %s\r\n\r\n".formatted(Environment.VERSION, HttpStatus.NOT_FOUND.getCode(), HttpStatus.NOT_FOUND.getStatus());
        };
    }

}
