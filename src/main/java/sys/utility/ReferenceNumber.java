package sys.utility;

import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReferenceNumber {

    /**
     * Generates a random reference number string.
     * Format example: REF-20251009-482193
     *
     * @return a randomly generated reference number string
     */
    public static String generateReferenceNumber() {
        // Create a random 6-digit number
        Random rand = new Random();
        int randomNum = 100000 + rand.nextInt(900000); // ensures 6 digits

        // Add current date for traceability
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Combine them into a readable reference format
        return "REF-" + date + "-" + randomNum;
    }
}