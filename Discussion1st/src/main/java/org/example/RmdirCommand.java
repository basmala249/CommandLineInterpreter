//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.io.File;

public class RmdirCommand implements Command {
    public RmdirCommand() {
    }

    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: rmdir <directory>");
            CommandLineInterface.tests.add("Usage: rmdir <directory>");
        } else {
            String dir = args[1];
            File directory = new File(CommandLineInterface.currentDirectory, dir);
            if (directory.exists() && directory.isDirectory()) {
                String[] inside = directory.list();
                if (inside != null && inside.length != 0) {
                    System.out.println("Directory isn't empty. Remove content first.");
                    CommandLineInterface.tests.add("Directory isn't empty. Remove content first.");
                } else if (directory.delete()) {
                    System.out.println("Directory removed: " + dir);
                    CommandLineInterface.tests.add("Directory removed: " + dir);
                } else {
                    System.out.println("Failed to remove directory: " + dir);
                    CommandLineInterface.tests.add("Failed to remove directory: " + dir);
                }
            } else {
                System.out.println("The directory path isn't valid: ");
                CommandLineInterface.tests.add("The directory path isn't valid. Remove content first.");
            }

        }
    }
}
