/*
File Name: Problem Set Unit 3
Author: Kaveeshan Sathasivam
Date Created: March 30, 2026
Date Last Modified: April 1, 2026
 */


import java.util.Scanner;

public class ProblemSet {

    //Main Method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input two emails: ");
        String input = scanner.nextLine();

        // Split into two emails (handles ", " or ",") using input.split, using comma as the split character
        String[] emails = input.split(",\\s*");


        //check if user entered 2 emails
        if (emails.length != 2) {
            System.out.println("Please enter exactly two emails.");
            scanner.close();
            return;
        }
        //processing each email
        for (String email : emails) {
            System.out.println(processEmail(email.trim()));  //removes the leading and trailing spaces
        }

        scanner.close();
    }

    // Process email and RETURN result string, this takes the email and then validates it and then formats the output correctly
    public static String processEmail(String email) {
        String result = validateEmail(email);


        if (result.startsWith("Valid")) {
            String[] parts = email.split("@");
            String local = parts[0];
            String domain = parts[1];

            return email + ": " + result +
                    " | Local: " + local +
                    " | Domain: " + domain;
        } else {
            return email + ": " + result;
        }
    }

    // Email validation rules not including the exceptions
    public static String validateEmail(String email) {

        // Rule 1: Exactly one @
        int atCount = email.length() - email.replace("@", "").length();
        if (atCount == 0) return "Invalid: Missing @";
        if (atCount > 1) return "Invalid: Multiple @";

        // Rule 2: Cannot start or end with dot
        if (email.startsWith(".") || email.endsWith(".")) return "Invalid: Starts or ends with dot";

        // Rule 3: No spaces
        if (email.contains(" ")) return "Invalid: Contains spaces";

        //Splits the local and the domain parts - local is before the @, and and domain is after the @
        String[] parts = email.split("@");
        String local = parts[0];
        String domain = parts[1];

        // Rule 4: Local part length - can't be below 1 or above 64 characters long
        if (local.length() < 1) return "Invalid: Local part too short";
        if (local.length() > 64) return "Invalid: Local part too long";

        // Rule 5: Domain must contain at least one dot
        if (!domain.contains(".")) return "Invalid: No dot in domain";

        // Rule 6: Domain extension - must be 2 - 6 characters and can only contain letters
        String extension = domain.substring(domain.lastIndexOf('.') + 1);
        if (extension.length() < 2 || extension.length() > 6) return "Invalid: Invalid domain extension length";
        if (!extension.matches("[a-zA-Z]+")) return "Invalid: Domain extension contains non-letters";

        // Exception B: allowed characters in local a-z, A-Z, 0-9. ._+, invalid if any other characters
        if (!local.matches("[a-zA-Z0-9._+]+")) return "Invalid: Invalid characters in local part";

        // Exception C: Gmail normalization - If the domain is gmail.com, it normalizes dots (treats john.doe@gmail.com same as johndoe@gmail.com).
        if (domain.equalsIgnoreCase("gmail.com")) return "Valid (Gmail normalized)";

        return "Valid";
    }
}