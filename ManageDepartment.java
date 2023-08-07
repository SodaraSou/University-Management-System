import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

class Department {
    private String deptId;
    private String facultyId;
    private String deptName;
    private String headName;
    private String officeNo;
    private int lastId = 0;

    // Default constructor
    public Department(String deptId, String facultyId, String deptName, String headName, String officeNo) {
        this.deptId = deptId;
        this.facultyId = facultyId;
        this.deptName = deptName;
        this.headName = headName;
        this.officeNo = officeNo;
    }

    // Method to find last ID for ID auto increment
    private void findLastId() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Department.txt"));
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

    // Method to read Department data from user input
    public void readDepartment(Scanner input) {
        findLastId();
        deptId = "D" + Integer.toString(lastId);
        System.out.print("Enter Faculty ID: ");
        facultyId = input.nextLine();
        System.out.print("Enter Department name: ");
        deptName = input.nextLine();
        System.out.print("Enter Department head name: ");
        headName = input.nextLine();
        System.out.print("Enter Office no: ");
        officeNo = input.nextLine();
    }

    // Method add department
    public void addDepartment() {
        // Add a new faculty
        try {
            FileWriter writer = new FileWriter("Department.txt", true);
            writer.write(deptId + ", " + facultyId + ", " + deptName + ", " + headName + ", " + officeNo + "\n");
            writer.close();
            System.out.println("");
            System.out.println("Faculty added successfully!\n");
        } catch (Exception e) {
            System.out.println("Error while adding faculty: " + e.getMessage());
        }
    }

