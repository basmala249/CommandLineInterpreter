//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.util.Arrays;
import java.util.Iterator;

public class GrepCommand implements Command {
    public GrepCommand() {
    }

    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: No pattern provided for grep.");
        } else {
            String pattern = args[0];
            CommandLineInterface.tempOutput.clear();
            args = (String[])Arrays.copyOfRange(args, 1, args.length);
            String[] var3 = args;
            int var4 = args.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String line = var3[var5];
                if (line.contains(pattern)) {
                    if (CommandLineInterface.PIPE) {
                        CommandLineInterface.tempOutput.add(line);
                    } else {
                        CommandLineInterface.tests.add(line);
                    }
                }
            }

            if (!CommandLineInterface.PIPE) {
                Iterator var7 = CommandLineInterface.tempOutput.iterator();

                while(var7.hasNext()) {
                    String result = (String)var7.next();
                    System.out.println(result);
                }
            }

        }
    }
}
