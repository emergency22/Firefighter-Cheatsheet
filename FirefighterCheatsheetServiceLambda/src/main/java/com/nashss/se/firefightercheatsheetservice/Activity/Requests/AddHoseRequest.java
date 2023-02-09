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
    private final Double hoseDiameter;
    private final int waterQuantityInGallons;
    private final int pumpDischargePressure;

//    private AddHoseRequest(String userName, String fireDept, String apparatusTypeAndNumber, String name, String color, Integer length, Double hoseDiameter, Integer waterQuantityInGallons, Integer pumpDischargePressure) {
//        this.userName = userName;
//        this.fireDept = fireDept;
//        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
//        this.name = name;
//        this.color = color;
//        this.length = length;
//        this.hoseDiameter = hoseDiameter;
//        this.waterQuantityInGallons = waterQuantityInGallons;
//        this.pumpDischargePressure = pumpDischargePressure;
//    }

    private AddHoseRequest(String userName, String fireDept, String apparatusTypeAndNumber, String name, String color, Integer length, Double hoseDiameter, Integer waterQuantityInGallons) {
        this.userName = userName;
        this.fireDept = fireDept;
        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
        this.name = name;
        this.color = color;
        this.length = length;
        this.hoseDiameter = hoseDiameter;
        this.waterQuantityInGallons = waterQuantityInGallons;
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

    public Double getHoseDiameter() {
        return hoseDiameter;
    }

    public int getWaterQuantityInGallons() {
        return waterQuantityInGallons;
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
                "hoseDiameter='" + hoseDiameter + '\'' +
                "waterQuantityInGallons='" + waterQuantityInGallons + '\'' +
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
        private Double hoseDiameter;
        private int waterQuantityInGallons;
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

        public Builder withHoseDiameter(Double hoseDiameter) {
            this.hoseDiameter = hoseDiameter;
            return this;
        }

        public Builder withWaterQuantityInGallons(int waterQuantityInGallons) {
            this.waterQuantityInGallons = waterQuantityInGallons;
            return this;
        }

        public Builder withPumpDischargePressure(int pumpDischargePressure) {
            this.pumpDischargePressure = pumpDischargePressure;
            return this;
        }

        public AddHoseRequest build() {
            return new AddHoseRequest(userName, fireDept, apparatusTypeAndNumber, name, color, length, hoseDiameter, waterQuantityInGallons);
        }
    }

}
