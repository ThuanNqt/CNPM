package services;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringService {

    public static String convertToString(String value) {
        try {
            // Normalize the input string to decomposed form
            String normalizedString = normalizeString(value);

            // Remove diacritical marks from the normalized string
            String stringWithoutDiacritics = removeDiacriticalMarks(normalizedString);

            return stringWithoutDiacritics;
        } catch (Exception ex) {
            handleException(ex);
        }

        // Return null if an exception occurs
        return null;
    }

    private static String normalizeString(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD);
    }

    private static String removeDiacriticalMarks(String input) {
        Pattern diacriticalMarksPattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return diacriticalMarksPattern.matcher(input).replaceAll("");
    }

    private static void handleException(Exception ex) {
        System.out.println("An exception occurred in StringService.convertToString()");
        System.out.println(ex.getMessage());
    }
}
