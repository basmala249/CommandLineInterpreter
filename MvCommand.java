package org.example;
import org.example.Command;

import java.nio.file.*;
import java.io.IOException;

public class MvCommand implements Command {
    @Override
    public void execute(String[] paths) {
        if (paths.length < 2) {
            System.out.println("Error: Insufficient arguments. Usage: mv <source...> <target>");
            return;
        }
        if (paths[0].equals("mv")) {
            paths = java.util.Arrays.copyOfRange(paths, 1, paths.length);
            //take substr
        }
        // Get current directory from CommandLineInterface
        Path currentDirectory = Paths.get(CommandLineInterface.currentDirectory).toAbsolutePath();

        // Resolve all paths relative to the current directory if necessary
        Path targetPath = currentDirectory.resolve(paths[paths.length - 1]).normalize();

        try {
            // Case 1: Single source and target
            if (paths.length == 2) {
                Path sourcePath = currentDirectory.resolve(paths[0]).normalize();

                // Check if source exists
                if (!Files.exists(sourcePath)) {
                    System.out.println("Source does not exist: " + sourcePath);
                    return;
                }

                // If source is a directory, target must also be a directory
                if (Files.isDirectory(sourcePath)) {
                    if (!Files.isDirectory(targetPath)) {
                        System.out.println("Error: When moving a directory, target must be a directory.");
                        return;
                    }
                    // Move the directory into the target directory
                    Files.move(sourcePath, targetPath.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Moved directory " + sourcePath + " to " + targetPath);
                } else {
                    // If source is a file, target can be a file (rename) or directory (move to directory)
                    if (Files.isDirectory(targetPath)) {
                        Path targetFilePath = targetPath.resolve(sourcePath.getFileName());
                        Files.move(sourcePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Moved file " + sourcePath + " to directory: " + targetFilePath);
                    } else {
                        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Renamed file " + sourcePath + " to " + targetPath);
                    }
                }
            }

            // Case 2: Multiple sources, last argument must be a directory
            else {
                if (!Files.isDirectory(targetPath)) {
                    System.out.println("Error: Target must be a directory when moving multiple sources.");
                    return;
                }

                for (int i = 0; i < paths.length - 1; i++) {
                    Path sourcePath = currentDirectory.resolve(paths[i]).normalize();

                    // Check if each source exists
                    if (!Files.exists(sourcePath)) {
                        System.out.println("Source does not exist: " + sourcePath);
                        continue;
                    }

                    // Move each source into the target directory
                    Path targetFilePath = targetPath.resolve(sourcePath.getFileName());
                    Files.move(sourcePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Moved " + sourcePath + " to " + targetFilePath);
                }
            }

        } catch (IOException e) {
            System.out.println("Failed to move file: " + e.getMessage());
        }
    }
}
