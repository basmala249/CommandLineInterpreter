import org.example.Command;
import org.example.CommandLineInterface;
import org.example.RmCommand;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.List;

public class RmCommandTest {
    private Command RCommand;

    @BeforeEach
    public void setup() {
        RCommand = new RmCommand();
    }

    // Test for removing a file
    @Test
    public void testRmFile() {
        CommandLineInterface.tests.clear();
        //creates file
        String testFileName = "testFile.txt";
        File testFile = new File(testFileName);

        try {
            // Create the test file
            if (testFile.createNewFile()) {
                System.out.println("Test file created: " + testFileName);
            }

            // Execute the RmCommand to delete the file
            RCommand.execute(new String[]{"rm", testFileName});
            List<String> result = CommandLineInterface.tests;

            // Assert that the deletion was successful
            assertTrue(result.contains(testFileName + " deleted successfully."));
            //this means file didnot delete successfully
            assertFalse(testFile.exists(), "File should not exist after deletion.");

        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception occurred during the test: " + e.getMessage());
        } finally {
            // Clean up: attempt to delete the test file if it still exists
            if (testFile.exists()) {
                testFile.delete();
            }
        }
    }

    // Test for removing a directory without -r option
    @Test
    public void testRmDirectoryWithoutRecursive() {
        CommandLineInterface.tests.clear();

        String dirName = "m";
        File testDir = new File(dirName);
        File nestedFile = new File(dirName + "/er.txt"); // File inside the directory

        try {
            // Create the test directory
            if (testDir.mkdir()) {
                System.out.println("Test directory created: " + dirName);
            }

            // Create a file inside the directory to ensure it's not empty
            if (nestedFile.createNewFile()) {
                System.out.println("Nested test file created: " + nestedFile.getName());
            }

            // Execute the RmCommand to delete the directory without -r
            RCommand.execute(new String[]{"rm", dirName});
            List<String> result = CommandLineInterface.tests;

            // Assert that the appropriate error message is returned
            assertTrue(result.contains("Error: Directory is not empty. Use -r to delete recursively."));
            assertTrue(testDir.exists(), "Directory should still exist after attempted deletion without -r.");

        } catch (Exception e) {
            fail("An exception occurred during the test: " + e.getMessage());
        } finally {
            // Clean up: attempt to delete the nested file and the test directory if they still exist
            if (nestedFile.exists()) {
                nestedFile.delete();
            }
            //remove all created file at testtime
            if (testDir.exists()) {
                testDir.delete();
            }
        }
    }

    // Test for removing a directory with -r option
    @Test
    public void testRmDirectoryWithRecursive() {
        CommandLineInterface.tests.clear();

        String dirName = "testDir";
        File testDir = new File(dirName);
        File nestedFile = new File(dirName + "/nestedFile.txt");

        try {
            // Create the test directory and a nested file
            if (testDir.mkdir()) {
                System.out.println("Test directory created: " + dirName);
            }
            //creates inside files
            if (nestedFile.createNewFile()) {
                System.out.println("Nested test file created: " + nestedFile.getName());
            }

            // Execute the RmCommand to delete the directory with -r
            RCommand.execute(new String[]{"rm", "-r", dirName});
            List<String> result = CommandLineInterface.tests;

            // Assert that the deletion was successful
            assertTrue(result.contains("Directory " + dirName + " deleted successfully."));

        } catch (Exception e) {
            fail("An exception occurred during the test: " + e.getMessage());
        } finally {
            // Clean up: attempt to delete the nested file if it still exists
            if (nestedFile.exists()) {
                nestedFile.delete();
            }
            // Clean up: attempt to delete the test directory if it still exists
            if (testDir.exists()) {
                testDir.delete();
            }
        }
    }
}
