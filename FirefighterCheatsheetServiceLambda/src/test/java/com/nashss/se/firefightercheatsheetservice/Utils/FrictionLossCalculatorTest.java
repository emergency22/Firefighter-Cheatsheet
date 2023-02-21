package com.nashss.se.firefightercheatsheetservice.Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FrictionLossCalculatorTest {

    @Test
    public void calculateFrictionLoss_validRequest_returnsCorrectValue() {
        //GIVEN
        double coefficient = 8;
        int hoseLength = 300;
        int gallons = 200;

        //WHEN
        Integer result = FrictionLossCalculator.calculateFrictionLoss(coefficient, hoseLength, gallons);
        //THEN

        assertEquals(148, result);

    }

}