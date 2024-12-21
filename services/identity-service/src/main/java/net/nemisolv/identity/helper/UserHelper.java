package net.nemisolv.identity.helper;

import lombok.RequiredArgsConstructor;
import net.nemisolv.identity.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserHelper {
    private  final UserRepository userRepo;


    public String generateUsername (String name) {
        return  generateUsername(name, null);
    }

    public  String generateUsername(String firstName, String lastName) {

        // Normalize the names to decompose the characters
        String convertedFirstName = normalizeAndRemoveDiacritics(firstName).toLowerCase();
        String convertedLastName = normalizeAndRemoveDiacritics(lastName).toLowerCase();
        String username = convertedFirstName + convertedLastName;
        if(firstName !=null && (lastName == null || lastName.isEmpty())) {
            username = convertedFirstName.replace(" ", "");
        }

        while(userRepo.findByUsername(username).isPresent()) {
            username = username + (int)(Math.random() * 1000);
        }

        return username;
    }
    private String normalizeAndRemoveDiacritics(String input) {
        if(!StringUtils.hasLength(input)) {
            return "";
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }




}
