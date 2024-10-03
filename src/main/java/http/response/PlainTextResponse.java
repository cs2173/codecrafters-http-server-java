package http.response;

import http.env.HttpHeader;
import http.env.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class PlainTextResponse extends HttpResponse {

    private final String body;
    private final Map<HttpHeader, String> headers = new HashMap<>();

    public PlainTextResponse(String requestBody) {
        this.body = Objects.requireNonNullElse(requestBody, "");
        this.headers.put(HttpHeader.CONTENT_TYPE, "text/plain");
        this.headers.put(HttpHeader.CONTENT_LENGTH, String.valueOf(this.body.length()));
    }

    @Override
    protected HttpStatus getStatus() {
        return HttpStatus.OK;
    }

    @Override
    protected Map<HttpHeader, String> getHeaders() {
        return Map.copyOf(headers);
    }

    @Override
    protected Optional<String> getBody() {
        return Optional.of(body);
    }

}
