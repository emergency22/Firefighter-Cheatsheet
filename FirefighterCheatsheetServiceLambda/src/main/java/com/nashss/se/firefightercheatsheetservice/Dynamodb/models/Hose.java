package com.nashss.se.firefightercheatsheetservice.Dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import java.util.Objects;

public class Hose {

    private String name;
    private String color;
    private int length;
    private Double hoseDiameter;
    private int waterQuantityInGallons;
    private int pumpDischargePressure;

    public Hose(String name, String color, int length, Double hoseDiameter, int waterQuantityInGallons) {
        this.name = name;
        this.color = color;
        this.length = length;
        this.hoseDiameter = hoseDiameter;
        this.waterQuantityInGallons = waterQuantityInGallons;
    }

    public Hose(String name, String color, int length, Double hoseDiameter, int waterQuantityInGallons, int pumpDischargePressure) {
        this.name = name;
        this.color = color;
        this.length = length;
        this.hoseDiameter = hoseDiameter;
        this.waterQuantityInGallons = waterQuantityInGallons;
        this.pumpDischargePressure = pumpDischargePressure;
    }

    @DynamoDBHashKey(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @DynamoDBAttribute(attributeName = "length")
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @DynamoDBAttribute(attributeName = "hoseDiameter")
    public Double getHoseDiameter() {
        return hoseDiameter;
    }

    public void setHoseDiameter(Double hoseDiameter) {
        this.hoseDiameter = hoseDiameter;
    }

    @DynamoDBAttribute(attributeName = "waterQuantityInGallons")
    public int getWaterQuantityInGallons() {
        return waterQuantityInGallons;
    }

    public void setWaterQuantityInGallons(int waterQuantityInGallons) {
        this.waterQuantityInGallons = waterQuantityInGallons;
    }

    @DynamoDBAttribute(attributeName = "pumpDischargePressure")
    public int getPumpDischargePressure() {
        return pumpDischargePressure;
    }

    public void setPumpDischargePressure(int pumpDischargePressure) {
        this.pumpDischargePressure = pumpDischargePressure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hose)) return false;
        Hose hose = (Hose) o;
        return length == hose.length &&
                waterQuantityInGallons == hose.waterQuantityInGallons &&
                pumpDischargePressure == hose.pumpDischargePressure &&
                Objects.equals(name, hose.name) &&
                Objects.equals(color, hose.color) &&
                Objects.equals(hoseDiameter, hose.hoseDiameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color, length, hoseDiameter, waterQuantityInGallons, pumpDischargePressure);
    }
}