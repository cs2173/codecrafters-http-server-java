package http.request;

import http.env.HttpHeader;
import http.env.HttpMethod;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestParser {

    private static class Patterns {
        private static final Supplier<Pattern> PATTERN_REQUEST = () -> Pattern.compile("(^%s)\\s(\\/[\\S]*).*".formatted(HttpMethod.getMethods()));
        private static final Supplier<Pattern> PATTERN_HEADER = () -> Pattern.compile("(\\w+-*\\w+):\\s(.+)(\\r\\n)?");
    }

    private final Scanner in;

    public HttpRequestParser(Scanner in) {
        this.in = in;
    }

    public HttpRequest parseRequest() {
        HttpRequest.Builder request = HttpRequest.builder();
        String line = null;
        Matcher matcher = null;

        while (in.hasNextLine()) {
            line = in.nextLine();

            // request
            matcher = Patterns.PATTERN_REQUEST.get().matcher(line);
            if (matcher.find()) {
                String method = matcher.group(1);
                request = request.forMethod(HttpMethod.valueOf(method));

                String path = matcher.group(2);
                request = request.withPath(path);

                continue;
            }

            // header
            matcher = Patterns.PATTERN_HEADER.get().matcher(line);
            if (matcher.find()) {
                Optional<HttpHeader> header = HttpHeader.parseHeader(matcher.group(1));
                String headerValue = matcher.group(2);
                if (header.isPresent() && !headerValue.isEmpty()) {
                    request = request.addHeader(header.get(), headerValue);
                }

                continue;
            }

            break;
        }

        return request.build();
    }

}

