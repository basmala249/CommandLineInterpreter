package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The TouchCommand class is responsible for creating new files
 * or updating the timestamps of existing files in the command line interface.
 *
 * Steps for execution:
 * 1. Check if any arguments are provided:
 *    - If no arguments are given, print usage instructions and exit.
 *
 * 2. Attempt to create or update the specified files:
 *    - Check if the parent directory exists; if not, print an error message.
 *    - Create or update the file as needed only if the parent directory exists.
 */
public class TouchCommand implements Command {
    @Override
    public void execute(String[] args) {
        List<String> output = new ArrayList<>();

        // Check for at least one file argument after the command name
        if (args.length < 2) {
            System.out.println("Usage: touch <filename1> ..."); // Inform the user how to use the command
            return ; // Return the usage message if no valid file names are provided
        }

        // Loop over each filename argument starting from the second argument
        for (String filename : Arrays.copyOfRange(args, 1, args.length)) {
            // Create a File object with the full path relative to the current directory
            File targetFile = new File(CommandLineInterface.currentDirectory, filename);

            // Get the parent directory of the target file
            File parentDir = targetFile.getParentFile();

            // Check if the parent directory exists
            if (parentDir != null && !parentDir.exists()) {
                // If the parent directory doesn't exist, add an error message to the output
                System.out.println("Error: Parent directory does not exist for " + targetFile.getAbsolutePath());
                continue; // Skip to the next filename if the directory doesn't exist
            }

            try {
                // Try to create the file; createNewFile() returns true if the file was created
                if (targetFile.createNewFile()) {
                    System.out.println("File created: " + targetFile.getAbsolutePath()); // Success message for file creation
                } else {
                    // If the file already exists, update its last modified timestamp
                    if (targetFile.setLastModified(System.currentTimeMillis())) {
                        System.out.println("File updated: " + targetFile.getAbsolutePath()); // Success message for timestamp update
                    } else {
                        // If the timestamp update fails, add an error message
                        System.out.println("Error: Unable to update the file: " + targetFile.getAbsolutePath());
                    }
                }
            } catch (IOException e) {
                // Catch any IOExceptions and provide an error message
                System.out.println("Error: Unable to create or update the file " + filename + ". " + e.getMessage());
            }
        }
    }
}