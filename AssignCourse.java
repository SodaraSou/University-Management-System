import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

class AssignCourses {
    private String assignId;
    private String teacherId;
    private String teacherName;
    private String courseId;
    private String courseName;
    private int lastId = 0;

    // Default constructor
    public AssignCourses(String assignId, String teacherId, String teacherName, String courseId, String courseName) {
        this.assignId = assignId;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.courseId = courseId;
        this.courseName = courseName;
    }

    // Method to find last ID for ID auto increment
    private void findLastId() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("AssignCourses.txt"));
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

    // Method to read input from user
    public void readAssignCourse(Scanner input) {
        findLastId();
        assignId = "A" + Integer.toString(lastId);
        System.out.print("Enter teacher ID: ");
        teacherId = input.nextLine();
        System.out.print("Enter teacher name: ");
        teacherName = input.nextLine();
        System.out.print("Enter course ID: ");
        courseId = input.nextLine();
        System.out.print("Enter course: ");
        courseName = input.nextLine();
    }

    // Method to assign courses to teachers
    public void assignCourses() {
        try {
            File inputFile = new File("AssignCourses.txt");
            FileWriter writer = new FileWriter(inputFile, true);
            writer.write(
                    assignId + ", " + teacherId + ", " + teacherName + ", " + courseId + ", " + courseName + "\n");
            writer.close();
            System.out.println("");
            System.out.println("Course assigned successfully.\n");
        } catch (Exception e) {
            System.out.println("Error while assigning course: " + e.getMessage());
        }
    }

    // Method to remove courses from teachers
    public void removeCourses(String idTeacher, String courseIdToRemove) {
        try {
            File inputFile = new File("AssignCourses.txt");
            File tempFile = new File("TempAssignCourses.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;
            // Read the file line by line and write to temporary file only if the course is
            // not found in the file to delete
            while ((currentLine = reader.readLine()) != null) {
                String[] assignData = currentLine.split(", ");
                if (!assignData[1].equals(idTeacher) || !assignData[3].equals(courseIdToRemove)) {
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
                System.out.println("Course removed successfully.\n");
            } else {
                System.out.println("Teacher with ID " + idTeacher + " or course " + courseIdToRemove + " not found.\n");
                tempFile.delete(); // Delete the temporary file if the course was not found
            }

        } catch (Exception e) {
            System.out.println("Error while removing course: " + e.getMessage());
        }
    }

    // Method to display all courses taught by a teacher
    public void displayCourses(String idToDisplay) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("AssignCourses.txt"));
            ArrayList<String> courseToDisplay = new ArrayList<String>();
            String currentLine;
            boolean found = false;
            // Read the file line by line and add the course to display if the teacher ID
            while ((currentLine = reader.readLine()) != null) {
                String[] assignData = currentLine.split(", ");
                if (assignData[1].equals(idToDisplay)) {
                    found = true;
                    courseToDisplay.add(assignData[4]);
                    teacherName = assignData[2];
                }
            }
            System.out.println("");
            if (found) {
                System.out.println("--------------");
                System.out.println("Teacher " + teacherName);
                System.out.println("--------------");
                for (int i = 0; i < courseToDisplay.size(); i++) {
                    System.out.println(courseToDisplay.get(i));
                }
                System.out.println("--------------\n");
                reader.close();
            } else {
                System.out.println("Teacher with ID " + idToDisplay + " not found.\n");
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error while displaying course: " + e.getMessage());
        }
    }
}

public class AssignCourse {
    public static void main() {
        Scanner input = new Scanner(System.in);
        AssignCourses assignCourses = new AssignCourses("", "", "", "", "");
        // User Input
        while (true) {
            System.out.println("----------Assign Courses----------");
            System.out.println("a. Assign Courses");
            System.out.println("b. Remove Courses");
            System.out.println("c. Display Courses");
            System.out.println("d. Exit");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();
            switch (choice) {
                case "a":
                    assignCourses.readAssignCourse(input);
                    assignCourses.assignCourses();
                    break;
                case "b":
                    System.out.print("Enter teacher ID to remove course: ");
                    String idTeacher = input.nextLine();
                    System.out.print("Enter course ID to remove: ");
                    String courseIdToRemove = input.nextLine();
                    assignCourses.removeCourses(idTeacher, courseIdToRemove);
                    break;
                case "c":
                    System.out.print("Enter teacher ID to display: ");
                    String idToDisplay = input.nextLine();
                    assignCourses.displayCourses(idToDisplay);
                    break;
                case "d":
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}
