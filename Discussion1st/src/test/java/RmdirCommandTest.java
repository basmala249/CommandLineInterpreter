import org.example.RmdirCommand;
import org.example.Command;
import org.example.CommandLineInterface;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
public class RmdirCommandTest {
    private Command rmdirCommand;

    /**
     * Setup method to initialize the test environment before each test.
     * This method is called before each test case runs.
     */
    @BeforeEach
    public void setup() {
        rmdirCommand = new RmdirCommand();
    }

    /**
     * Test case to verify the behavior of removing an empty directory.
     * It checks that the directory is removed successfully.
     */
    @Test
    public void testRemoveEmptyDirectory()
    {
        CommandLineInterface.tests.clear();
        String dirName = "testDir"; // Name of the directory to create and remove
        File dir = new File(CommandLineInterface.currentDirectory, dirName);
        dir.mkdir(); // Create the directory for testing

        // Execute the RmdirCommand to remove the directory
        rmdirCommand.execute(new String[]{"rmdir", dirName});
        List <String> result = CommandLineInterface.tests;
        // Assert that the directory no longer exists
        assertFalse(dir.exists(), "The directory should be removed.");

        // Assert that the command returns the correct message
        assertEquals("Directory removed: " + dirName, result.get(0));
    }

    /**
     * Test case to verify the behavior of attempting to remove a non-empty directory.
     * It checks that the directory remains and the correct error message is returned.
     */
    @Test
    public void testRemoveNonEmptyDirectory() throws IOException
    {
        CommandLineInterface.tests.clear();
        String dirName = "nonEmptyDir"; // Name of the directory to create
        File dir = new File(CommandLineInterface.currentDirectory, dirName);
        dir.mkdir(); // Create the directory for testing

        // Create a file inside the directory to make it non-empty
        new File(dir, "testFile.txt").createNewFile();

        // Execute the RmdirCommand to remove the directory
        rmdirCommand.execute(new String[]{"rmdir", dirName});
        List <String> result = CommandLineInterface.tests;
        // Assert that the directory still exists
        assertTrue(dir.exists(), "The directory should still exist since it is not empty.");

        // Assert that the command returns the correct message for non-empty directory
        assertEquals("Directory isn't empty. Remove content first.", result.get(0));

        // Clean up: Remove the test file and the directory after the test
        for (File file : dir.listFiles()) file.delete(); // Delete files within the directory
        dir.delete(); // Delete the directory itself
    }
}
