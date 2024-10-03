package http.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

public final class Gzip {

    public static byte[] compress(String content) {
        if (content == null || content.isBlank()) {
            return null;
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(content.length());
             GZIPOutputStream gzip = new GZIPOutputStream(outputStream)) {
            gzip.write(content.getBytes(StandardCharsets.UTF_8));
            gzip.finish();
            gzip.flush();
            return outputStream.toByteArray();
        } catch (IOException ioe) {
            System.out.printf("IOException: %s%n", ioe.getMessage());
            return null;
        }
    }

}
