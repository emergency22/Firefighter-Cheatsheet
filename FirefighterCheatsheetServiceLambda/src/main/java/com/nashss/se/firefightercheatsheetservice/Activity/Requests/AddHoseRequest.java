package com.nashss.se.firefightercheatsheetservice.Activity.Requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddHoseRequest.Builder.class)
public class AddHoseRequest {
    private final String userName;
    private final String fireDept;
    private final String apparatusTypeAndNumber;

    private final String name;
    private final String color;
    private final int length;
    private final Double diameter;
    private final int gallons;
    private final int pumpDischargePressure;

    private AddHoseRequest(String userName, String fireDept, String apparatusTypeAndNumber, String name,
        String color, Integer length, Double diameter, Integer gallons) {
        this.userName = userName;
        this.fireDept = fireDept;
        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
        this.name = name;
        this.color = color;
        this.length = length;
        this.diameter = diameter;
        this.gallons = gallons;
        this.pumpDischargePressure = 0;
    }

    public String getUserName() {
        return userName;
    }

    public String getFireDept() {
        return fireDept;
    }

    public String getApparatusTypeAndNumber() {
        return apparatusTypeAndNumber;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getLength() {
        return length;
    }

    public Double getDiameter() {
        return diameter;
    }

    public int getGallons() {
        return gallons;
    }

    public int getPumpDischargePressure() {
        return pumpDischargePressure;
    }

    @Override
    public String toString() {
        return "AddHoseRequest{" +
                "userName='" + userName + '\'' +
                "fireDept='" + fireDept + '\'' +
                "apparatusTypeAndNumber='" + apparatusTypeAndNumber + '\'' +
                "name='" + name + '\'' +
                "color='" + color + '\'' +
                "length='" + length + '\'' +
                "diameter='" + diameter + '\'' +
                "gallons='" + gallons + '\'' +
                "pumpDischargePressure='" + pumpDischargePressure + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userName;
        private String fireDept;
        private String apparatusTypeAndNumber;
        private String name;
        private String color;
        private int length;
        private Double diameter;
        private int gallons;
        private int pumpDischargePressure;

        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder withFireDept(String fireDept) {
            this.fireDept = fireDept;
            return this;
        }

        public Builder withApparatusTypeAndNumber(String apparatusTypeAndNumber) {
            this.apparatusTypeAndNumber = apparatusTypeAndNumber;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withColor(String color) {
            this.color = color;
            return this;
        }

        public Builder withLength(int length) {
            this.length = length;
            return this;
        }

        public Builder withDiameter(Double diameter) {
            this.diameter = diameter;
            return this;
        }

        public Builder withGallons(int gallons) {
            this.gallons = gallons;
            return this;
        }

        public Builder withPumpDischargePressure(int pumpDischargePressure) {
            this.pumpDischargePressure = pumpDischargePressure;
            return this;
        }

        public AddHoseRequest build() {
            return new AddHoseRequest(userName, fireDept, apparatusTypeAndNumber, name, color, length, diameter, gallons);
        }
    }

}
