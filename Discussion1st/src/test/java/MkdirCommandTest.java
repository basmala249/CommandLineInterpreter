import org.example.MkdirCommand;
import org.example.Command;
import org.example.CommandLineInterface;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.List;
public class MkdirCommandTest {
    private Command mkdirCommand;

    /**
     * Setup method to initialize the test environment before each test.
     * This method is called before each test case runs.
     */
    @BeforeEach
    public void setup()
    {
        mkdirCommand = new MkdirCommand();
    }

    /**
     * Test case to verify the creation of a new directory.
     * The test checks if the directory is created successfully.
     */
    @Test
    public void testCreateDirectory()
    {
        CommandLineInterface.tests.clear();
        // Name of the directory to be created
        String dirName = "testDir";
        // Create a File object for the new directory
        File newDir = new File(CommandLineInterface.currentDirectory, dirName);

        // Clean up: delete the directory if it already exists before the test
        if (newDir.exists()) newDir.delete();

        // Execute the mkdir command
        mkdirCommand.execute(new String[]{"mkdir", dirName});
        List <String> result = CommandLineInterface.tests;
        // Assert that the directory now exists
        assertTrue(newDir.exists());
        // Assert that the command returned the expected success message
        assertEquals("Directory created: " + newDir.getAbsolutePath(), result.get(0));

        // Clean up: delete the newly created directory after the test
        newDir.delete();
    }

    /**
     * Test case to verify the behavior when trying to create a directory that already exists.
     * The test checks that the correct message is returned.
     */
    @Test
    public void testDirectoryAlreadyExists()
    {
        CommandLineInterface.tests.clear();
        // Name of the directory to be tested
        String dirName = "existingDir";
        // Create a File object for the existing directory
        File existingDir = new File(CommandLineInterface.currentDirectory, dirName);
        // Create the directory beforehand for this test
        existingDir.mkdirs();

        // Execute the mkdir command on the existing directory
        mkdirCommand.execute(new String[]{"mkdir", dirName});
        List <String> result = CommandLineInterface.tests;
        // Assert that the command returned the expected message indicating the directory already exists
        assertEquals("Directory already exists: " + existingDir.getAbsolutePath(), result.get(0));

        // Clean up: delete the existing directory after the test
        existingDir.delete();
    }
}
