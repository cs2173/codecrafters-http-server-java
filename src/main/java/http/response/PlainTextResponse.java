package http.response;

import http.env.Encoding;
import http.env.HttpHeader;
import http.env.HttpStatus;
import http.request.HttpRequest;
import http.util.Gzip;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class PlainTextResponse extends HttpResponse {

    private final byte[] body;
    private final Map<HttpHeader, String> headers = new HashMap<>();

    public PlainTextResponse(HttpRequest request) {
        String value = Objects.requireNonNullElse(request.getPathValue(), "");

        String encoding = request.getHeaders().get(HttpHeader.ACCEPT_ENCODING);

        if (Encoding.isGzipRequested(encoding)) {
            this.body = Gzip.compress(value);
            this.headers.put(HttpHeader.CONTENT_ENCODING, Encoding.GZIP.getEncoding());
        } else {
            this.body = value.getBytes(StandardCharsets.UTF_8);
            this.headers.put(HttpHeader.CONTENT_LENGTH, String.valueOf(value.length()));
        }

        this.headers.put(HttpHeader.CONTENT_TYPE, "text/plain");
        if (this.body != null) {
            this.headers.put(HttpHeader.CONTENT_LENGTH, String.valueOf(this.body.length));
        }
    }

    @Override
    protected HttpStatus getResponseStatus() {
        return HttpStatus.OK;
    }

    @Override
    protected Map<HttpHeader, String> getResponseHeaders() {
        return Map.copyOf(headers);
    }

    @Override
    public byte[] getBody() {
        return body;
    }

}
