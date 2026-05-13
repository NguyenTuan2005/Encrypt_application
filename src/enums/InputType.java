package enums;

public enum InputType {
    TEXT_TYPE("TEXT"),
    FILE_TYPE("FILE");

    private final String typeName;

    InputType(String typeName) {
        this.typeName = typeName;
    }
}
