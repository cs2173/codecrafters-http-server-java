package http.env;

import java.util.Arrays;
import java.util.Optional;

public enum HttpHeader {
    HOST("Host"),
    USER_AGENT("User-Agent"),
    ACCEPT("Accept"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length");

    private final String header;

    HttpHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public static Optional<HttpHeader> parseHeader(String value) {
        return Arrays.stream(HttpHeader.values())
                .filter(v -> v.header.equals(value))
                .findFirst();
    }

}
