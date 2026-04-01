/*
File Name: Problem Set Unit 3
Author: Kaveeshan Sathasivam
Date Created: March 30, 2026
Date Last Modified: April 1, 2026
*/

import java.util.Scanner;

public class EmailValidatorOption3Simple {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input email(s): ");
        String input = scanner.nextLine().trim();

        String firstEmail = "";
        String secondEmail = "";

        // Check for comma
        int commaIndex = input.indexOf(',');
        if (commaIndex == -1) {
            firstEmail = input;
        } else {
            firstEmail = input.substring(0, commaIndex).trim();
            secondEmail = input.substring(commaIndex + 1).trim();
        }

        // Process first email
        if (firstEmail.length() > 0) {
            System.out.println(processEmail(firstEmail));
        }

        // Process second email if exists
        if (secondEmail.length() > 0) {
            System.out.println(processEmail(secondEmail));
        }

        scanner.close();
    }

    public static String processEmail(String email) {
        String result = validateEmail(email);

        if (result.startsWith("Valid")) {
            // Split local and domain
            int atIndex = email.indexOf('@');
            String local = email.substring(0, atIndex);
            String domain = email.substring(atIndex + 1);

            return email + ": " + result + " | Local: " + local + " | Domain: " + domain;
        } else {
            return email + ": " + result;
        }
    }

    public static String validateEmail(String email) {
        int atIndex = email.indexOf('@');
        int lastAtIndex = email.lastIndexOf('@');

        // Rule 1: exactly one '@'
        if (atIndex == -1) return "Invalid: Missing @";
        if (atIndex != lastAtIndex) return "Invalid: Multiple @";

        // Rule 2: cannot start or end with dot
        if (email.charAt(0) == '.' || email.charAt(email.length() - 1) == '.') {
            return "Invalid: Starts or ends with dot";
        }

        // Rule 3: no spaces
        if (email.indexOf(' ') != -1) return "Invalid: Contains spaces";

        // Split local and domain
        String local = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        // Rule 4: local length
        if (local.length() < 1) return "Invalid: Local part too short";
        if (local.length() > 64) return "Invalid: Local part too long";

        // Rule 5: domain must contain at least one dot
        if (domain.indexOf('.') == -1) return "Invalid: No dot in domain";

        // Rule 6: domain extension length (2-6) and letters only
        int lastDot = domain.lastIndexOf('.');
        if (lastDot == -1) return "Invalid: No dot in domain";

        String extension = domain.substring(lastDot + 1);
        if (extension.length() < 2) return "Invalid: Domain extension too short";
        if (extension.length() > 6) return "Invalid: Domain extension too long";

        for (int i = 0; i < extension.length(); i++) {
            char c = extension.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                return "Invalid: Domain extension contains non-letters";
            }
        }

        // Exception B: allowed characters in local
        for (int i = 0; i < local.length(); i++) {
            char c = local.charAt(i);
            if (!((c >= 'a' && c <= 'z') ||
                  (c >= 'A' && c <= 'Z') ||
                  (c >= '0' && c <= '9') ||
                  c == '.' || c == '+' || c == '_')) {
                return "Invalid: Invalid characters in local part";
            }
        }

        // Exception C: Gmail normalization
        if (domain.equalsIgnoreCase("gmail.com")) return "Valid (Gmail normalized)";

        return "Valid";
    }
}