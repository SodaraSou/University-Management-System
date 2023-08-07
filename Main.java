import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("----------University Management System----------");
            System.out.println("a. Manage Faculty");
            System.out.println("b. Manage Department");
            System.out.println("c. Manage Students");
            System.out.println("d. Enroll Students into Department");
            System.out.println("e. Manage Courses");
            System.out.println("f. Manage Teachers");
            System.out.println("g. Assign Courses to Teacher");
            System.out.println("h. Create Teachers and Students Account");
            System.out.println("i. Login");
            System.out.println("j. Exit");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "a":
                    ManageFaculty.main();
                    break;
                case "b":
                    ManageDepartment.main();
                    break;
                case "c":
                    ManageStudent.main();
                    break;
                case "d":
                    EnrollStudent.main();
                    break;
                case "e":
                    ManageCourse.main();
                    break;
                case "f":
                    ManageTeacher.main();
                    break;
                case "g":
                    AssignCourse.main();
                    break;
                case "h":
                    CreateAccount.main();
                    break;
                case "i":
                    LoginToAccount.main();
                    break;
                case "j":
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            if (choice.equals("j")) {
                break;
            }
        }
        input.close();
        System.exit(0);
    }
}
