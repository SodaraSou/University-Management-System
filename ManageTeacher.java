import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Teacher {
    private String teacherId;
    private String teacherName;
    private String gender;
    private String dob;
    private String phoneNo;
    private String address;
    private int lastId;

    // Default constructor
    public Teacher(String teacherId, String teacherName, String gender, String dob, String phoneNo, String address) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.gender = gender;
        this.dob = dob;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    private void findLastId() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Teachers.txt"));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] teacherData = currentLine.split(", ");
                lastId = Integer.parseInt(teacherData[0].substring(1));
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

    // Method to read teacher data from user input
    public void readTeacher(Scanner input) {
        findLastId();
        teacherId = "T" + Integer.toString(lastId);
        System.out.print("Enter teacher name: ");
        teacherName = input.nextLine();
        System.out.print("Enter gender: ");
        gender = input.nextLine();
        System.out.print("Enter date of birth: ");
        dob = input.nextLine();
        System.out.print("Enter phone number: ");
        phoneNo = input.nextLine();
        System.out.print("Enter address: ");
        address = input.nextLine();
    }

    // Method add teacher
    public void addTeacher() {
        try {
            FileWriter writer = new FileWriter("Teachers.txt", true);
            writer.write(teacherId + ", " + teacherName + ", " + gender + ", " + dob + ", " + phoneNo + ", " + address
                    + "\n");
            writer.close();
            System.out.println("");
            System.out.println("Teacher added successfully!\n");
        } catch (Exception e) {
            System.out.println("Error while adding teacher: " + e.getMessage());
        }
    }

    // Method to search course by id
    public void searchTeacher(String idToSearch) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Teachers.txt"));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] teacherData = currentLine.split(", ");
                if (teacherData[0].equals(idToSearch)) {
                    System.out.println("");
                    System.out.println(
                            "---------------------------------------------------------------------------------------------");
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s\n", "TeacherID", "TeacherName", "Gender",
                            "DOB",
                            "PhoneNo", "Address");
                    System.out.println(
                            "---------------------------------------------------------------------------------------------");
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s\n", teacherData[0], teacherData[1],
                            teacherData[2],
                            teacherData[3], teacherData[4], teacherData[5]);
                    System.out.println(
                            "---------------------------------------------------------------------------------------------\n");
                    reader.close();
                    return;
                }
            }
            System.out.println("");
            System.out.println("Teacher with ID " + idToSearch + " not found.\n");
            reader.close();
        } catch (Exception e) {
            System.out.println("Error while searching teacher: " + e.getMessage());
        }
    }

    // Method to update teacher
    public void updateTeacher(String idToUpdate, Scanner input) {
        try {
            String inputFile = "Teachers.txt";
            String tempFile = "TempTeachers.txt";
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;
            boolean found = false;
            while ((currentLine = reader.readLine()) != null) {
                String[] teacherData = currentLine.split(", ");
                if (teacherData[0].equals(idToUpdate)) {
                    found = true;
                    while (true) {
                        System.out.println("---------Update---------");
                        System.out.println("1. Update teacher name");
                        System.out.println("2. Update gender");
                        System.out.println("3. Update DOB");
                        System.out.println("4. Update phone number");
                        System.out.println("5. Update address");
                        System.out.println("6. Exit");
                        System.out.print("Enter your choice: ");
                        String choice = input.nextLine();

                        switch (choice) {
                            case "1":
                                System.out.print("Enter new teacher name: ");
                                String newTeacherName = input.nextLine();
                                teacherData[1] = newTeacherName;
                                break;
                            case "2":
                                System.out.print("Enter new gender: ");
                                String newGender = input.nextLine();
                                teacherData[2] = newGender;
                                break;
                            case "3":
                                System.out.print("Enter new DOB: ");
                                String newDOB = input.nextLine();
                                teacherData[3] = newDOB;
                                break;
                            case "4":
                                System.out.print("Enter new phone number: ");
                                String newPhoneNumber = input.nextLine();
                                teacherData[4] = newPhoneNumber;
                                break;
                            case "5":
                                System.out.print("Enter new address: ");
                                String newAddress = input.nextLine();
                                teacherData[5] = newAddress;
                                break;
                            case "6":
                                break;
                            default:
                                System.out.println("Invalid choice!");
                                break;
                        }
                        if (choice.equals("6")) {
                            break; // Exit the inner loop and continue with the outer loop
                        }
                    }
                    // Save the updated data back to the file after the update loop
                    String updatedLine = String.join(", ", teacherData);
                    writer.write(updatedLine + "\n");
                } else {
                    writer.write(currentLine + "\n");
                }
            }

            reader.close();
            writer.close();

            System.out.println("");
            // Delete the original file and rename the temporary file only if the ID was
            if (found) {
                System.out.println("Teacher updated successfully!\n");
                new File(inputFile).delete();
                new File(tempFile).renameTo(new File(inputFile));
            } else {
                System.out.println("Teacher with ID " + idToUpdate + " not found.");
                new File(tempFile).delete(); // Delete the temporary file if the ID was not found
            }
            return;
        } catch (Exception e) {
            System.out.println("Error while updating teacher: " + e.getMessage());
        }
    }

    // Method to delete teacher
    public void deleteTeacher(String idToDelete) {
        try {
            File inputFile = new File("Teachers.txt");
            File tempFile = new File("TempTeachers.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;

            while ((currentLine = reader.readLine()) != null) {
                String[] teacherData = currentLine.split(", ");
                if (!teacherData[0].equals(idToDelete)) {
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
                System.out.println("Teacher deleted successfully.\n");
            } else {
                System.out.println("Teacher with ID " + idToDelete + " not found.\n");
                tempFile.delete(); // Delete the temporary file if the ID was not found
            }

        } catch (Exception e) {
            System.out.println("Error while deleting teacher: " + e.getMessage());
        }
    }

    // Method display all courses that a teacher is teaching
    public void displayCourses(String idToDisplay) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("AssignCourses.txt"));
            ArrayList<String> courseToDisplay = new ArrayList<String>();
            String currentLine;
            boolean found = false;
            while ((currentLine = reader.readLine()) != null) {
                String[] assignData = currentLine.split(", ");
                if (assignData[1].equals(idToDisplay)) {
                    found = true;
                    courseToDisplay.add(assignData[4]);
                    teacherName = assignData[2];
                }
            }
            reader.close();
            System.out.println("");
            if (found) {
                System.out.println("--------------");
                System.out.println("Teacher " + teacherName);
                System.out.println("--------------");
                for (int i = 0; i < courseToDisplay.size(); i++) {
                    System.out.println(courseToDisplay.get(i));
                }
                System.out.println("--------------\n");
            } else {
                System.out.println("Teacher with ID " + idToDisplay + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error while displaying course: " + e.getMessage());
        }
    }
}

