package console;

import configuration.ConsoleCorrespondation;

public interface IConsole {
    void writeln(ConsoleCorrespondation input, TextColor color);
    String readln();
}
