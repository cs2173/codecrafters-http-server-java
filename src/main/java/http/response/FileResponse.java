package http.response;

import http.env.*;
import http.request.HttpRequest;
import http.util.Files;

import java.nio.charset.StandardCharsets;
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

    public FileResponse(HttpRequest request) {
        String directory = Objects.requireNonNullElse(Environment.getInstance().getDirectory(), "");
        String filename = Objects.requireNonNullElse(request.getPathValue(), "");
        this.filepath = Path.of(directory, filename);

        boolean shouldCreateFile = request.getMethod() == HttpMethod.POST;
        if (shouldCreateFile) {
            Files.writeContents(this.filepath, request.getBody());
        }

        this.contents = Files.readContents(this.filepath);

        this.status = switch (this.contents) {
            case Optional<String> c when c.isPresent() && shouldCreateFile -> HttpStatus.CREATED;
            case Optional<String> c when c.isPresent() -> HttpStatus.OK;
            default -> HttpStatus.NOT_FOUND;
        };

        if (this.contents.isPresent()) {
            this.headers.put(HttpHeader.CONTENT_TYPE, "application/octet-stream");
            if (shouldCreateFile) {
                this.headers.put(HttpHeader.CONTENT_LENGTH, String.valueOf(Files.getFileSize(this.filepath)));
            } else {
                this.headers.put(HttpHeader.CONTENT_LENGTH, String.valueOf(this.contents.get().length()));
            }
        }
    }

    @Override
    protected HttpStatus getResponseStatus() {
        return status;
    }

    @Override
    protected Map<HttpHeader, String> getResponseHeaders() {
        return Map.copyOf(headers);
    }

    @Override
    public byte[] getBody() {
        return contents.map(s -> s.getBytes(StandardCharsets.UTF_8)).orElse(null);
    }

}
