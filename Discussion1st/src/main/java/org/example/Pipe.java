//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.util.ArrayList;
import java.util.List;

public class Pipe implements Command {
    private final List<String[]> commands = new ArrayList();

    public Pipe() {
    }

    public void addCommand(String[] commandTokens) {
        this.commands.add(commandTokens);
    }

    public void execute(String[] args) {
        CommandLineInterface.Output.clear();
        CommandLineInterface.tempOutput.clear();

        for(int i = 0; i < this.commands.size(); ++i) {
            String[] inputArgs = (String[])this.commands.get(i);
            Command command = (Command)CommandLineInterface.commands.get(inputArgs[0]);
            if (i == 0) {
                command.execute(inputArgs);
                CommandLineInterface.Output.addAll(CommandLineInterface.tempOutput);
            }
            else {
                if (command instanceof GrepCommand) {
                    String[] grepArgs = new String[1 + CommandLineInterface.Output.size()];
                    grepArgs[0] = inputArgs[1];
                    System.arraycopy(CommandLineInterface.Output.toArray(new String[0]), 0, grepArgs, 1, CommandLineInterface.tempOutput.size());
                    command.execute(grepArgs);
                } else {
                    command.execute((String[])CommandLineInterface.Output.toArray(new String[0]));
                }

                CommandLineInterface.Output.clear();
                CommandLineInterface.Output.addAll(CommandLineInterface.tempOutput);
                CommandLineInterface.tempOutput.clear();
            }
        }

        CommandLineInterface.tests.addAll(CommandLineInterface.Output);
    }
}
