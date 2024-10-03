package http.env;

import java.util.Arrays;

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
        return Arrays.stream(Encoding.values())
                .map(Encoding::getEncoding)
                .filter(e -> e.equalsIgnoreCase(encoding))
                .count() == 1;
    }
}
