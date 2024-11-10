//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.io.File;

public class MkdirCommand implements Command {
    public MkdirCommand() {
    }

    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: mkdir <directory>");
        } else {
            File current = new File(System.getProperty("user.dir"));
            String dir = args[1];
            File directory = new File(current, dir);
            String[] result = new String[1];
            if (directory.exists()) {
                result[0] = "Directory already exists: " + directory.getAbsolutePath();
            } else if (directory.mkdirs()) {
                result[0] = "Directory created: " + directory.getAbsolutePath();
            } else {
                result[0] = "Creation failed: " + directory.getAbsolutePath();
            }

            System.out.println(result[0]);
            CommandLineInterface.tests.add(result[0]);
        }
    }
}