public class ManageTeacher {
    public static void main() {
        Scanner input = new Scanner(System.in);
        Teacher teacher = new Teacher("", "", "", "", "", "");
        // User input
        while (true) {
            System.out.println("----------Manage Teacher----------");
            System.out.println("a. Add a new teacher");
            System.out.println("b. Search a teacher by id");
            System.out.println("c. Update a teacher");
            System.out.println("d. Delete a teacher by id");
            System.out.println("e. Display all courses that a teacher is teaching");
            System.out.println("f. Exit");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "a":
                    // Add a new course
                    teacher.readTeacher(input);
                    teacher.addTeacher();
                    break;
                case "b":
                    // Search a department by id
                    System.out.print("Enter teacher ID to Search: ");
                    String idToSearch = input.nextLine();
                    teacher.searchTeacher(idToSearch);
                    break;
                case "c":
                    // Update a department
                    System.out.print("Enter teacher ID to Update: ");
                    String idToUpdate = input.nextLine();
                    teacher.updateTeacher(idToUpdate, input);
                    break;
                case "d":
                    // Delete a faculty by id
                    System.out.print("Enter teacher ID to Delete: ");
                    String idToDelete = input.nextLine();
                    teacher.deleteTeacher(idToDelete);
                    break;
                case "e":
                    // Display all courses that a teacher is teaching
                    System.out.print("Enter teacher ID to display all courses: ");
                    String idToDisplay = input.nextLine();
                    teacher.displayCourses(idToDisplay);
                    break;
                case "f":
                    // Exit
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}
