package com.nashss.se.firefightercheatsheetservice.Utils;

public class FrictionLossCalculator {

    //FL = C * Q^2 * L
    //PDP = NP + FL + DL + EL

    //Q = GPM / 100
    //L = Hose length / 100

    private double coefficient;
    private int hoseLength;
    private int gallons;

    FrictionLossCalculator(double coefficient, int hoseLength, int gallons) {
        this.coefficient = coefficient;
        this.hoseLength = hoseLength;
        this.gallons = gallons;
    }

    public double calculateFrictionLoss() {
        double adjustedGallons = (double) this.gallons / 100;
        double adjustedHoseLength = (double) this.hoseLength / 100;

        return this.coefficient * adjustedGallons * adjustedHoseLength;
    }

}
