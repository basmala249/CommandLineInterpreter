import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.Pipe;
import org.example.Command;
import org.example.CommandLineInterface;
import org.example.GrepCommand;
import org.example.CommandLineInterface;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.Pipe;
import org.example.CommandLineInterface;
import org.example.LsCommand;
import org.example.GrepCommand;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PipeTest {
    private Pipe pipe;
    private GrepCommand GrpCommand ;
    @BeforeEach
    public void setUp() {
        pipe = new Pipe();
        GrpCommand = new GrepCommand();
        CommandLineInterface.Output.clear();
        CommandLineInterface.tempOutput.clear();
        CommandLineInterface.tests.clear(); // Clear tests to start fresh
    }

    @Test
    public void testPipeWithLsAndGrep() {
        CommandLineInterface.tests.clear();
        // Mock the output of ls command
        String pattern = "txt";
        String[] lsOutput = {pattern , "file1.txt", "file2.txt", "image.jpg", "document.pdf"};
        //CommandLineInterface.tempOutput.addAll(Arrays.asList(lsOutput));
        // Debug output to inspect actual result

        GrpCommand.execute(lsOutput);
        System.out.println("Final Output: " + CommandLineInterface.tests);
        // Verify the output should only contain files matching "file"
        assertTrue(CommandLineInterface.tests.contains("file1.txt"), "Output should contain file1.txt");
        assertTrue(CommandLineInterface.tests.contains("file2.txt"), "Output should contain file2.txt");
        assertEquals(2, CommandLineInterface.tests.size(), "Output should have 2 matching files.");
    }

}
