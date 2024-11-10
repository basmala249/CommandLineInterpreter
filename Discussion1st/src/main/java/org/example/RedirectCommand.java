//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class RedirectCommand implements Command {
    boolean append;

    public RedirectCommand(boolean append) {
        this.append = append;
    }

    public void execute(String[] args) {
        CommandLineInterface.RedirectOutput.clear();
        String[] s = this.append ? args[0].split(">>") : args[0].split(">");
        if (s.length != 2) {
            System.out.println("Invalid command\n");
        } else {
            String[] cmd = new String[]{s[0].trim()};
            String file = s[1].trim();
            String[] tokens = cmd[0].split(" ");
            if (tokens[0].equals("echo")) {
                StringBuilder sb = new StringBuilder();

                for(int i = 1; i < tokens.length; ++i) {
                    sb.append(tokens[i]).append(" ");
                }

                CommandLineInterface.RedirectOutput.add(sb.toString().trim());
            } else {
                Command curr_Command = (Command)CommandLineInterface.commands.get(tokens[0]);
                if (curr_Command != null) {
                    if (tokens[0].equals("ls")) {
                        curr_Command.execute(new String[0]);
                    } else if (tokens[0].equals("cat")) {
                        if (tokens.length == 1) {
                            File outputFile = new File(CommandLineInterface.currentDirectory, file);

                            try {
                                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, this.append));

                                try {
                                    System.out.println("Enter text to write to " + file + " (type 'EOF' on a new line to finish):");
                                    Scanner scan = new Scanner(System.in);

                                    while(true) {
                                        String tmp = scan.nextLine();
                                        if (tmp.equals("EOF")) {
                                            break;
                                        }

                                        bw.write(tmp);
                                        bw.newLine();
                                    }
                                } catch (Throwable var13) {
                                    try {
                                        bw.close();
                                    } catch (Throwable var11) {
                                        var13.addSuppressed(var11);
                                    }

                                    throw var13;
                                }

                                bw.close();
                            } catch (IOException var14) {
                                IOException e = var14;
                                System.err.println(e.getMessage());
                            }

                            return;
                        }

                        curr_Command.execute(tokens);
                    } else {
                        System.out.println("Command not found: " + tokens[0]);
                    }
                } else {
                    System.out.println("Command not found: " + tokens[0]);
                }
            }

            try {
                File outputFile = new File(CommandLineInterface.currentDirectory, file);
                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, this.append));

                try {
                    Iterator var23 = CommandLineInterface.RedirectOutput.iterator();

                    while(var23.hasNext()) {
                        String str = (String)var23.next();
                        bw.write(str);
                        bw.newLine();
                    }
                } catch (Throwable var15) {
                    try {
                        bw.close();
                    } catch (Throwable var12) {
                        var15.addSuppressed(var12);
                    }

                    throw var15;
                }

                bw.close();
            } catch (IOException var16) {
                System.err.println(var16.getMessage());
            }

        }
    }
}
