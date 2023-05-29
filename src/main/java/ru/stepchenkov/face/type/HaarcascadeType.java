package ru.stepchenkov.face.type;

public enum HaarcascadeType {
    FRONTALFACE("src/main/resources/templates/haarcascade/haarcascade_frontalface_default.xml"),
    FRONTALFACE_ALT("src/main/resources/templates/haarcascade/haarcascade_frontalface_alt.xml");
    private String path;

    HaarcascadeType(String path) {
        this.path = path;
    }

    public String get() {
        return path;
    }
}
