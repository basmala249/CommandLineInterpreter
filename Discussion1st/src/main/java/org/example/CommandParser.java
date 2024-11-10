//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

public class CommandParser {
    public CommandParser() {
    }

    public String[] parse(String input) {
        input = input.trim();
        return input.split("\\s+");
    }
}
