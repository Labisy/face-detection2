package ru.stepchenkov.vision.type;

public enum FileType {
    VIDEO("src/main/resources/templates/test.mp4"),
    NET_MODEL("C:/diplom/openFace-model")
    ;

    private String filePath;

    FileType(String filePath) {
        this.filePath = filePath;
    }

    public String get() {
        return filePath;
    }
}
