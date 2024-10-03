package http.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public final class Files {

    public static Optional<String> readContents(Path filepath) {
        if (!java.nio.file.Files.exists(filepath)) {
            return Optional.empty();
        }

        try {
            String content = java.nio.file.Files.readString(filepath);
            return Optional.ofNullable(content);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static void writeContents(Path filepath, String contents) {
        try {
            java.nio.file.Files.writeString(filepath, contents);
        } catch (IOException e) {
            // noop
        }
    }

    public static long getFileSize(Path filepath) {
        try {
            return java.nio.file.Files.size(filepath);
        } catch (IOException e) {
            return 0L;
        }
    }

}
