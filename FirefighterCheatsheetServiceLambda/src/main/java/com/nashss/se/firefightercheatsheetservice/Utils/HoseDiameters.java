package com.nashss.se.firefightercheatsheetservice.Utils;

public enum HoseDiameters {

    INCH_AND_HALF(1.5),
    INCH_THREE_QUARTER(1.75),
    TWO_INCH(2),
    TWO_AND_HALF(2.5),
    THREE_INCH(3);

    private double diameter;

    /**
     * @param diameter The hose diameter.
     */
    HoseDiameters(double diameter) {
        this.diameter = diameter;
    }

    public double getDiameter() {
        return diameter;
    }

}
