package http.response;

import http.env.HttpHeader;
import http.env.HttpStatus;

import java.util.Map;

public class NoBodyResponse extends HttpResponse {

    @Override
    protected HttpStatus getResponseStatus() {
        return HttpStatus.OK;
    }

    @Override
    protected Map<HttpHeader, String> getResponseHeaders() {
        return Map.of();
    }

    @Override
    public byte[] getBody() {
        return null;
    }

}
