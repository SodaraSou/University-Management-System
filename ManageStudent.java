import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

class Student {
    private String studentId;
    private String studentName;
    private String gender;
    private String dob;
    private String phoneNo;
    private String address;
    private String year;
    private String generation;
    private String degree;
    private int lastId = 0;

    // Default constructor
    public Student(String studentId, String studentName, String gender, String dob, String phoneNo, String address,
            String year, String generation, String degree) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.gender = gender;
        this.dob = dob;
        this.phoneNo = phoneNo;
        this.address = address;
        this.year = year;
        this.generation = generation;
        this.degree = degree;
    }

    // Method get last ID for ID auto increment
    private void findLastId() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Student.txt"));
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

    // Method to read student data from user input
    public void readStudent(Scanner input) {
        findLastId();
        studentId = "S" + Integer.toString(lastId);
        System.out.print("Enter student name: ");
        studentName = input.nextLine();
        System.out.print("Enter gender: ");
        gender = input.nextLine();
        System.out.print("Enter DOB: ");
        dob = input.nextLine();
        System.out.print("Enter phone number: ");
        phoneNo = input.nextLine();
        System.out.print("Enter address: ");
        address = input.nextLine();
        System.out.print("Enter year: ");
        year = input.nextLine();
        System.out.print("Enter generation: ");
        generation = input.nextLine();
        System.out.print("Enter degree: ");
        degree = input.nextLine();
    }

    // Method add Student
    public void addStudent() {
        try {
            FileWriter writer = new FileWriter("Student.txt", true);
            writer.write(studentId + ", " + studentName + ", " + gender + ", " + dob + ", " + phoneNo + ", " + address
                    + ", " + year + ", " + generation + ", " + degree + "\n");
            writer.close();
            System.out.println("");
            System.out.println("Student added successfully!\n");
        } catch (Exception e) {
            System.out.println("Error while adding student: " + e.getMessage());
        }
    }

    // Method to search Student by id
    public void searchStudent(String idToSearch) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Student.txt"));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] studentData = currentLine.split(", ");
                if (studentData[0].equals(idToSearch)) {
                    System.out.println("");
                    System.out.println(
                            "-------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s \n", "StudentID",
                            "StudentName", "Gender", "DOB", "PhoneNo", "Address", "Year", "Generation", "Degree");
                    System.out.println(
                            "-------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s \n", studentData[0],
                            studentData[1],
                            studentData[2],
                            studentData[3], studentData[4], studentData[5], studentData[6], studentData[7],
                            studentData[8]);
                    System.out.println(
                            "-------------------------------------------------------------------------------------------------------------------------------------------\n");
                    reader.close();
                    return;
                }
            }
            System.out.println("");
            System.out.println("Student with ID " + idToSearch + " not found.\n");
            reader.close();
        } catch (Exception e) {
            System.out.println("Error while searching student: " + e.getMessage());
        }
    }

    // Method to update Student
    public void updateStudent(String idToUpdate, Scanner input) {
        try {
            String inputFile = "Student.txt";
            String tempFile = "TempStudent.txt";

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;
            // Read the file line by line and update the data to be updated
            while ((currentLine = reader.readLine()) != null) {
                String[] studentData = currentLine.split(", ");
                if (studentData[0].equals(idToUpdate)) {
                    found = true;
                    while (true) {
                        System.out.println("-----------Update-----------");
                        System.out.println("1. Update student name");
                        System.out.println("2. Update gender");
                        System.out.println("3. Update DOB");
                        System.out.println("4. Update phone number");
                        System.out.println("5. Update address");
                        System.out.println("6. Update year");
                        System.out.println("7. Update generation");
                        System.out.println("8. Update degree");
                        System.out.println("9. Exit");
                        System.out.print("Enter your choice: ");
                        String choice = input.nextLine();

                        switch (choice) {
                            case "1":
                                System.out.print("Enter new student name: ");
                                String newStudentName = input.nextLine();
                                studentData[1] = newStudentName;
                                break;
                            case "2":
                                System.out.print("Enter new student sex: ");
                                String newStudentSex = input.nextLine();
                                studentData[2] = newStudentSex;
                                break;
                            case "3":
                                System.out.print("Enter new student DOB: ");
                                String newStudentDOB = input.nextLine();
                                studentData[3] = newStudentDOB;
                                break;
                            case "4":
                                System.out.print("Enter new student phone number: ");
                                String newStudentPhoneNo = input.nextLine();
                                studentData[4] = newStudentPhoneNo;
                                break;
                            case "5":
                                System.out.print("Enter new student address: ");
                                String newStudentAddress = input.nextLine();
                                studentData[5] = newStudentAddress;
                                break;
                            case "6":
                                System.out.print("Enter new student year: ");
                                String newStudentYear = input.nextLine();
                                studentData[6] = newStudentYear;
                                break;
                            case "7":
                                System.out.print("Enter new student generation: ");
                                String newStudentGeneration = input.nextLine();
                                studentData[7] = newStudentGeneration;
                                break;
                            case "8":
                                System.out.print("Enter new student degree: ");
                                String newStudentDegree = input.nextLine();
                                studentData[8] = newStudentDegree;
                                break;
                            case "9":
                                break;
                            default:
                                System.out.println("Invalid choice!");
                                break;
                        }
                        if (choice.equals("9")) {
                            break; // Exit the inner loop and continue with the outer loop
                        }
                    }
                    // Save the updated data back to the file after the update loop
                    String updatedLine = String.join(", ", studentData);
                    writer.write(updatedLine + "\n");
                } else {
                    writer.write(currentLine + "\n");
                }
            }

            reader.close();
            writer.close();

            System.out.println("");
            // Delete the original file and rename the temporary file only if the ID was
            // found
            if (found) {
                System.out.println("Student updated successfully!\n");
                new File(inputFile).delete();
                new File(tempFile).renameTo(new File(inputFile));
            } else {
                System.out.println("Student with ID " + idToUpdate + " not found.\n");
                new File(tempFile).delete(); // Delete the temporary file if the ID was not found
            }
            return;

        } catch (Exception e) {
            System.out.println("Error while updating student: " + e.getMessage());
        }
    }

    // Method to delete student
    public void deleteStudent(String idToDelete) {
        try {
            File inputFile = new File("Student.txt");
            File tempFile = new File("TempStudent.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;

            // Copy all the lines from the original file to the temporary file except the
            // line to be deleted
            while ((currentLine = reader.readLine()) != null) {
                String[] studentData = currentLine.split(", ");
                if (!studentData[0].equals(idToDelete)) {
                    writer.write(currentLine);
                    writer.newLine();
                } else {
                    found = true;
                }
            }

            reader.close();
            writer.close();

            System.out.println("");
            // Delete the original file and rename the temporary file only if the ID was
            // found
            if (found) {
                inputFile.delete();
                tempFile.renameTo(inputFile);
                System.out.println("Student deleted successfully.\n");
            } else {
                System.out.println("Student with ID " + idToDelete + " not found.\n");
                tempFile.delete(); // Delete the temporary file if the ID was not found
            }

        } catch (Exception e) {
            System.out.println("Error while deleting student: " + e.getMessage());
        }
    }

}

public class ManageStudent {
    public static void main() {
        Scanner input = new Scanner(System.in);
        Student student = new Student("", "", "", "", "", "", "", "", "");
        // User input
        while (true) {
            System.out.println("----------Manage Student----------");
            System.out.println("a. Add a new student");
            System.out.println("b. Search a student by id");
            System.out.println("c. Update a student");
            System.out.println("d. Delete a student by id");
            System.out.println("e. Exit");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "a":
                    // Add a new department
                    student.readStudent(input);
                    student.addStudent();
                    break;
                case "b":
                    // Search a department by id
                    System.out.print("Enter student ID to Search: ");
                    String idToSearch = input.nextLine();
                    student.searchStudent(idToSearch);
                    break;
                case "c":
                    // Update a department
                    System.out.print("Enter student ID to Update: ");
                    String idToUpdate = input.nextLine();
                    student.updateStudent(idToUpdate, input);
                    break;
                case "d":
                    // Delete a faculty by id
                    System.out.print("Enter student ID to Delete: ");
                    String idToDelete = input.nextLine();
                    student.deleteStudent(idToDelete);
                    break;
                case "e":
                    // Exit
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}
