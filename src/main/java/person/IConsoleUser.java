package person;

import configuration.ConsoleCorrespondation;
import console.TextColor;

public interface IConsoleUser {
    void writeToConsole(ConsoleCorrespondation text, TextColor color);
}
