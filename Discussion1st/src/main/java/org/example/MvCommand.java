package org.example;

import org.example.Command;
import java.nio.file.*;
import java.io.IOException;

public class MvCommand implements Command {
    /*
       this execute functions takes path checks first if it contains valid arguments
       then check if source and destination exits or not
       then checks if the first is file no matter second is file or folder
       but if first is folder then the second must be folder
       if multiple files are found then the target must be folder
       this in the same directory
       another directory first it copy to the other directory then walk through files it just take file source name
       to make source for it at the other directories and there is replacemnt existing replace it with other
       do this until all folder and files have been deleted then after finish it delete directeroy from it is
       cuurent place and recurse till reach root and end recursive
     */
    @Override
    public void execute(String[] paths) {
        //Checks if it is less than 2 arrguments
        //No target path so give an error
        //Command form should be mv source(s)  target
        if (paths.length < 2) {
            System.out.println("Error: Insufficient arguments. Usage: mv <source...> <target>");
            CommandLineInterface.tests.add("Error: Insufficient arguments. Usage: mv <source...> <target>");
            return;
        }
        //remove mv to get source and target
        if (paths[0].equals("mv")) {
            // remove first part like substring
            paths = java.util.Arrays.copyOfRange(paths, 1, paths.length);
        }
        //get from static current directory value the absolute value current
        Path currentDirectory = Paths.get(CommandLineInterface.currentDirectory).toAbsolutePath();
        //extract target destination
        Path targetPath = currentDirectory.resolve(paths[paths.length - 1]).normalize();
        try {
            //having 2 files
            if (paths.length == 2) {
                //get directory of first source
                Path sourcePath = currentDirectory.resolve(paths[0]).normalize();
                //if given path is wrong
                if (!Files.exists(sourcePath)) {
                    System.out.println("Error: Source does not exist: " + sourcePath);
                    CommandLineInterface.tests.add("Error: Source does not exist: " + sourcePath);
                    return;
                }

                // Check if moving a directory into a file
                if (Files.isDirectory(sourcePath) && Files.isRegularFile(targetPath)) {
                    System.out.println("Error: Cannot move a directory into a file: " + targetPath);
                    CommandLineInterface.tests.add("Error: Cannot move a directory into a file: " + targetPath);
                    return;
                }
                //if target is directory not file
                if (Files.isDirectory(sourcePath)) {
                    moveOrCopyDirectory(sourcePath, targetPath);
                } else {
                    moveOrRenameFile(sourcePath, targetPath);
                }

            } else {
                //multiple file with target file gives an error
                if (!Files.isDirectory(targetPath)) {
                    System.out.println("Error: Target must be a directory when moving multiple sources.");
                    CommandLineInterface.tests.add("Error: Target must be a directory when moving multiple sources.");
                    return;
                }
                //if there are multiple files
                for (int i = 0; i < paths.length - 1; i++) {
                    //get directory for every file and folder
                    Path sourcePath = currentDirectory.resolve(paths[i]).normalize();
                    //if file not found
                    if (!Files.exists(sourcePath)) {
                        System.out.println("Error: Source does not exist: " + sourcePath);
                        CommandLineInterface.tests.add("Error: Source does not exist: " + sourcePath);
                        continue;
                    }
                    //to get name of file.txt
                    Path targetFilePath = targetPath.resolve(sourcePath.getFileName());
                    //move with replacement if i found the file with the same name
                    Files.move(sourcePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Moved " + sourcePath + " to " + targetFilePath);
                    CommandLineInterface.tests.add("Moved " + sourcePath + " to " + targetFilePath);
                }
            }

        } catch (IOException e) {
            CommandLineInterface.tests.add("Error: " + e);
            System.out.println("Failed to move: " + e.getMessage());
        }
    }

    private void moveOrCopyDirectory(Path sourcePath, Path targetPath) throws IOException {
        //if files avalible just move it to the given dictionary if exists
        if (Files.exists(targetPath) && Files.isDirectory(targetPath)) {
            Path newTargetPath = targetPath.resolve(sourcePath.getFileName());
            //copy directory at the target place till the end
            copyDirectoryRecursively(sourcePath, newTargetPath);
            //delete recursively till reach leave
            deleteDirectoryRecursively(sourcePath);
            System.out.println("Moved directory " + sourcePath + " into " + newTargetPath);
            CommandLineInterface.tests.add("Moved directory " + sourcePath + " into " + newTargetPath);
        } else {
            //copy at the destination then romve it
            copyDirectoryRecursively(sourcePath, targetPath);
            deleteDirectoryRecursively(sourcePath);
            System.out.println("Renamed directory " + sourcePath + " to " + targetPath);
            CommandLineInterface.tests.add("Renamed directory " + sourcePath + " to " + targetPath);
        }
    }

    private void moveOrRenameFile(Path sourcePath, Path targetPath) throws IOException {
        //if files is directory so put file inside it or folder
        if (Files.isDirectory(targetPath)) {
            Path targetFilePath = targetPath.resolve(sourcePath.getFileName());
            Files.move(sourcePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved file " + sourcePath + " to directory: " + targetFilePath);
            CommandLineInterface.tests.add("Moved file " + sourcePath + " to directory: " + targetFilePath);
        } else {
            //function Replace Existing if file with the same name replaced
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Renamed file " + sourcePath + " to " + targetPath);
            CommandLineInterface.tests.add("Renamed file " + sourcePath + " to " + targetPath);
        }
    }
    //to copy from location to anothor locatinn
    private void copyDirectoryRecursively(Path source, Path target) throws IOException {
        Files.walk(source).forEach(path -> {
            //will move on the path if directory will create directory at this place and start to
            //past files and if it is directory will do paths again till there is no directories
            try {
                //remove any directory till reach leave(file) or empty folder
                Path targetPath = target.resolve(source.relativize(path));
                //if directory should create at the next side as it
                if (Files.isDirectory(path)) {
                    Files.createDirectories(targetPath);
                } else {
                    //if file just copy it
                    Files.copy(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                System.out.println("Error: Cannot copy directory " + path + " - " + e.getMessage());
                CommandLineInterface.tests.add("Error: Cannot copy directory " + path + " - " + e.getMessage());
            }
        });
    }
    //to move from current directory to another directory
    private void deleteDirectoryRecursively(Path path) throws IOException {
        Files.walk(path)//move to its children first then return to parent to delete it
                .sorted((p1, p2) -> p2.compareTo(p1)) // Delete children before parents
                .forEach(p -> {
                    try {
                        Files.delete(p);//delete if it is file
                    } catch (IOException e) {
                        System.out.println("Error: Cannot delete " + p + " - " + e.getMessage());
                        CommandLineInterface.tests.add("Error: Cannot delete " + p + " - " + e.getMessage());
                    }
                });
    }
}
