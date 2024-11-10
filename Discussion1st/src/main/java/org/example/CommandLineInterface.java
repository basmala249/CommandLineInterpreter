//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CommandLineInterface {
    public static String currentDirectory = System.getProperty("user.dir");
    public static final Map<String, Command> commands = new HashMap();
    public static final CommandParser parser = new CommandParser();
    public static List<String> Output = new ArrayList();
    public static List<String> RedirectOutput = new ArrayList();
    public static List<String> tempOutput = new ArrayList();
    public static boolean PIPE = false;
    public static boolean REDIRECT = false;
    public static List<String> tests = new ArrayList();

    public CommandLineInterface() {
        commands.put("pwd", new PwdCommand());
        commands.put("cd", new CdCommand());
        commands.put("ls", new LsCommand());
        commands.put("mkdir", new MkdirCommand());
        commands.put("rmdir", new RmdirCommand());
        commands.put("touch", new TouchCommand());
        commands.put("mv", new MvCommand());
        commands.put("rm", new RmCommand());
        commands.put("cat", new CatCommand());
        commands.put("help", new HelpCommand());
        commands.put("grep", new GrepCommand());
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type 'help' for a list of commands. Type 'exit' to quit.");

        while(true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.equals("exit")) {
                System.out.println("Exiting...");
                return;
            }

            tests.clear();
            Output.clear();
            PIPE = false;
            REDIRECT = false;
            RedirectOutput.clear();
            String[] tokens;
            if (input.contains("|")) {
                PIPE = true;
                tokens = input.split("\\|");
                this.executePipedCommands(tokens);
            } else {
                Object cmd;
                if (input.contains(">>")) {
                    REDIRECT = true;
                    tokens = new String[]{input};
                    cmd = new RedirectCommand(true);
                } else if (input.contains(">")) {
                    REDIRECT = true;
                    tokens = new String[]{input};
                    cmd = new RedirectCommand(false);
                } else {
                    tokens = parser.parse(input);
                    cmd = (Command)CommandLineInterface.commands.get(tokens[0]);
                }

                if (cmd != null) {
                    ((Command)cmd).execute(tokens);
                } else {
                    System.out.println("Command not found: " + tokens[0]);
                }
            }

            if (PIPE) {
                this.print();
            }
        }
    }

    private void print() {
        Iterator var1 = Output.iterator();

        while(var1.hasNext()) {
            String output = (String)var1.next();
            System.out.println(output);
        }

    }

    private void executePipedCommands(String[] commands) {
        Pipe pipe = new Pipe();
        String[] tok = new String[0];
        String[] var4 = commands;
        int var5 = commands.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String commandStr = var4[var6];
            String trimmedCommandStr = commandStr.trim();
            String[] tokens = parser.parse(trimmedCommandStr);
            if (tokens.length == 0) {
                System.out.println("Empty command detected.");
            } else {
                Command currentCommand = (Command)CommandLineInterface.commands.get(tokens[0]);
                if (currentCommand != null) {
                    pipe.addCommand(tokens);
                    tok = tokens;
                } else {
                    System.out.println("Command not found: " + tokens[0]);
                }
            }
        }

        pipe.execute(tok);
    }
}
