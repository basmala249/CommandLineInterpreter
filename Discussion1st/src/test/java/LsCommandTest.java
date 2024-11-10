import org.example.LsCommand;
import org.example.Command;
import org.example.CommandLineInterface;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class LsCommandTest {
    private Command lsCommand;

    /**
     * Setup method to initialize the test environment before each test.
     * This method is called before each test case runs.
     */
    @BeforeEach
    public void setup() {
        lsCommand = new LsCommand();
    }

    /**
     * Test case to verify listing of files in the current directory, excluding hidden files.
     **/
    @Test
    public void testListFiles()
    {
        CommandLineInterface.tests.clear();
        // Execute the command to list files
        lsCommand.execute(new String[]{"ls"});
        List <String> result = CommandLineInterface.tests;
        System.out.println(result);
        // Get the current directory
        File currentDir = new File(CommandLineInterface.currentDirectory);

        // Get expected files (non-hidden) from the current directory
        String[] expectedFiles = currentDir.list((dir, name) -> !name.startsWith("."));

        // Assert that the result matches the expected files
        assertArrayEquals(expectedFiles, result.toArray());
    }

    /**
     * Test case to verify listing of all files, including hidden files.
     */
    @Test
    public void testListAllFilesIncludingHidden()
    {
        CommandLineInterface.tests.clear();
        // Execute the command to list all files
        lsCommand.execute(new String[]{"ls", "-a"});
        List <String> result = CommandLineInterface.tests;
        System.out.println(result);
        // Get the current directory
        File currentDir = new File(CommandLineInterface.currentDirectory);

        // Get expected files (including hidden) from the current directory
        String[] expectedFiles = currentDir.list();

        // Assert that the result matches the expected files
        assertArrayEquals(expectedFiles, result.toArray());
    }

    /**
     * Test case to verify listing of files in reverse order.
     */
    @Test
    public void testListFilesInReverseOrder()
    {
        CommandLineInterface.tests.clear();
        // Execute the command to list files in reverse order
        lsCommand.execute(new String[]{"ls", "-r"});
        List <String> result = CommandLineInterface.tests;
        // Get the current directory
        File currentDir = new File(CommandLineInterface.currentDirectory);
        System.out.println(result);
        // Get non-hidden files from the current directory
        String[] files = currentDir.list((dir, name) -> !name.startsWith("."));

        // Sort the files in reverse order
        Arrays.sort(files, (a, b) -> b.compareTo(a)); // Reverse sort

        // Assert that the result matches the expected reverse order
        assertArrayEquals(files, result.toArray());
    }
}
