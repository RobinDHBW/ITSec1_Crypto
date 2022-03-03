package console;

import configuration.ConsoleCorrespondation;

public interface IConsole {
    void writeln(ConsoleCorrespondation input, TextColor color);
    void writeln(String input, TextColor color);
}
