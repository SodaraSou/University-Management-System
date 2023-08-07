import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

class Login {
    // Method for login
    public void login(Scanner input) {
        System.out.print("Enter username: ");
        String loginUsername = input.nextLine();
        System.out.print("Enter password: ");
        String loginPassword = input.nextLine();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"));

            String currentLine;
            // Read file line by line
            while ((currentLine = reader.readLine()) != null) {
                // Split line by comma and space
                String[] accountDetails = currentLine.split(", ");
                // Check if username and password matches
                if (loginUsername.equals(accountDetails[1]) && loginPassword.equals(accountDetails[3])) {
                    System.out.println("");
                    System.out.println("Hi " + accountDetails[2] + ": " + accountDetails[1] + "\n");
                    reader.close();
                    return;
                }
            }
            System.out.println("");
            System.out.println("Invalid username or password!\n");
            reader.close();
        } catch (Exception e) {
            System.out.println("Error while login to account: " + e.getMessage());
        }
    }
}

public class LoginToAccount {
    public static void main() {
        Scanner input = new Scanner(System.in);
        Login login = new Login();
        // User Input
        while (true) {
            System.out.println("----------Login----------");
            System.out.println("a. Login");
            System.out.println("b. Exit");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "a":
                    login.login(input);
                    break;
                case "b":
                    return;
                default:
                    System.out.println("Invalid choice!\n");
            }
        }
    }
}
