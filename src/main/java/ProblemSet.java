/* 
File: Problem Set Unit 3
Author: Kaveeshan Sathasivam
Date Created: March 30, 2026
Date Last Modified: March 31, 2026
 */

import java.util.Scanner;

public class ProblemSet {

    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Input two emails: ");
        String input = scanner.nextLine();

        // Split into two emails (handles ", " or ",")
        String[] emails = input.split(",\\s*");

        if (emails.length != 2) {
            System.out.println("Please enter exactly two emails.");
            return;
        }

        for (String email : emails) {
            processEmail(email.trim());
        }

        scanner.close();
    }

    // Process each email
    public static void processEmail(String email) {
        String result = validateEmail(email);

        if (result.startsWith("Valid")) {
            String[] parts = email.split("@");
            String local = parts[0];
            String domain = parts[1];

            System.out.println(email + ": " + result +
                    " | Local: " + local +
                    " | Domain: " + domain);
        } else {
            System.out.println(email + ": " + result);
        }
    }

    // Email Validation Rules/Logic
    public static String validateEmail(String email) {

        // Rule 1: Exactly one @
        int atCount = email.length() - email.replace("@", "").length();
        if (atCount == 0) return "Invalid: Missing @";
        if (atCount > 1) return "Invalid: Multiple @";

        // Rule 2: Cannot start or end with a dot
        if (email.startsWith(".") || email.endsWith(".")) {
            return "Invalid: Starts or ends with dot";
        }

        // Rule 3: No spaces
        if (email.contains(" ")) {
            return "Invalid: Contains spaces";
        }

        String[] parts = email.split("@");
        String local = parts[0];
        String domain = parts[1];

        // Rule 4: Local length - cant be under 1 or above 64 characters
        if (local.length() < 1) return "Invalid: Local part too short";
        if (local.length() > 64) return "Invalid: Local part too long";

        // Rule 5: Domain must contain dot
        if (!domain.contains(".")) {
            return "Invalid: No dot in domain";
        }

        // Rule 6: Domain extension
        String extension = domain.substring(domain.lastIndexOf('.') + 1);

        if (extension.length() < 2 || extension.length() > 6) {
            return "Invalid: Invalid domain extension length";
        }

        if (!extension.matches("[a-zA-Z]+")) {
            return "Invalid: Domain extension contains non-letters";
        }

        // Exception B: allowed characters in local
        if (!local.matches("[a-zA-Z0-9._+]+")) {
            return "Invalid: Invalid characters in local part";
        }

        // Exception C: Gmail normalization
        if (domain.equalsIgnoreCase("gmail.com")) {
            return "Valid (Gmail normalized)";
        }

        return "Valid";
    }
}
