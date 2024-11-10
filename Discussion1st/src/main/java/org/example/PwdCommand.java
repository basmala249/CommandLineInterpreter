//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

public class PwdCommand implements Command {
    public PwdCommand() {
    }

    public void execute(String[] args) {
        System.out.println(CommandLineInterface.currentDirectory);
        CommandLineInterface.tests.add(CommandLineInterface.currentDirectory);
    }
}
