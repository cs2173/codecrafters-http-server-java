package http.response;

import http.env.Encoding;
import http.env.HttpHeader;
import http.env.HttpStatus;
import http.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class PlainTextResponse extends HttpResponse {

    private final String body;
    private final Map<HttpHeader, String> headers = new HashMap<>();

    public PlainTextResponse(HttpRequest request) {
        this.body = Objects.requireNonNullElse(request.getPathValue(), "");

        this.headers.put(HttpHeader.CONTENT_TYPE, "text/plain");
        this.headers.put(HttpHeader.CONTENT_LENGTH, String.valueOf(this.body.length()));

        String encoding = request.getHeaders().get(HttpHeader.ACCEPT_ENCODING);
        if (Encoding.isValid(encoding)) {
            this.headers.put(HttpHeader.CONTENT_ENCODING, Encoding.extractValidEncodings(encoding));
        }
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
