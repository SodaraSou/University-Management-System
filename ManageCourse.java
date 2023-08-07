import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

class Course {
    private String courseId;
    private String courseName;
    private String credit;
    private String type;
    private int lastId = 0;

    // Default constructor
    public Course(String courseId, String courseName, String credit, String type) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.type = type;
    }

    // Method to find the last ID in the file to auto increment the new ID
    private void findLastId() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Courses.txt"));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] courseData = currentLine.split(", ");
                lastId = Integer.parseInt(courseData[0].substring(1));
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

    // Method to read course data from user input
    public void readCourse(Scanner input) {
        findLastId();
        courseId = "C" + Integer.toString(lastId);
        System.out.print("Enter course name: ");
        courseName = input.nextLine();
        System.out.print("Enter credit: ");
        credit = input.nextLine();
        System.out.print("Enter type: ");
        type = input.nextLine();
    }

    // Method add course
    public void addCourse() {
        try {
            FileWriter writer = new FileWriter("Courses.txt", true);
            writer.write(courseId + ", " + courseName + ", " + credit + ", " + type + "\n");
            writer.close();
            System.out.println("");
            System.out.println("Course added successfully!\n");
        } catch (Exception e) {
            System.out.println("Error while adding course: " + e.getMessage());
        }
    }

    // Method to search course by id
    public void searchCourse(String idToSearch) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Courses.txt"));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] courseData = currentLine.split(", ");
                if (courseData[0].equals(idToSearch)) {
                    System.out.println("");
                    System.out.println("--------------------------------------------------------------");
                    System.out.printf("%-15s %-15s %-15s %-15s %n", "CourseID", "CourseName", "Credit",
                            "type");
                    System.out.println("--------------------------------------------------------------");
                    System.out.printf("%-15s %-15s %-15s %-15s %n", courseData[0], courseData[1],
                            courseData[2],
                            courseData[3]);
                    System.out.println("--------------------------------------------------------------\n");
                    reader.close();
                    return;
                }
            }
            System.out.println("");
            System.out.println("Course not found!\n");
            reader.close();
        } catch (Exception e) {
            System.out.println("Error while searching course: " + e.getMessage());
        }
    }

    // Method to update Course by id
    public void updateCourse(String idToUpdate, Scanner input) {
        try {
            String inputFile = "Courses.txt";
            String tempFile = "TempCourses.txt";

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;

            while ((currentLine = reader.readLine()) != null) {
                // Split the line by ", " to get the data of the current line
                String[] courseData = currentLine.split(", ");
                // Check if the ID of the current line matches the ID to update
                if (courseData[0].equals(idToUpdate)) {
                    found = true;
                    while (true) {
                        System.out.println("----------Update----------");
                        System.out.println("1. Update course name");
                        System.out.println("2. Update credit");
                        System.out.println("3. Update type");
                        System.out.println("4. Exit");
                        System.out.print("Enter your choice: ");
                        String choice = input.nextLine();

                        switch (choice) {
                            case "1":
                                System.out.print("Enter new course name: ");
                                String newCourseName = input.nextLine();
                                courseData[1] = newCourseName;
                                break;
                            case "2":
                                System.out.print("Enter new course credit: ");
                                String newCredit = input.nextLine();
                                courseData[2] = newCredit;
                                break;
                            case "3":
                                System.out.print("Enter new type: ");
                                String newType = input.nextLine();
                                courseData[3] = newType;
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
                    // Save the updated data back to the file after the update loop
                    String updatedLine = String.join(", ", courseData);
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
                System.out.println("Course updated successfully!\n");
                new File(inputFile).delete();
                new File(tempFile).renameTo(new File(inputFile));
            } else {
                System.out.println("Course with ID " + idToUpdate + " not found.\n");
                new File(tempFile).delete(); // Delete the temporary file if the ID was not found
            }
            return;
        } catch (Exception e) {
            System.out.println("Error while updating course: " + e.getMessage());
        }
    }

    // Method to delete course
    public void deleteCourse(String idToDelete) {
        try {
            File inputFile = new File("Courses.txt");
            File tempFile = new File("TempCourses.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;
            // Copy all lines from original file to temporary file except the line to delete
            while ((currentLine = reader.readLine()) != null) {
                String[] courseData = currentLine.split(", ");
                if (!courseData[0].equals(idToDelete)) {
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
                System.out.println("Course deleted successfully.\n");
            } else {
                System.out.println("Course with ID " + idToDelete + " not found.\n");
                tempFile.delete(); // Delete the temporary file if the ID was not found
            }

        } catch (Exception e) {
            System.out.println("Error while deleting course: " + e.getMessage());
        }
    }
}

public class ManageCourse {
    public static void main() {
        Scanner input = new Scanner(System.in);
        Course course = new Course("", "", "", "");
        // User input
        while (true) {
            System.out.println("----------Manage Course----------");
            System.out.println("a. Add a new course");
            System.out.println("b. Search a course by id");
            System.out.println("c. Update a course");
            System.out.println("d. Delete a course by id");
            System.out.println("e. Exit");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "a":
                    // Add a new course
                    course.readCourse(input);
                    course.addCourse();
                    break;
                case "b":
                    // Search a department by id
                    System.out.print("Enter course ID to Search: ");
                    String idToSearch = input.nextLine();
                    course.searchCourse(idToSearch);
                    break;
                case "c":
                    // Update a department
                    System.out.print("Enter course ID to Update: ");
                    String idToUpdate = input.nextLine();
                    course.updateCourse(idToUpdate, input);
                    break;
                case "d":
                    // Delete a faculty by id
                    System.out.print("Enter course ID to Delete: ");
                    String idToDelete = input.nextLine();
                    course.deleteCourse(idToDelete);
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