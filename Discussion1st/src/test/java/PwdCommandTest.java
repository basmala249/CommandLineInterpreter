import org.example.Command;
import org.example.CommandLineInterface;
import org.example.PwdCommand;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
public class PwdCommandTest {
    private Command pwdCommand;

    /**
     * Setup method to initialize the test environment before each test.
     * This method is called before each test case runs.
     */
    @BeforeEach
    public void setup() {
        pwdCommand = new PwdCommand();
    }

    /**
     * Test case to verify the execution of the PwdCommand.
     * The test checks if the command correctly returns the current working directory.
     */
    @Test
    public void testExecute()
    {
        CommandLineInterface.tests.clear();
        // Execute the PwdCommand, which should return the current directory
        pwdCommand.execute(new String[]{});
        List <String> result = CommandLineInterface.tests;
        // Assert that the result matches the expected current directory
        assertEquals(CommandLineInterface.currentDirectory, result.get(0));
    }
}
