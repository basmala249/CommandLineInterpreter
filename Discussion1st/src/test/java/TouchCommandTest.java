import org.example.TouchCommand;
import org.example.Command;
import org.example.CommandLineInterface;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class TouchCommandTest {
    private Command touchCommand;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalSystemOut;

    /**
     * Setup method to initialize the test environment before each test.
     * This method is called before each test case runs.
     */
    @BeforeEach
    public void setup() {
        touchCommand = new TouchCommand();
        outputStream = new ByteArrayOutputStream();
        originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream)); // Capture System.out
    }

    /**
     * Tear down method to restore the original System.out stream after each test.
     */
    @AfterEach
    public void tearDown() {
        System.setOut(originalSystemOut); // Restore the original System.out
    }

    // Test case to create a new file
    @Test
    public void testCreateNewFile() throws IOException {
        // Define the name of the file to be created
        String fileName = "testFile.txt";
        // Create a File object representing the new file in the current directory
        File newFile = new File(CommandLineInterface.currentDirectory, fileName);

        // Execute the touch command to create the new file
        touchCommand.execute(new String[]{"touch", fileName});

        // Assert that the file now exists
        assertTrue(newFile.exists(), "The file should have been created.");
        // Assert that the console output contains the correct creation message
        assertTrue(outputStream.toString().contains("File created: " + newFile.getAbsolutePath()));

        // Clean up: Delete the file after the test to maintain a clean state
        newFile.delete();
    }

    // Test case to update an existing file's timestamp
    @Test
    public void testUpdateExistingFile() throws InterruptedException, IOException {
        // Define the name of the file to be updated
        String fileName = "existingFile.txt";
        // Create a File object representing the existing file in the current directory
        File existingFile = new File(CommandLineInterface.currentDirectory, fileName);

        // Create the existing file
        existingFile.createNewFile();
        // Store the original last modified timestamp of the file
        long originalTimestamp = existingFile.lastModified();
        // Sleep for a second to ensure a noticeable time difference
        Thread.sleep(1000);

        // Execute the touch command to update the existing file's timestamp
        touchCommand.execute(new String[]{"touch", fileName});
        // Get the updated last modified timestamp of the file
        long updatedTimestamp = existingFile.lastModified();

        // Assert that the updated timestamp is greater than the original timestamp
        assertTrue(updatedTimestamp > originalTimestamp, "The file's timestamp should be updated.");
        // Assert that the console output contains the correct update message
        assertTrue(outputStream.toString().contains("File updated: " + existingFile.getAbsolutePath()));

        // Clean up: Delete the existing file after the test to maintain a clean state
        existingFile.delete();
    }

    // Test case for attempting to touch a file in a non-existent directory
    @Test
    public void testNonExistentParentDirectory() {
        // Define a filename with a non-existent parent directory
        String fileName = "nonExistentDir/testFile.txt";
        // Create a File object representing the new file in a non-existent directory
        File targetFile = new File(CommandLineInterface.currentDirectory, fileName);

        // Execute the touch command to create the file in a non-existent directory
        touchCommand.execute(new String[]{"touch", fileName});

        // Assert that the file does not exist
        assertFalse(targetFile.exists(), "The file should not have been created.");
        // Assert that the console output contains the error message
        assertTrue(outputStream.toString().contains("Error: Parent directory does not exist for " + targetFile.getAbsolutePath()));
    }

    // Test case for creating multiple files in one command
    @Test
    public void testCreateMultipleFiles() {
        String[] fileNames = {"file1.txt", "file2.txt"};

        // Execute the touch command to create multiple files
        touchCommand.execute(new String[]{"touch", fileNames[0], fileNames[1]});

        for (String fileName : fileNames) {
            File newFile = new File(CommandLineInterface.currentDirectory, fileName);

            // Assert that the file now exists
            assertTrue(newFile.exists(), "The file " + fileName + " should have been created.");
        }

        // Assert that the console output contains the correct creation messages
        for (String fileName : fileNames) {
            assertTrue(outputStream.toString().contains("File created: " + new File(CommandLineInterface.currentDirectory, fileName).getAbsolutePath()));
        }

        // Clean up: Delete the files after the test to maintain a clean state
        for (String fileName : fileNames) {
            new File(CommandLineInterface.currentDirectory, fileName).delete();
        }
    }

    // Test case for creating a file in an existing directory
    @Test
    public void testCreateFileInExistingDirectory() throws IOException {
        // Define an existing directory name and a file name to create
        String dirName = "existingDir";
        String fileName = "newFile.txt";
        File existingDir = new File(CommandLineInterface.currentDirectory, dirName);
        File newFile = new File(existingDir, fileName);

        // Create the directory if it doesn't exist
        if (!existingDir.exists()) {
            existingDir.mkdir(); // Create the directory
        }

        // Execute the touch command to create the new file in the existing directory
        touchCommand.execute(new String[]{"touch", dirName + "/" + fileName});

        // Assert that the file now exists in the existing directory
        assertTrue(newFile.exists(), "The file should have been created in the existing directory.");
        // Assert that the console output contains the correct creation message
        assertTrue(outputStream.toString().contains("File created: " + newFile.getAbsolutePath()));

        // Clean up: Delete the created file and the directory after the test
        newFile.delete();
        existingDir.delete();
    }
}