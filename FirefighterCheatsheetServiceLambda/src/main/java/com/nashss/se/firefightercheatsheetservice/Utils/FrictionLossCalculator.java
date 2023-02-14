package com.nashss.se.firefightercheatsheetservice.Utils;

public class FrictionLossCalculator {

    //FL = C * Q^2 * L
    //PDP = NP + FL + DL + EL

    //Q = GPM / 100
    //L = Hose length / 100

    private double coefficient;
    private int hoseLength;
    private int gallons;

    /**
     * FrictionLossCalculator constructor.
     * @param coefficient Hose coefficient.
     * @param hoseLength Hose length.
     * @param gallons Gallons per minute.
     */
    public FrictionLossCalculator(double coefficient, int hoseLength, int gallons) {
        this.coefficient = coefficient;
        this.hoseLength = hoseLength;
        this.gallons = gallons;
    }

    /**
     * Method to calculate friction loss.
     * @return Integer class.
     */
    public Integer calculateFrictionLoss() {
        Double adjustedGallons = (double) this.gallons / 100;
        Double adjustedHoseLength = (double) this.hoseLength / 100;
        Double doublePSI = this.coefficient * adjustedGallons * adjustedHoseLength;
        return doublePSI.intValue() + 100;
    }

}
