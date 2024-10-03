package http.response;

import http.env.Environment;
import http.env.HttpHeader;
import http.env.HttpStatus;
import http.util.Files;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FileResponse extends HttpResponse {

    private final Path filepath;
    private final Optional<String> contents;
    private final HttpStatus status;
    private final Map<HttpHeader, String> headers = new HashMap<>();

    public FileResponse(String requestBody) {
        String directory = Objects.requireNonNullElse(Environment.getInstance().getDirectory(), "");
        String filename = Objects.requireNonNullElse(requestBody, "");
        this.filepath = Path.of(directory, filename);

        this.contents = Files.readContents(filepath);

        this.status = this.contents.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        if (this.contents.isPresent()) {
            this.headers.put(HttpHeader.CONTENT_TYPE, "application/octet-stream");
            this.headers.put(HttpHeader.CONTENT_LENGTH, String.valueOf(this.contents.get().length()));
        }
    }

    @Override
    protected HttpStatus getStatus() {
        return status;
    }

    @Override
    protected Map<HttpHeader, String> getHeaders() {
        return Map.copyOf(headers);
    }

    @Override
    protected Optional<String> getBody() {
        return contents;
    }

}
