package net.nemisolv.identity;

import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class MainTest {

    public static  String generateUsername(String firstName, String lastName) {
        // Normalize the names to decompose the characters and remove diacritics
        String convertedFirstName = normalizeAndRemoveDiacritics(firstName);
        String convertedLastName = normalizeAndRemoveDiacritics(lastName);

        // Ensure lowercase for username
        String username = (convertedFirstName + convertedLastName).toLowerCase();

        // If last name is empty, use first name without spaces
        if (firstName != null && (lastName == null || lastName.isEmpty())) {
            username = convertedFirstName.replaceAll("\\s+", "").toLowerCase();
        }

        return username;
    }

    private static String normalizeAndRemoveDiacritics(String input) {
        if (!StringUtils.hasLength(input)) {
            return "";
        }

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String removeUnicode = normalized.replaceAll("\\p{M}","");
        System.out.println(removeUnicode);
        return removeUnicode;
    }

    public static void main(String[] args) {
        String name = "Nam Vũ";
        String username = generateUsername(name, null);
        System.out.println(username);
    }
}