    // Method to search department by id
    public void searchDepartment(String idToSearch) {
        // Search a faculty
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Department.txt"));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] departmentData = currentLine.split(", ");
                if (departmentData[0].equals(idToSearch)) {
                    System.out.println("");
                    System.out.println("------------------------------------------------------------------------");
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", "DepartmentID", "FacultyID", "DepartmentName",
                            "HeadName",
                            "OfficeNo");
                    System.out.println("------------------------------------------------------------------------");
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", departmentData[0], departmentData[1],
                            departmentData[2],
                            departmentData[3], departmentData[4]);
                    System.out.println("------------------------------------------------------------------------\n");
                    reader.close();
                    return;
                }
            }
            System.out.println("");
            System.out.println("Department with the ID of " + idToSearch + " not found.\n");
            reader.close();
        } catch (Exception e) {
            System.out.println("Error while searching Department: " + e.getMessage());
        }
    }

    // Method to update department
    public void updateDepartment(String idToUpdate, Scanner input) {
        try {
            String inputFile = "Department.txt";
            String tempFile = "TempDepartment.txt";
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;
            boolean found = false;
            // Read the file line by line and update the data to be updated
            while ((currentLine = reader.readLine()) != null) {
                String[] departmentData = currentLine.split(", ");
                if (departmentData[0].equals(idToUpdate)) {
                    found = true;
                    while (true) {
                        System.out.println("");
                        System.out.println("1. Update faculty ID");
                        System.out.println("2. Update department Name");
                        System.out.println("3. Update head Name");
                        System.out.println("4. Update Office No");
                        System.out.println("5. Exit");
                        System.out.print("Enter your choice: ");
                        String choice = input.nextLine();

                        switch (choice) {
                            case "1":
                                System.out.print("Enter new Faculty ID: ");
                                String newFacultyID = input.nextLine();
                                departmentData[1] = newFacultyID;
                                break;
                            case "2":
                                System.out.print("Enter new department Name: ");
                                String newDepartmentName = input.nextLine();
                                departmentData[2] = newDepartmentName;
                                break;
                            case "3":
                                System.out.print("Enter new head Name: ");
                                String newheadName = input.nextLine();
                                departmentData[3] = newheadName;
                                break;
                            case "4":
                                System.out.print("Enter new Office No: ");
                                String newOfficeNo = input.nextLine();
                                departmentData[4] = newOfficeNo;
                                break;
                            case "5":
                                break;
                            default:
                                System.out.println("Invalid choice!");
                                break;
                        }
                        if (choice.equals("5")) {
                            break; // Exit the inner loop and continue with the outer loop
                        }
                    }
                    // Save the updated data back to the file after the update loop
                    String updatedLine = String.join(", ", departmentData);
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
                System.out.println("Department updated successfully!\n");
                new File(inputFile).delete();
                new File(tempFile).renameTo(new File(inputFile));
            } else {
                System.out.println("Department with ID " + idToUpdate + " not found.\n");
                new File(tempFile).delete(); // Delete the temporary file if the ID was not found
            }
            return;
        } catch (Exception e) {
            System.out.println("Error while updating department: " + e.getMessage());
        }
    }

    // Method to delete department
    public void deleteDepartment(String idToDelete) {
        try {
            File inputFile = new File("Department.txt");
            File tempFile = new File("TempDepartment.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;
            // Copy all lines from original file to temporary file except the line to delete
            while ((currentLine = reader.readLine()) != null) {
                String[] departmentData = currentLine.split(", ");
                if (!departmentData[0].equals(idToDelete)) {
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
                System.out.println("Department deleted successfully.\n");
            } else {
                System.out.println("Department with ID " + idToDelete + " not found.\n");
                tempFile.delete(); // Delete the temporary file if the ID was not found
            }

        } catch (Exception e) {
            System.out.println("Error while deleting department: " + e.getMessage());
        }
    }

    // Method Display all departments belong to a faculty
    public void displayAllDepartment(Scanner input, String idToDisplay) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Department.txt"));
            String currentLine;
            boolean found = false;
            System.out.println("");
            System.out.println("------------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s %-15s %-15s\n", "DepartmentID", "FacultyID", "DepartmentName",
                    "HeadName",
                    "OfficeNo");
            System.out.println("------------------------------------------------------------------------");
            while ((currentLine = reader.readLine()) != null) {
                String[] departmentData = currentLine.split(", ");
                if (departmentData[1].equals(idToDisplay)) {
                    found = true;
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s\n", departmentData[0], departmentData[1],
                            departmentData[2],
                            departmentData[3], departmentData[4]);
                }
            }
            if (!found) {
                System.out.println("No departments found for the given faculty ID.");
            }
            System.out.println("------------------------------------------------------------------------\n");
            reader.close();
        } catch (Exception e) {
            System.out.println("Error while displaying all departments: " + e.getMessage());
        }
    }
}

public class ManageDepartment {
    public static void main() {
        Scanner input = new Scanner(System.in);
        Department dept = new Department("", "", "", "", "");
        // User input
        while (true) {
            System.out.println("---------Manage Department---------");
            System.out.println("a. Add a new Department");
            System.out.println("b. Search a department by id");
            System.out.println("c. Update a department");
            System.out.println("d. Delete a department by id");
            System.out.println("e. Display all departments belong to a faculty");
            System.out.println("f. Exit");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "a":
                    // Add a new department
                    dept.readDepartment(input);
                    dept.addDepartment();
                    break;
                case "b":
                    // Search a department by id
                    System.out.print("Enter Department ID to Search: ");
                    String idToSearch = input.nextLine();
                    dept.searchDepartment(idToSearch);
                    break;
                case "c":
                    // Update a department
                    System.out.print("Enter Department ID to Update: ");
                    String idToUpdate = input.nextLine();
                    dept.updateDepartment(idToUpdate, input);
                    break;
                case "d":
                    // Delete a faculty by id
                    System.out.print("Enter Department ID to Delete: ");
                    String idToDelete = input.nextLine();
                    dept.deleteDepartment(idToDelete);
                    break;
                case "e":
                    // Display all departments belong to a faculty
                    System.out.print("Enter Faculty ID to Display: ");
                    String idToDisplay = input.nextLine();
                    dept.displayAllDepartment(input, idToDisplay);
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
