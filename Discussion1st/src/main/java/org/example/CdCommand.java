//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.io.File;
import java.util.Arrays;

public class CdCommand implements Command {
    public CdCommand() {
    }

    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: cd <directory>");
            CommandLineInterface.tests.add("Usage: cd <directory>");
        } else {
            String path = String.join(" ", (CharSequence[])Arrays.copyOfRange(args, 1, args.length));
            File newDir;
            if (path.equals("..")) {
                newDir = (new File(CommandLineInterface.currentDirectory)).getParentFile();
                if (newDir != null) {
                    CommandLineInterface.currentDirectory = newDir.getAbsolutePath();
                    System.setProperty("user.dir", CommandLineInterface.currentDirectory);
                    System.out.println("Changed directory to: " + CommandLineInterface.currentDirectory);
                    CommandLineInterface.tests.add("Changed directory to: " + CommandLineInterface.currentDirectory);
                } else {
                    System.out.println("Error: Already at the root directory.");
                    CommandLineInterface.tests.add("Already at the root directory.");
                }

            } else {
                newDir = new File(CommandLineInterface.currentDirectory, path);
                if (newDir.isDirectory() && newDir.exists()) {
                    CommandLineInterface.currentDirectory = newDir.getAbsolutePath();
                    System.setProperty("user.dir", CommandLineInterface.currentDirectory);
                    System.out.println("Changed directory to: " + CommandLineInterface.currentDirectory);
                    CommandLineInterface.tests.add("Changed directory to: " + CommandLineInterface.currentDirectory);
                } else {
                    System.out.println("Error: invalid directory path.");
                    CommandLineInterface.tests.add("Error: invalid directory path.");
                }

            }
        }
    }
}
