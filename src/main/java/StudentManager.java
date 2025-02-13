import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class StudentManager {
    public static void sortFromSchool(Scanner scn) {
        List<String> schoolList = DatabaseManager.getDistinctSchoolTypes();
        int sizeSchool = schoolList.size();

    for( int a=0;a<sizeSchool;a++) {
        System.out.println("Select school: ");
        for (int i = 0; i < sizeSchool; i++) {
            System.out.println((i + 1) + ") " + schoolList.get(i));
        }
        int choose = scn.nextInt();

        outerLoop:
        while (true) {
            if (choose <= 0 || choose > sizeSchool) {
                System.out.println("Please choose from the valid range (1 to " + sizeSchool + ").");
                System.out.print("Enter your choice: ");
                choose = scn.nextInt();
                scn.nextLine();
                continue;
            } else {
                System.out.println(schoolList.get(choose - 1) + " school selected.");
                List<Integer> studentIds = DatabaseManager.giveSchoolGetStuList(schoolList.get(choose - 1));
                int sizeStu = studentIds.size();

                for (Integer studentId : studentIds) {
                    changeStudentStatus(studentId, scn);
                }
            }

            System.out.println("Are all students' statuses updated?");
            System.out.println("1) Yes\n2) No");
            int x = 0;

            while (true) {
                System.out.print("Enter your choice (1 or 2): ");
                x = scn.nextInt();
                if (x == 1 || x == 2) {
                    break;
                } else {
                    System.out.println("Please choose correctly (1 or 2).");
                }
            }

            if (x == 1) {
                break outerLoop;
            }
        }
    }

        displayStudentTable();
    }


    public static void changeStudentStatus(int id, Scanner scn) {



            String[] studentData = DatabaseManager.getStuData(id);
            System.out.println("+------------------+------------------+------------------+------------------+------------------+------------------+------------------+");
            System.out.println("| Student Name     | Age              | Address          | School           | Teacher Number   | Status           | Parent ID        |");
            System.out.println("+------------------+------------------+------------------+------------------+------------------+------------------+------------------+");
            System.out.printf("| %-16s | %-16s | %-16s | %-16s | %-16s | %-16s | %-16s |\n",
                    studentData[0], studentData[1], studentData[2], studentData[3], studentData[4], studentData[5], studentData[6]);
            System.out.println("+------------------+------------------+------------------+------------------+------------------+------------------+------------------+");

        int option = 0;
        while (option < 1 || option > 6) {
            System.out.println("Select new status:");
            System.out.println("1. Not Get From School");
            System.out.println("2. In the Bus");
            System.out.println("3. Dropped");
            System.out.println("4. Not Getting From School Today");
            System.out.println("5. There is Issue Contact Teacher");
            System.out.println("6. Ignore/Pass");
            System.out.print("Enter option: ");

            if (scn.hasNextInt()) {
                option = scn.nextInt();
                scn.nextLine(); // Consume newline
                if (option < 1 || option > 6) {
                    System.out.println("Invalid option selected. Please enter a number between 1 and 6.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                scn.next(); // Clear the invalid input
            }
        }

        if (option == 6) {
            System.out.println("No changes made to the student status.");
            return; // Exit the method
        }

        String newStatus;
        switch (option) {
            case 1:
                newStatus = "Not Get From School";
                break;
            case 2:
                newStatus = "In the Bus";
                break;
            case 3:
                newStatus = "Dropped";
                break;
            case 4:
                newStatus = "Not Getting From School Today";
                break;
            case 5:
                newStatus = "There is Issue Contact Teacher";
                break;
            default:
                return; // This should never happen due to the while loop validation
        }

        boolean result = DatabaseManager.updateStuField(id, "Student_Status", newStatus);
        if (result) {
            System.out.println("Student status updated successfully.");
        } else {
            System.out.println("Failed to update student status.");
        }
    }

    public static void displayStudentTable() {
        System.out.println("Student Data from Database:");
        System.out.printf("%-10s %-15s %-5s %-20s %-15s %-15s %-35s %-10s%n", "ID", "Name", "Age", "Address", "School", "Teacher Number", "Status", "Parent ID");
        DatabaseManager.printStuTableData();
    }
}






