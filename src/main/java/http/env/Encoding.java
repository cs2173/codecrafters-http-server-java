package http.env;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Encoding {
    GZIP("gzip");

    private final String encoding;
    Encoding(String encoding) {
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }

    public static boolean isValid(String encoding) {
        if (encoding == null || encoding.isBlank()) {
            return false;
        }
        return Arrays.stream(Encoding.values())
                .map(Encoding::getEncoding)
                .anyMatch(encoding::contains);
    }

    public static String extractValidEncodings(String encoding) {
        if (encoding == null || encoding.isBlank() || !isValid(encoding)) {
            return null;
        }
        return Arrays.stream(Encoding.values())
                .map(Encoding::getEncoding)
                .filter(encoding::contains)
                .collect(Collectors.joining(", "));
    }

}
