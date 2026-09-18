package smartcare;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputValidator {

    public static int readAge(Scanner sc) {
        while (true) {
            try {
                int age = Integer.parseInt(sc.nextLine().trim());

                if (age > 0 && age <= 120) {
                    return age;
                }

                System.out.print("Enter a valid age (1-120): ");

            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    public static String readNonEmpty(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    public static String readDate(Scanner sc) {
        while (true) {
            System.out.print("Enter appointment date (YYYY-MM-DD): ");
            String date = sc.nextLine().trim();

            try {
                LocalDate.parse(date);
                return date;

            } catch (DateTimeParseException e) {
                System.out.println(
                        "Invalid date format. Please use YYYY-MM-DD."
                );
            }
        }
    }

    public static int readChoice(Scanner sc, int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());

                if (choice >= min && choice <= max) {
                    return choice;
                }

            } catch (NumberFormatException ignored) {
                // Ask again below.
            }

            System.out.print(
                    "Enter a valid choice (" + min + "-" + max + "): "
            );
        }
    }
}