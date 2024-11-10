import org.example.CommandLineInterface;
import org.example.HelpCommand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class HelpCommandTest {
    @Test
    void test_without_args(){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        HelpCommand help_c = new HelpCommand();
        help_c.execute(new String[1]);

        String expected_help_string = """
                pwd             : print the path of current directory.
                ls              : view all files in the current directory.
                ls -a           : view all files, including hidden files.
                ls -r           : view all files in the current directory in reverse order.
                mkdir <dir>     : make a new directory with name <dir>.
                rmdir <dir>     : delete directory <dir> if it is empty.
                touch <file>    : create a new file with the name <file>.
                mv <src> <dest> : move from <src> to <dest>.
                rm <file>       : remove the file <file>. 
                cd <dir>        : change the current directory to <dir>.
                cat <file>      : list the contents of <file>.
                > <file>        : create or overwrites the <file>.
                >> <file>       : append text to <file>.
                |               : pipe output of one command to another.
                exit            : to quit.
                """;
        assertEquals(expected_help_string.trim(), out.toString().trim());
    }

    @Test
    void test_with_2much_args(){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        HelpCommand help_c = new HelpCommand();
        help_c.execute(new String[3]);

        String expectedErrorMessage = "Usage: help(has no parameter)";
        assertEquals(expectedErrorMessage, out.toString().trim());
    }
}
