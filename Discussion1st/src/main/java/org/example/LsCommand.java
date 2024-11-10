//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.io.File;
import java.util.Arrays;

public class LsCommand implements Command {
    public LsCommand() {
    }

    public void execute(String[] args) {
        boolean showAll = false;
        boolean reverseOrder = false;
        String[] var4 = args;
        int var5 = args.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String arg = var4[var6];
            if (arg.equals("-a")) {
                showAll = true;
            } else if (arg.equals("-r")) {
                reverseOrder = true;
            }
        }

        String currentDir = CommandLineInterface.currentDirectory;
        File directory = new File(currentDir);
        File[] files = directory.listFiles();
        if (files != null) {
            if (!showAll) {
                files = this.filterHiddenFiles(files);
            }

            if (reverseOrder) {
                files = this.reverseArray(files);
            }

            CommandLineInterface.tempOutput.clear();
            File[] var14 = files;
            int var8 = files.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                File file = var14[var9];
                if (CommandLineInterface.PIPE) {
                    CommandLineInterface.tempOutput.add(file.getName());
                } else if (CommandLineInterface.REDIRECT) {
                    CommandLineInterface.RedirectOutput.add(file.getName());
                } else {
                    System.out.println(file.getName());
                    CommandLineInterface.tests.add(file.getName());
                }
            }
        } else {
            System.out.println("Error: Unable to access the directory.");
            CommandLineInterface.tests.add("Error: Unable to access the directory.");
        }

    }

    private File[] filterHiddenFiles(File[] files) {
        return (File[])Arrays.stream(files).filter((file) -> {
            return !file.getName().startsWith(".");
        }).toArray((x$0) -> {
            return new File[x$0];
        });
    }

    private File[] reverseArray(File[] files) {
        File[] reversed = new File[files.length];

        for(int i = 0; i < files.length; ++i) {
            reversed[i] = files[files.length - 1 - i];
        }

        return reversed;
    }
}
