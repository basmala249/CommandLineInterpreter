//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CatCommand implements Command {
    public CatCommand() {
    }

    public void execute(String[] args) {
        if (args[0].equals("cat")) {
            args = (String[])Arrays.copyOfRange(args, 1, args.length);
        }

        if (args.length < 1) {
            System.out.println("Usage: cat <file1>");
        } else {
            String currentDirectory = CommandLineInterface.currentDirectory;
            CommandLineInterface.tempOutput.clear();

            for(int i = 0; i < args.length; ++i) {
                String filePath = args[i];
                File file = new File(currentDirectory, filePath);
                if (file.exists() && file.isFile()) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));

                        String line;
                        try {
                            while((line = br.readLine()) != null) {
                                if (CommandLineInterface.PIPE) {
                                    if(file.isFile())
                                       CommandLineInterface.tempOutput.add(line);
                                } else if (CommandLineInterface.REDIRECT) {
                                    CommandLineInterface.RedirectOutput.add(line);
                                } else {
                                    System.out.println(line);
                                    CommandLineInterface.tests.add(line);
                                }
                            }
                        } catch (Throwable var10) {
                            try {
                                br.close();
                            } catch (Throwable var9) {
                                var10.addSuppressed(var9);
                            }

                            throw var10;
                        }

                        br.close();
                    } catch (IOException var11) {
                        IOException e = var11;
                        if(!CommandLineInterface.PIPE) {
                            System.out.printf("Error reading file '%s': %s%n", file.getAbsolutePath(), e.getMessage());
                            CommandLineInterface.tests.add("Error reading file '%s': %s%n" + file.getAbsolutePath() + e.getMessage());

                        }
                    }
                } else {
                    if(!CommandLineInterface.PIPE) {
                        System.out.printf("Error reading file");
                        CommandLineInterface.tests.add("Error reading file");
                    }
                }
            }

        }
    }
}
