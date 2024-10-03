package http.response;

import http.env.HttpHeader;
import http.env.HttpStatus;

import java.util.Map;
import java.util.Optional;

public class NotFoundResponse extends HttpResponse {

    @Override
    protected HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected Map<HttpHeader, String> getHeaders() {
        return Map.of();
    }

    @Override
    protected Optional<String> getBody() {
        return Optional.empty();
    }

}
