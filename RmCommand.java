package org.example;

import java.io.File;
import java.util.Scanner;

public class RmCommand implements Command {//inherir from command and imlement execute functiom

    @Override
    public void execute(String[] args) {
        //remove from input command instruction
        if (args[0].equals("rm")) {
            args = java.util.Arrays.copyOfRange(args, 1, args.length);
            //take substr
        }
        // if user import rm only
        if (args.length == 0) {
            System.out.println("Usage: rm [options] <file/directory>");
            return;
        }

        boolean force = false;//if file is unwritable check if i can delet it or not
        boolean recursive = false;//in directory to check if i will remove every file and folder inside it
        int index = 0;

        // Parse command line options
        while (index < args.length) {
            String arg = args[index];
            if (arg.equals("-f") || arg.equals("--force")) {
                force = true;//true give permission for file deletion
            } else if (arg.equals("-r")) {
                recursive = true;//true to remove eacg file and directory inside directory
            } else {
                // Handle file/directory removal
                File target = getFileFromPath(arg);//this return file or directory where this file or folder located
                remove(target, force, recursive);// then pass it to remove function
            }
            index++;
        }
    }

    private File getFileFromPath(String path) {
        File file = new File(path);
        if (!file.isAbsolute()) {
            return new File(CommandLineInterface.currentDirectory, path);
        }
        return file;
    }

    private void remove(File file, boolean force, boolean recursive) {
        if (!file.exists()) {// checks if file available or not exists
            System.out.println("Error: " + file.getPath() + " does not exist.");
            return;
        }

        if (file.isDirectory()) {//if file is directory
            if (recursive || file.list().length == 0) {
                // if directory is empty so delete without using -r or if the command includes -r so delete the recursive part till leaf
                deleteDirectory(file);//goes to delete dictionary
            } else {
                //this mean that directory is recursive then and there is no -r included so you should include it
                System.out.println("Error: " + file.getName() + " is a directory. Use -r to remove it.");
            }
        } else {
            //check if i can delete file or not through two ways first user gives force so command has the right to delete it
            //or i will ask user at the runtime to check
            if (force || confirmRemoval(file.getName())) {
                if (file.delete()) {//file deleted succesfully
                    System.out.println("Removed: " + file.getName());
                } else {
                    System.out.println("Error: Unable to remove " + file.getName());// Not able to delete this file
                }
            } else {
                //this mean no confirm to delete this file
                System.out.println("Skipped: " + file.getName());
            }
        }
    }

    private boolean confirmRemoval(String fileName) {
        Scanner scanner = new Scanner(System.in);
        //take from user yes ot no to check if i have the acees to delete the file
        System.out.print("Remove " + fileName + "? (y/n): ");
        String response = scanner.nextLine();
        return response.trim().toLowerCase().startsWith("y");//retun true if it is working
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();// list all folder inside directory
        if (files != null) {//if not empty will remove it
            for (File file : files) {//loop on files and if it is directory it will recurse on it again till arrive to the leaf
                if (file.isDirectory()) {
                    deleteDirectory(file);//this is the recursive part
                } else {
                    if (file.delete()) {
                        System.out.println("Removed: " + file.getName());
                    } else {
                        System.out.println("Error: Unable to remove " + file.getName());
                    }
                }
            }
        }
        if (directory.delete()) {//finally after deleting any inside files or directories , then delete directory
            System.out.println("Removed directory: " + directory.getName());
        } else {
            System.out.println("Error: Unable to remove directory " + directory.getName());
        }
    }
}
