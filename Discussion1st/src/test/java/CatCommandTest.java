import org.example.CatCommand;
import java.io.IOException;
import org.example.CommandLineInterface;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileWriter;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;


public class CatCommandTest {
    @Test
    void test_with_correct_file() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        CatCommand cat_c = new CatCommand();
        File file = new File("CatTest.txt");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Rabana Yasla7 Al 7al");
        }

        CommandLineInterface.currentDirectory = file.getParent();
        cat_c.execute(new String[]{"cat", "CatTest.txt"});

        String expectedOutput = "Rabana Yasla7 Al 7al";
        assertEquals(expectedOutput, out.toString().trim());
        file.delete();
    }

    @Test
    void testCatCommandWithNonExistingFile() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CatCommand cat_c = new CatCommand();

        cat_c.execute(new String[]{"cat", "x.xyz"});
        String error_message = "Error reading file";

        assertEquals(error_message.trim(), out.toString().trim());
    }
}
