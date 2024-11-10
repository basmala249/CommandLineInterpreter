import org.example.Command;
import org.example.CommandLineInterface;
import org.example.MvCommand;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.example.Command;
import java.nio.file.*;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class MvCommandTest {
    private Command mvCommand;

    @BeforeEach
    public void setup() {
        mvCommand = new MvCommand();
    }

    @Test
    public void MoveCommand() {
        CommandLineInterface.tests.clear();
        String sourcePath = "testSource.txt"; // Sample source file
        String targetPath = "testDestination.txt"; // Sample destination

        // Create a mock source file for testing
        try {
            new File(sourcePath).createNewFile();
        } catch (Exception e) {
            fail("Could not create source file for testing.");
        }

        // Execute the move command
        String[] args = {sourcePath, targetPath};
        mvCommand.execute(args);

        // Verify that the source file no longer exists
        assertFalse(new File(sourcePath).exists(), CommandLineInterface.tests.get(0));
        System.out.println(new File(sourcePath).exists() + CommandLineInterface.tests.get(0));
        // Verify that the target file exists
        assertTrue(new File(targetPath).exists(), CommandLineInterface.tests.get(0));
    }
    @Test
    public void testMultipleSourcesLastNotDirectory() {
        CommandLineInterface.tests.clear();
        String sourceFilePath1 = CommandLineInterface.currentDirectory + "/testFile1.txt"; // Create first source file
        String sourceFilePath2 = CommandLineInterface.currentDirectory + "/testFile2.txt"; // Create second source file
        String targetFilePath = CommandLineInterface.currentDirectory + "/notADirectory.txt"; // Create a target file (not a directory)

        try {
            //Creates Multiole files to test All of this
            new File(sourceFilePath1).createNewFile();
            new File(sourceFilePath2).createNewFile();
            new File(targetFilePath).createNewFile(); // Create target file
        } catch (Exception e) {
            //Checks if cannot Create
            fail("Could not create source files or target file for testing.");
        }

        // Execute mvCommand with multiple sources, last is not a directory
        String[] args = {sourceFilePath1, sourceFilePath2, targetFilePath}; // Multiple sources
        mvCommand.execute(args);

        // Check if the expected error message is present in the global tests array
        assertTrue(CommandLineInterface.tests.stream()
                        .anyMatch(msg -> msg.contains("Error: Target must be a directory when moving multiple sources.")),
                "Should indicate an error for invalid target when moving multiple sources.");
        //message if the sent was file Not folder
        // Cleanup after test
        try {
            Files.deleteIfExists(Paths.get(sourceFilePath1));
            Files.deleteIfExists(Paths.get(sourceFilePath2));
            Files.deleteIfExists(Paths.get(targetFilePath)); // Clean up target file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
