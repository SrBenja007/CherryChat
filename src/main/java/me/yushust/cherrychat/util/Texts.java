package me.yushust.cherrychat.util;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Texts {

    /*public static final Pattern COLOR_PATTERN = Pattern.compile("(?i)&([0-9A-fa-f])");
    public static final Pattern MAGIC_PATTERN = Pattern.compile("(?i)&([Kk])");
    public static final Pattern BOLD_REGEX = Pattern.compile("(?i)&([L])");
    public static final Pattern STRIKETHROUGH_REGEX = Pattern.compile("(?i)&([M])");
    public static final Pattern UNDERLINE_REGEX = Pattern.compile("(?i)&([N])");
    public static final Pattern ITALIC_REGEX = Pattern.compile("(?i)&([O])");
    public static final Pattern RESET_REGEX = Pattern.compile("(?i)&([R])");*/

    public static final String ALPHANUMERICS = "AaBbCcDdEeFfGgHhIiJjKkMmNnLlOoPpQqRrSsTtUuVvWwXxYyZz0123456789";

    public static String decreaseAlphaNumerics(String text, int min) {
        return decreaseAlphaNumerics(text, min, 257);
    }

    public static String decreaseAlphaNumerics(String text, int min, int max) {
        String textWithoutFlood = text;
        if(min < 0) {
            min = 0;
        }
        if(max <= min) {
            max = 257;
        }
        for(char letter : ALPHANUMERICS.toCharArray()) {
            for(int count = max - 1; count >= min; count--) {
                textWithoutFlood = textWithoutFlood.replace(
                        Strings.repeat(String.valueOf(letter), count),
                        String.valueOf(letter)
                );
            }
        }
        return textWithoutFlood;
    }

    public static int count(String text, String value) {
        int count = 0;
        while(text.contains(value)) {
            text = text.replaceFirst(value, "~~ignore~~");
            count++;
        }
        return count;
    }

    public static String toLowerCase(String text, int minLetters) {
        String textFormatted = text;
        if(minLetters < 0) minLetters = 0;

        int upperCaseChars = 0;
        for(char c : textFormatted.toCharArray()) {
            if(Character.isUpperCase(c)) {
                upperCaseChars++;
            }
        }
        if(upperCaseChars >= minLetters) {
            textFormatted = textFormatted.toLowerCase();
        }

        return textFormatted;
    }

    public static String capitalizeFirst(String text) {
        if(text == null || text.isEmpty()) return text;
        String firstLetter = String.valueOf(text.charAt(0));
        return firstLetter.toUpperCase() + text.substring(1);
    }

}
