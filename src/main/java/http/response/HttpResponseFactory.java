package http.response;

import http.env.HttpHeader;
import http.request.HttpRequest;

public class HttpResponseFactory {

    public static HttpResponse getResponse(HttpRequest request) {
        return switch (request.getPath()) {
            case null -> new NoBodyResponse();
            case "/" -> new NoBodyResponse();
            case String p when p.startsWith("/echo") || (p.startsWith("/user-agent") && request.getHeaders().containsKey(HttpHeader.USER_AGENT)) ->
                    new PlainTextResponse(request);
            case String p when p.startsWith("/files") -> new FileResponse(request);
            default -> new NotFoundResponse();
        };
    }

}
