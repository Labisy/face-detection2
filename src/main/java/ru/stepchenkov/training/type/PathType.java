package ru.stepchenkov.training.type;

public enum PathType {
    TRAIN_FEATURES("src/main/resources/templates/models/pairsDevTrain.txt"),
    TEST_FEATURES("src/main/resources/templates/models/pairsDevTest.txt"),
    ;
    private String path;

    PathType(String path) {
        this.path = path;
    }

    public String get() {
        return path;
    }
}
