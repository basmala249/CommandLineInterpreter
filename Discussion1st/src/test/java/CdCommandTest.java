import org.example.CdCommand;
import org.example.Command;
import org.example.CommandLineInterface;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.List;

public class CdCommandTest {
    private Command cdCommand;

    // Store the original directory before tests are executed
    private static final String originalDir = CommandLineInterface.currentDirectory;

    /**
     * Setup method to initialize the test environment before each test.
     * This method is called before each test case runs.
     */
    @BeforeEach
    public void setup()
    {
        // Reset the current directory to its original value before each test
        CommandLineInterface.currentDirectory = originalDir;
        cdCommand = new CdCommand();
    }

    /**
     * Test case to verify changing the directory to the parent directory.
     */
    @Test
    public void testChangeDirectoryToParent()
    {
        CommandLineInterface.tests.clear();
        // Execute the command to change to the parent directory
        cdCommand.execute(new String[]{"cd", ".."});
        List<String> result = CommandLineInterface.tests;
        // Get the parent directory of the original directory
        File parentDir = new File(originalDir).getParentFile();

        /** Check if the parent directory is not null (i.e., we are not at the root) **/
        if (parentDir != null)
        {
            // Verify that the current directory is updated to the parent directory
            assertEquals(parentDir.getAbsolutePath(), CommandLineInterface.currentDirectory);
            // Verify the result message contains the expected confirmation
            assertTrue(result.get(0).contains("Changed directory to: "));
        }
        else
        {
            // If at the root directory, check for the appropriate error message
            assertTrue(result.get(0).contains("[Error: Already at the root directory.]"));
        }
    }

    /**
     * Test case to verify handling of an invalid directory path.
     */
    @Test
    public void testInvalidDirectory()
    {
        CommandLineInterface.tests.clear();
        // Execute the command with a nonexistent directory
        cdCommand.execute(new String[]{"cd", "nonexistent_dir"});
        // Assert that the result contains the expected error message
        List<String> result = CommandLineInterface.tests;
        assertEquals("Error: invalid directory path.", result.get(0));
    }
}
