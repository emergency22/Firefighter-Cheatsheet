package com.nashss.se.firefightercheatsheetservice.Utils;

public class FrictionLossCalculator {

    //FL = C * Q^2 * L
    //PDP = NP + FL + DL + EL

    //Q = GPM / 100
    //L = Hose length / 100

    /**
     * FrictionLossCalculator constructor.
     */
    public FrictionLossCalculator() {
    }

    /**
     * Method to calculate friction loss.
     * @param coefficient Hose coefficient based on diameter.
     * @param hoseLength Hose length.
     * @param gallons  Gallons per minute.
     * @return Integer class.
     */
    public static Integer calculateFrictionLoss(double coefficient, int hoseLength, int gallons) {
        Double adjustedGallons = (double) gallons / 100;
        Double adjustedHoseLength = (double) hoseLength / 100;
        Double doublePSI = coefficient * adjustedGallons * adjustedHoseLength;
        return doublePSI.intValue() + 100;
    }

}
