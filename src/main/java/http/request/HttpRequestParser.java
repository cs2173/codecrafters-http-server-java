package http.request;

import http.env.HttpHeader;
import http.env.HttpMethod;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestParser {

    private static class Patterns {
        private static final Supplier<Pattern> PATTERN_REQUEST = () -> Pattern.compile("(^%s)\\s(\\/[\\S]*).*".formatted(HttpMethod.getMethods()));
        private static final Supplier<Pattern> PATTERN_HEADER = () -> Pattern.compile("(\\w+-*\\w+):\\s(.+)(\\r\\n)?");
    }

    private final InputStream in;

    public HttpRequestParser(InputStream in) {
        this.in = in;
    }

    private List<String> parseInputStream() throws IOException {
        StringBuilder request = new StringBuilder();
        while (in.available() != 0) {
            int data = in.read();
            request.append((char) data);
        }
        return List.of(request.toString().split("\r\n"));
    }

    public HttpRequest parseRequest() throws IOException {
        List<String> input = parseInputStream();

        HttpRequest.Builder request = HttpRequest.builder();

        Matcher matcher = null;

        for (String line : input) {
            if (line.isBlank()) {
                continue;
            }

            matcher = Patterns.PATTERN_REQUEST.get().matcher(line);
            if (matcher.find()) {
                String method = matcher.group(1);
                request = request.forMethod(HttpMethod.valueOf(method));

                String path = matcher.group(2);
                request = request.withPath(path);

                continue;
            }

            matcher = Patterns.PATTERN_HEADER.get().matcher(line);
            if (matcher.find()) {
                Optional<HttpHeader> header = HttpHeader.parseHeader(matcher.group(1));
                String headerValue = matcher.group(2);
                if (header.isPresent() && !headerValue.isEmpty()) {
                    request = request.addHeader(header.get(), headerValue);
                }

                continue;
            }

            request = request.withBody(line);
        }

        return request.build();
    }

}

