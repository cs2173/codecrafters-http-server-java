package http.response;

import http.env.Environment;
import http.env.HttpHeader;
import http.env.HttpStatus;

import java.util.Map;
import java.util.Optional;

public abstract class HttpResponse {

    protected abstract HttpStatus getStatus();
    protected abstract Map<HttpHeader, String> getHeaders();
    protected abstract Optional<String> getBody();

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        // status
        builder.append("%s %d %s\r\n".formatted(Environment.getInstance().getVersion(), getStatus().getCode(), getStatus().getStatus()));

        // headers
        getHeaders().forEach((k, v) -> builder.append("%s: %s\r\n".formatted(k.getHeader(), v)));
        builder.append("\r\n");

        // getBody
        if (getBody().isPresent()) {
            builder.append(getBody().get());
        }

        return builder.toString();
    }

}
