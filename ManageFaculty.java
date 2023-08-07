import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

class Faculty {
    private String facultyID;
    private String facultyName;
    private String deanName;
    private String officeNo;
    private int lastId = 0;

    // Default constructor
    public Faculty(String facultyID, String facultyName, String deanName, String officeNo) {
        this.facultyID = facultyID;
        this.facultyName = facultyName;
        this.deanName = deanName;
        this.officeNo = officeNo;
    }

    // Find the last ID in the file for auto ID increment
    private void findLastId() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Faculty.txt"));
            String currentLine;
            // Read file line by line
            while ((currentLine = reader.readLine()) != null) {
                // Split line by comma and space
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

    // Method to read employee data from user input
    public void readFaculty(Scanner input) {
        findLastId();
        facultyID = "F" + Integer.toString(lastId);
        System.out.print("Enter Faculty name: ");
        facultyName = input.nextLine();
        System.out.print("Enter Faculty Dean name: ");
        deanName = input.nextLine();
        System.out.print("Enter Office no: ");
        officeNo = input.nextLine();
        System.out.println("");
    }

    // Method to add faculty to file
    public void addFaculty() {
        try {
            FileWriter writer = new FileWriter("Faculty.txt", true);
            writer.write(facultyID + ", " + facultyName + ", " + deanName + ", " + officeNo + "\n");
            writer.close();
            System.out.println("Faculty added successfully!\n");
        } catch (Exception e) {
            System.out.println("Error while adding faculty: " + e.getMessage());
        }
    }

    // Method to search faculty by ID
    public void searchFaculty(String idToSearch) {
        try {
            // Read the file
            BufferedReader reader = new BufferedReader(new FileReader("Faculty.txt"));
            // Retrieve data from file line by line and check if the ID matches
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] facultyData = currentLine.split(", ");
                if (facultyData[0].equals(idToSearch)) {
                    System.out.println("");
                    System.out.println("------------------------------------------------------------------------");
                    System.out.printf("%-15s %-25s %-15s %-15s%n", "FacultyID", "FacultyName", "DeanName", "OfficeNo");
                    System.out.println("------------------------------------------------------------------------");
                    System.out.printf("%-15s %-25s %-15s %-15s%n", facultyData[0], facultyData[1], facultyData[2],
                            facultyData[3]);
                    System.out.println("------------------------------------------------------------------------\n");
                    reader.close();
                    return;
                }
            }
            System.out.println("");
            System.out.println("Faculty with ID " + idToSearch + " not found!\n");
            reader.close();
        } catch (Exception e) {
            System.out.println("Error while searching faculty: " + e.getMessage());
        }
    }

    // Method to update faculty by ID
    public void updateFaculty(String idToUpdate, Scanner input) {
        try {
            String inputFile = "Faculty.txt";
            String tempFile = "TempFaculty.txt";

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;
            while ((currentLine = reader.readLine()) != null) {
                String[] facultyData = currentLine.split(", ");
                if (facultyData[0].equals(idToUpdate)) {
                    found = true;
                    while (true) {
                        System.out.println("");
                        System.out.println("---------Update-----------");
                        System.out.println("1. Update Faculty Name");
                        System.out.println("2. Update Dean Name");
                        System.out.println("3. Update Office No");
                        System.out.println("4. Exit");
                        System.out.print("Enter your choice: ");
                        String choice = input.nextLine();

                        switch (choice) {
                            case "1":
                                System.out.print("Enter new Faculty Name: ");
                                String newFacultyName = input.nextLine();
                                facultyData[1] = newFacultyName;
                                break;
                            case "2":
                                System.out.print("Enter new Dean Name: ");
                                String newDeanName = input.nextLine();
                                facultyData[2] = newDeanName;
                                break;
                            case "3":
                                System.out.print("Enter new Office No: ");
                                String newOfficeNo = input.nextLine();
                                facultyData[3] = newOfficeNo;
                                break;
                            case "4":
                                break;
                            default:
                                System.out.println("Invalid choice!");
                                break;
                        }
                        if (choice.equals("4")) {
                            break; // Exit the inner loop and continue with the outer loop
                        }
                    }
                    // Save the updated data back to the file
                    String updatedLine = String.join(", ", facultyData);
                    writer.write(updatedLine + "\n");
                } else {
                    writer.write(currentLine + "\n");
                }
            }

            reader.close();
            writer.close();

            System.out.println("");
            // Replace the original file with the temporary file only if the ID was found
            if (found) {
                new File(inputFile).delete();
                new File(tempFile).renameTo(new File(inputFile));
                System.out.println("Faculty updated successfully!\n");
            } else {
                System.out.println("Faculty with ID " + idToUpdate + " not found.\n");
                new File(tempFile).delete(); // Delete the temporary file if the ID was not found
            }
            return;

        } catch (Exception e) {
            System.out.println("Error while updating faculty: " + e.getMessage());
        }
    }

    // Method to delete faculty
    public void deleteFaculty(String idToDelete) {
        try {
            File inputFile = new File("Faculty.txt");
            File tempFile = new File("TempFaculty.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;

            // Write all the data to the temporary file except the data to be deleted
            while ((currentLine = reader.readLine()) != null) {
                String[] facultyData = currentLine.split(", ");
                if (!facultyData[0].equals(idToDelete)) {
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
                System.out.println("Faculty deleted successfully.\n");
            } else {
                System.out.println("Faculty with ID " + idToDelete + " not found.\n");
                tempFile.delete(); // Delete the temporary file if the ID was not found
            }

        } catch (Exception e) {
            System.out.println("Error while deleting faculty: " + e.getMessage());
        }
    }
}

public class ManageFaculty {
    public static void main() {
        Scanner input = new Scanner(System.in);
        Faculty faculty = new Faculty("", "", "", "");
        // User input
        while (true) {
            System.out.println("---------Manage Faculty---------");
            System.out.println("a. Add a new faculty");
            System.out.println("b. Search a faculty by id");
            System.out.println("c. Update a faculty");
            System.out.println("d. Delete a faculty by id");
            System.out.println("e. Exit");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "a":
                    // Add a new faculty
                    faculty.readFaculty(input);
                    faculty.addFaculty();
                    break;
                case "b":
                    // Search a faculty by id
                    System.out.print("Enter Faculty ID to Search: ");
                    String idToSearch = input.nextLine();
                    faculty.searchFaculty(idToSearch);
                    break;
                case "c":
                    // Update a faculty
                    System.out.print("Enter Faculty ID to Update: ");
                    String idToUpdate = input.nextLine();
                    faculty.updateFaculty(idToUpdate, input);
                    break;
                case "d":
                    // Delete a faculty by id
                    System.out.print("Enter Faculty ID to Delete: ");
                    String idToDelete = input.nextLine();
                    faculty.deleteFaculty(idToDelete);
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