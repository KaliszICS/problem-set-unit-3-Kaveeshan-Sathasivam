/* 
File: Problem Set Unit 3
Author: Kaveeshan Sathasivam
Date Created: March 30, 2026
Date Last Modified: March 30, 2026
 */

import java.util.Scanner;

public class ProblemSet {

    public static void main(String args[]) {

        Scanner input = new Scanner(System.in);

        System.out.print("Input two emails: ");
        String line = input.nextLine();

        // Split into two emails
        String[] emails = line.split(",\\s*");

        for (String email : emails) {
            validateEmail(email.trim());
        }

        input.close();
    }

    public static void validateEmail(String email) {

        String local = "";
        String domain = "";

        // First Rule: Can only be exactly one @
        int atCount = 0;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                atCount++;
            }
        }

        if (atCount == 0) {
            System.out.println(email + ": Invalid: Missing @");
            return;
        }
        if (atCount > 1) {
            System.out.println(email + ": Invalid: Multiple @");
            return;
        }

        int atIndex = email.indexOf('@');
        local = email.substring(0, atIndex);
        domain = email.substring(atIndex + 1);

        // Second Rule: It can't start or end with a dot
        if (email.startsWith(".") || email.endsWith(".")) {
            System.out.println(email + ": Invalid: Starts or ends with dot");
            return;
        }

        // Third Rule: No spaces
        if (email.contains(" ")) {
            System.out.println(email + ": Invalid: Contains spaces");
            return;
        }

        // Rule 4: Local length - can't be 0 and can't be over 64 characters
        if (local.length() < 1) {
            System.out.println(email + ": Invalid: Local part too short");
            return;
        }
        if (local.length() > 64) {
            System.out.println(email + ": Invalid: Local part too long");
            return;
        }

        // Rule 5: The domain must have dot
        if (!domain.contains(".")) {
            System.out.println(email + ": Invalid: No dot in domain");
            return;
        }

        // Get the extension
        int lastDot = domain.lastIndexOf('.');
        String extension = domain.substring(lastDot + 1);

        // Seventh Rule: extension length - cant be less than 2 or greater than 6
        if (extension.length() < 2 || extension.length() > 6) {
            System.out.println(email + ": Invalid: Invalid domain extension length");
            return;
        }

        // Extra: extension must be letters only
        for (int i = 0; i < extension.length(); i++) {
            if (!Character.isLetter(extension.charAt(i))) {
                System.out.println(email + ": Invalid: Domain extension contains non-letters");
                return;
            }
        }

        // ===== Exceptions =====

        // Gmail normalization check
        if (domain.equalsIgnoreCase("gmail.com")) {
            System.out.println(email + ": Valid (Gmail normalized) | Local: "
                    + local + " | Domain: " + domain);
            return;
        }

        // If valid email
        System.out.println(email + ": Valid | Local: " + local + " | Domain: " + domain);
    }
}
