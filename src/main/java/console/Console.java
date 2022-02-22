package console;

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
}
