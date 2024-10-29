package org.example;
import java.io.File;
import java.util.Scanner;


public class RmCommand implements Command {

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: No path provided.");
            return;
        }
        if(args[0].equals("rm")) {
            args = java.util.Arrays.copyOfRange(args, 1, args.length);
        }
        boolean recursive = false;
        String path = args[args.length - 1];

        // Check if the recursive flag is provided
        if (args.length > 1 && args[0].equals("-r")) {
            recursive = true;
        }

        // Resolve the path
        File target = new File(path);
        if (!target.isAbsolute()) {
            target = new File(CommandLineInterface.currentDirectory, path);
        }

        if (!target.exists()) {
            System.out.println("Error: Path does not exist.");
            return;
        }

        // If it's a file, ask for permission to delete
        if (target.isFile()) {
            askForFileDeletion(target);
        }
        // If it's a directory, handle based on recursive flag and contents
        else if (target.isDirectory()) {
            if (target.list().length == 0) { // Directory is empty
                deleteDirectory(target);
            } else if (recursive) { // Directory is not empty, and -r is provided
                deleteDirectoryRecursively(target);
            } else { // Directory is not empty, and -r is not provided
                System.out.println("Error: Directory is not empty. Use -r to delete recursively.");
            }
        }
    }

    private void askForFileDeletion(File file) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Are you sure you want to delete " + file.getName() + "? (y/n): ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("y")) {
            if (file.delete()) {
                System.out.println(file.getName() + " deleted successfully.");
            } else {
                System.out.println("Error: Could not delete " + file.getName());
            }
        } else {
            System.out.println("File deletion canceled.");
        }
    }

    private void deleteDirectory(File dir) {
        if (dir.delete()) {
            System.out.println("Directory " + dir.getName() + " deleted successfully.");
        } else {
            System.out.println("Error: Could not delete directory " + dir.getName());
        }
    }

    private void deleteDirectoryRecursively(File dir) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                deleteDirectoryRecursively(file);
            } else {
                System.out.println("File " + file.getName() + " deleted successfully.");
                file.delete();
            }
        }
        deleteDirectory(dir); // Delete the directory after all contents are deleted
    }
}
