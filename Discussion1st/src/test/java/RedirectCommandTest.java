import org.example.CommandLineInterface;
import org.example.RedirectCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.List;


public class RedirectCommandTest {
    private final RedirectCommand redirect_overwrite = new RedirectCommand(false);
    private final RedirectCommand redirect_append = new RedirectCommand(true);

    @Test
    public void overwrite_test() throws IOException {
        File output_file = new File("test.txt");

        redirect_overwrite.execute(new String[]{"echo Hello World > test.txt"});

        try (BufferedReader reader = new BufferedReader(new FileReader(output_file))) {
            List<String> lines = reader.lines().toList();
            assertEquals(1, lines.size());
            assertEquals("Hello World", lines.get(0));
        }
        output_file.delete();
    }


    @Test
    public void append_test() throws IOException {
        File output_file = new File("test.txt");

        try (BufferedWriter w = new BufferedWriter(new FileWriter(output_file))) {
            w.write("Hello World\n");
        }
        redirect_append.execute(new String[]{"echo Hello World1 >> test.txt"});

        try (BufferedReader reader = new BufferedReader(new FileReader(output_file))) {
            List<String> lines = reader.lines().toList();
            assertEquals(2, lines.size());
            assertEquals("Hello World", lines.get(0));
            assertEquals("Hello World1", lines.get(1));
        }
    }


    @Test
    void invalid_overwrite_test() {
        ByteArrayOutputStream output_stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output_stream));

        redirect_overwrite.execute(new String[]{"test > output.txt"});
        String expectedErrorMessage = "Command not found: test";
        assertTrue(output_stream.toString().contains(expectedErrorMessage));
    }


    @Test
    void invalid_append_test() {
        ByteArrayOutputStream output_stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output_stream));

        redirect_append.execute(new String[]{"test >> output.txt"});
        String expectedErrorMessage = "Command not found: test";
        assertTrue(output_stream.toString().contains(expectedErrorMessage));
    }


//    @Test
//    void test_cat_command() throws IOException {
//        CommandLineInterface.REDIRECT = true;
//        File input_file = new File("test.txt");
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(input_file))) {
//            writer.write("this is the test.txt file");
//        }
//
//        redirect_overwrite.execute(new String[]{"cat test.txt > output.txt"});
//
//        File output_file = new File("output.txt");
//
//        try (BufferedReader br = new BufferedReader(new FileReader(output_file))) {
//            List<String> lines = br.lines().toList();
//            assertEquals(1, lines.size());
//            assertEquals("this is the test.txt file", lines.get(0));
//        }
//        input_file.delete();
//        output_file.delete();
//    }
}
