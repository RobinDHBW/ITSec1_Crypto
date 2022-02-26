package console;

public enum TextColor {
    BLACK("\u001B[30m"), WHITE("\u001B[37m"), RED("\u001B[31m"), GREEN("\u001B[32m");

    private final String value;

    TextColor(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
