import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

class Account {
    private String accountID;
    private String username;
    private String role;
    private String password;
    private String phoneNumber;
    private int lastId = 0;

    // Default constructor
    public Account(String accountID, String username, String role, String password, String phoneNumber) {
        this.accountID = accountID;
        this.username = username;
        this.role = role;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Method to find last ID for ID auto increment
    private void findLastId() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] AccountData = currentLine.split(", ");
                lastId = Integer.parseInt(AccountData[0]);
            }
            reader.close();
            if (lastId == 0) {
                lastId = 1;
            } else {
                lastId++;
            }
        } catch (Exception e) {
            System.out.println("Error while finding last ID: " + e.getMessage());
        }
    }

    // Method read use account creation input
    public void readAccountInput(Scanner input, String choice) {
        findLastId();
        accountID = Integer.toString(lastId);
        System.out.print("Enter username: ");
        username = input.nextLine();
        System.out.print("Enter password: ");
        password = input.nextLine();
        System.out.print("Enter phone number: ");
        phoneNumber = input.nextLine();
        if (choice.equals("a")) {
            role = "Teacher";
        } else {
            role = "Student";
        }
    }

    // Method create account for teacher
    public void createAccount() {
        try {
            FileWriter writer = new FileWriter("Accounts.txt", true);
            writer.write(accountID + ", " + username + ", " + role + ", " + password + ", " + phoneNumber + "\n");
            writer.close();
            System.out.println("");
            System.out.println("Account creation successful!\n");
        } catch (Exception e) {
            System.out.println("Error while creating account: " + e.getMessage());
        }
    }
}

public class CreateAccount {
    public static void main() {
        Scanner input = new Scanner(System.in);
        Account account = new Account("", "", "", "", "");
        // User Input
        while (true) {
            System.out.println("----------Create Account----------");
            System.out.println("a. Create account for teacher");
            System.out.println("b. Create account for student");
            System.out.println("c. Exit");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "a":
                    account.readAccountInput(input, choice);
                    account.createAccount();
                    break;
                case "b":
                    account.readAccountInput(input, choice);
                    account.createAccount();
                    break;
                case "c":
                    return;
                default:
                    System.out.println("Invalid choice!\n");
            }
        }
    }
}
