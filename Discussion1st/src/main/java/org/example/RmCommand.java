//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.io.File;
import java.util.Arrays;

public class RmCommand implements Command {
    public RmCommand() {
    }

    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: No path provided.");
            CommandLineInterface.tests.add("Error: No path provided.");
        } else {
            if (args[0].equals("rm")) {
                args = (String[])Arrays.copyOfRange(args, 1, args.length);
            }

            boolean recursive = false;
            String path = args[args.length - 1];
            if (args.length > 1 && args[0].equals("-r")) {
                recursive = true;
            }

            File target = new File(path);
            if (!target.isAbsolute()) {
                target = new File(CommandLineInterface.currentDirectory, path);
            }

            if (!target.exists()) {
                System.out.println("Error: Path does not exist.");
                CommandLineInterface.tests.add("Error: Path does not exist.");
            } else {
                if (target.isFile()) {
                    this.askForFileDeletion(target);
                } else if (target.isDirectory()) {
                    if (target.list().length == 0) {
                        this.deleteDirectory(target);
                    } else if (recursive) {
                        this.deleteDirectoryRecursively(target);
                    } else {
                        System.out.println("Error: Directory is not empty. Use -r to delete recursively.");
                        CommandLineInterface.tests.add("Error: Directory is not empty. Use -r to delete recursively.");
                    }
                }

            }
        }
    }

    private void askForFileDeletion(File file) {
        if (file.delete()) {
            System.out.println(file.getName() + " deleted successfully.");
            CommandLineInterface.tests.add(file.getName() + " deleted successfully.");
        } else {
            System.out.println("Error: Could not delete " + file.getName());
            CommandLineInterface.tests.add("Error: Could not delete " + file.getName());
        }

    }

    private void deleteDirectory(File dir) {
        if (dir.delete()) {
            System.out.println("Directory " + dir.getName() + " deleted successfully.");
            CommandLineInterface.tests.add("Directory " + dir.getName() + " deleted successfully.");
        } else {
            System.out.println("Error: Could not delete directory " + dir.getName());
            CommandLineInterface.tests.add("Error: Could not delete directory " + dir.getName());
        }

    }

    private void deleteDirectoryRecursively(File dir) {
        File[] var2 = dir.listFiles();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            File file = var2[var4];
            if (file.isDirectory()) {
                this.deleteDirectoryRecursively(file);
            } else {
                System.out.println("File " + file.getName() + " deleted successfully.");
                CommandLineInterface.tests.add("File " + file.getName() + " deleted successfully.");
                file.delete();
            }
        }

        this.deleteDirectory(dir);
    }
}
