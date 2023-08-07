import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Enroll {
    private String enrollId;
    private String studentId;
    private String studentName;
    private String deptId;
    private String deptName;
    private int lastId = 0;

    // Default constructor  
    public Enroll(String enrollId, String studentId, String studentName, String deptId, String deptName) {
        this.enrollId = enrollId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.deptId = deptId;
        this.deptName = deptName;
    }

    // Method to find last ID for ID auto increment
    private void findLastId() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Enrolls.txt"));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] facultyData = currentLine.split(", ");
                lastId = Integer.parseInt(facultyData[0].substring(1));
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

    // Method to read enroll data
    public void readEnroll(Scanner input) {
        findLastId();
        enrollId = "E" + Integer.toString(lastId);
        System.out.print("Enter student ID: ");
        studentId = input.nextLine();
        System.out.print("Enter student name: ");
        studentName = input.nextLine();
        System.out.print("Enter department ID: ");
        deptId = input.nextLine();
        System.out.print("Enter department name: ");
        deptName = input.nextLine();
    }

    // Method to enroll student into department
    public void enrollStudentIntoDept() {
        try {
            FileWriter writer = new FileWriter("Enrolls.txt", true);
            writer.write(
                    enrollId + ", " + studentId + ", " + studentName + ", " + deptId + ", " + deptName + "\n");
            writer.close();
            System.out.println("");
            System.out.println("Student enrolled into department successfully\n");
        } catch (Exception e) {
            System.out.println("Error while enrolling student into department: " + e.getMessage());
        }
    }

    // Method to remove student from department
    public void removeStudentFromDept(String idTodelete, String deptIdTodelete) {
        try {
            File inputFile = new File("Enrolls.txt");
            File tempFile = new File("TempEnrolls.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;
            // Copy all lines from original file to temporary file except the line to delete
            while ((currentLine = reader.readLine()) != null) {
                String[] enrollData = currentLine.split(", ");
                if (!enrollData[1].equals(idTodelete) || !enrollData[3].equals(deptIdTodelete)) {
                    writer.write(currentLine);
                    writer.newLine();
                } else {
                    found = true;
                }
            }

            reader.close();
            writer.close();

            System.out.println("");
            // Delete the original file and rename the temporary file only if the course was
            // found
            if (found) {
                inputFile.delete();
                tempFile.renameTo(inputFile);
                System.out.println("Student removed successfully.\n");
            } else {
                System.out.println("Student with ID " + idTodelete + " not found.\n");
                tempFile.delete(); // Delete the temporary file if the course was not found
            }

        } catch (Exception e) {
            System.out.println("Error while removing student from department: " + e.getMessage());
        }
    }

    // Method to display all students study at given department
    public void displayAllStudentStudyAtDept(String deptIDToDisplay) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Enrolls.txt"));
            ArrayList<String> enrollDetailsToDisplay = new ArrayList<String>();
            boolean found = false;
            String currentLine;
            // Add the department name to the array list if the department ID matches
            while ((currentLine = reader.readLine()) != null) {
                String[] enrollDetails = currentLine.split(", ");
                if (enrollDetails[3].equals(deptIDToDisplay)) {
                    found = true;
                    enrollDetailsToDisplay.add(enrollDetails[2]);
                    deptName = enrollDetails[4];
                }
            }
            System.out.println("");
            if (found) {
                System.out.println("--------------");
                System.out.println("Department " + deptName);
                System.out.println("--------------");
                for (int i = 0; i < enrollDetailsToDisplay.size(); i++) {
                    System.out.println(enrollDetailsToDisplay.get(i));
                }
                System.out.println("--------------\n");
                reader.close();
            } else {
                System.out.println("Department with ID " + deptIDToDisplay + " not found.\n");
                reader.close();
            }
        } catch (Exception e) {
            System.out.println("Error while displaying all students study at given department: " + e.getMessage());
        }
    }

    // Method to display all department studies by given student
    public void displayAllDeptStudiesByStudent(String studentIdToDisplay) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Enrolls.txt"));
            ArrayList<String> enrollDetailsToDisplay = new ArrayList<String>();
            boolean found = false;
            String currentLine;
            // Add the department name to the array list if the student ID matches
            while ((currentLine = reader.readLine()) != null) {
                String[] enrollDetails = currentLine.split(", ");
                if (enrollDetails[1].equals(studentIdToDisplay)) {
                    found = true;
                    enrollDetailsToDisplay.add(enrollDetails[4]);
                    studentName = enrollDetails[2];
                }
            }
            System.out.println("");
            if (found) {
                System.out.println("--------------");
                System.out.println("Student " + studentName);
                System.out.println("--------------");
                for (int i = 0; i < enrollDetailsToDisplay.size(); i++) {
                    System.out.println(enrollDetailsToDisplay.get(i));
                }
                System.out.println("--------------\n");
                reader.close();
            } else {
                System.out.println("Student with ID " + studentIdToDisplay + " not found.\n");
                reader.close();
            }
        } catch (Exception e) {
            System.out.println("Error while displaying all department studies by given student: " + e.getMessage());
        }
    }
}

public class EnrollStudent {
    public static void main() {
        Scanner input = new Scanner(System.in);
        Enroll enroll = new Enroll("", "", "", "", "");
        // User input
        while (true) {
            System.out.println("----------Enroll Student----------");
            System.out.println("a. Enroll student into department");
            System.out.println("b. Remove student from department");
            System.out.println("c. Display all student study at given department");
            System.out.println("d. Display all department studies by given student");
            System.out.println("e. Exit");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "a":
                    enroll.readEnroll(input);
                    enroll.enrollStudentIntoDept();
                    break;
                case "b":
                    System.out.print("Enter student ID to remove: ");
                    String idTodelete = input.nextLine();
                    System.out.print("Enter department ID to remove: ");
                    String deptIdTodelete = input.nextLine();
                    enroll.removeStudentFromDept(idTodelete, deptIdTodelete);
                    break;
                case "c":
                    System.out.print("Enter department ID: ");
                    String deptIDToDisplay = input.nextLine();
                    enroll.displayAllStudentStudyAtDept(deptIDToDisplay);
                    break;
                case "d":
                    System.out.print("Enter student ID: ");
                    String studentIdToDisplay = input.nextLine();
                    enroll.displayAllDeptStudiesByStudent(studentIdToDisplay);
                    break;
                case "e":
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
