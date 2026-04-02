/*
File Name: Problem Set Unit 3
Author: Kaveeshan Sathasivam
Date Created: March 30, 2026
Date Last Modified: April 2, 2026
*/

import java.util.Scanner;

public class EmailValidatorOption3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input email(s): ");
        String input = scanner.nextLine().trim();

        String firstEmail = "";
        String secondEmail = "";

        // Split emails using comma 
        int commaIndex = input.indexOf(',');
        //index at -1 means that it doesn't exist
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

        // Process second email
        if (secondEmail.length() > 0) {
            System.out.println(processEmail(secondEmail));
        }

        scanner.close();
    }

    public static String processEmail(String email) {
        String result = validateEmail(email);

        if (result.startsWith("Valid")) {
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
        if (atIndex == -1) {
            return "Invalid: Missing @";
        } else if (atIndex != lastAtIndex) {
            return "Invalid: Multiple @";
        }

        // Rule 2: cannot start or end with dot
        if (email.charAt(0) == '.') {
            return "Invalid: Starts or ends with dot";
        } else if (email.charAt(email.length() - 1) == '.') {
            return "Invalid: Starts or ends with dot";
        }

        // Rule 3: no spaces
        if (email.indexOf(' ') != -1) {
            return "Invalid: Contains spaces";
        }

        // Split local and domain
        String local = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        // Rule 4: local length
        if (local.length() < 1) {
            return "Invalid: Local part too short";
        } else if (local.length() > 64) {
            return "Invalid: Local part too long";
        }

        // Rule 5: domain must contain at least one dot
        int lastDot = domain.lastIndexOf('.');
        if (lastDot == -1) {
            return "Invalid: No dot in domain";
        }

        // Rule 6: domain extension length (2-6) and letters only
        String extension = domain.substring(lastDot + 1);
        if (extension.length() < 2) {
            return "Invalid: Domain extension too short";
        } else if (extension.length() > 6) {
            return "Invalid: Domain extension too long";
        }

        if (!isAllLetters(extension, 0)) {
            return "Invalid: Domain extension contains non-letters";
        }

        if (!isValidLocal(local, 0)) {
            return "Invalid: Invalid characters in local part";
        }

        // Gmail normalization only after all validations pass
        if (domain.equalsIgnoreCase("gmail.com")) {
            return "Valid (Gmail normalized)";
        }

        return "Valid";
    }

    // method to check if all letters 
    public static boolean isAllLetters(String s, int index) {
        if (index >= s.length()) {
            return true;
        } else {
            char c = s.charAt(index);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                return false;
            } else {
                return isAllLetters(s, index + 1);
            }
        }
    }

    // method to check allowed characters in local 
    public static boolean isValidLocal(String local, int index) {
        if (index >= local.length()) {
            return true;
        } else {
            char c = local.charAt(index);
            if (!((c >= 'a' && c <= 'z') ||
                  (c >= 'A' && c <= 'Z') ||
                  (c >= '0' && c <= '9') ||
                  c == '.' || c == '+' || c == '_')) {
                return false;
            } else {
                return isValidLocal(local, index + 1);
            }
        }
    }
}