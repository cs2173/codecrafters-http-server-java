package http.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Strings {

    public static String afterLast(String word, String delimiter) {
        String inputWord = Objects.requireNonNullElse(word, "");
        if (inputWord.isBlank()) {
            return "";
        }

        String inputDelimiter = Objects.requireNonNull(delimiter, "");
        if (inputDelimiter.isBlank()) {
            return "";
        }

        String regex = "%s([^/]+)".formatted(delimiter);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(word);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return word;
    }

}

