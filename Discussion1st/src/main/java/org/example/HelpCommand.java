//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

public class HelpCommand implements Command {
    public HelpCommand() {
    }

    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: help(has no parameter)");
        } else {
            String help_string = "pwd             : print the path of current directory.\nls              : view all files in the current directory.\nls -a           : view all files, including hidden files.\nls -r           : view all files in the current directory in reverse order.\nmkdir <dir>     : make a new directory with name <dir>.\nrmdir <dir>     : delete directory <dir> if it is empty.\ntouch <file>    : create a new file with the name <file>.\nmv <src> <dest> : move from <src> to <dest>.\nrm <file>       : remove the file <file>.\ncd <dir>        : change the current directory to <dir>.\ncat <file>      : list the contents of <file>.\n> <file>        : create or overwrites the <file>.\n>> <file>       : append text to <file>.\n|               : pipe output of one command to another.\nexit            : to quit.\n";
            System.out.println(help_string);
        }
    }
}
