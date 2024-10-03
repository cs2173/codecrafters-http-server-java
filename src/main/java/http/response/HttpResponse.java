package http.response;

import http.env.Environment;
import http.env.HttpHeader;
import http.env.HttpStatus;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public abstract class HttpResponse {

    protected abstract HttpStatus getResponseStatus();

    protected abstract Map<HttpHeader, String> getResponseHeaders();

    public abstract byte[] getBody();

    public byte[] getStatus() {
        String status = "%s %d %s\r\n".formatted(Environment.getInstance().getVersion(), getResponseStatus().getCode(), getResponseStatus().getStatus());
        return status.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getHeaders() {
        StringBuilder builder = new StringBuilder();
        getResponseHeaders().forEach((k, v) -> builder.append("%s: %s\r\n".formatted(k.getHeader(), v)));
        builder.append("\r\n");
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }

}
