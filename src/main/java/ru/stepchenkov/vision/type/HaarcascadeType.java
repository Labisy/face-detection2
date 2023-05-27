package ru.stepchenkov.vision.type;

public enum HaarcascadeType {
    FRONTALFACE("src/main/resources/templates/haarcascade/haarcascade_frontalface_default.xml");
    private String path;

    HaarcascadeType(String path) {
        this.path = path;
    }

    public String get() {
        return path;
    }
}
