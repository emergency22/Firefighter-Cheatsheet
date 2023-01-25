package com.nashss.se.firefightercheatsheetservice.Utils;

public enum HOSELENGTHS {

    FIFTY(50),
    ONE_HUNDRED(100),
    ONE_FIFTY(150),
    TWO_HUNDRED(200),
    TWO_FIFTY(250),
    THREE_HUNDRED(300),
    THREE_FIFTY(350),
    FOUR_HUNDRED(400),
    FOUR_FIFTY(450),
    FIVE_HUNDRED(500);

    private int id;

    HOSELENGTHS(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
