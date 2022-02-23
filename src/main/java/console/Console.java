package console;

import configuration.ConsoleCorrespondation;

public class Console implements IConsole {
    private static final String ansiReset = "\u001B[0m";
    private final TextColor defaultColor;

    public Console(TextColor color) {
        this.defaultColor = color;
    }

    @Override
    public String readln() {
        return null;
    }

    @Override
    public void writeln(String input, TextColor color) {
        System.out.println(color.getValue() + input + ansiReset);
    }

    public void writeln(String input) {
        writeln(input, defaultColor);
    }

//    public void writeln(ConsoleCorrespondation input, TextColor color){
//        writeln(input.getValue(), color);
//    }
//
//    public void writeln(ConsoleCorrespondation input){
//        writeln(input, defaultColor);
//    }
}
