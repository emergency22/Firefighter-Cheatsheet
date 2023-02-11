package com.nashss.se.firefightercheatsheetservice.Dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nashss.se.firefightercheatsheetservice.Converters.HoseListConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonDeserialize(builder = Coefficient.Builder.class)
@DynamoDBTable(tableName = "coefficient")
public class Coefficient {
    private Double hoseDiameter;
    private Double coefficient;

    public Coefficient() {}

    public Coefficient(Double hoseDiameter, Double coefficient) {
        this.hoseDiameter = hoseDiameter;
        this.coefficient = coefficient;
    }

    @DynamoDBHashKey(attributeName = "hoseDiameter")
    public Double getHoseDiameter() {
        return hoseDiameter;
    }

    public void setHoseDiameter(Double hoseDiameter) {
        this.hoseDiameter = hoseDiameter;
    }

    @DynamoDBAttribute (attributeName = "coefficient")
    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coefficient)) return false;
        Coefficient that = (Coefficient) o;
        return Objects.equals(hoseDiameter, that.hoseDiameter) && Objects.equals(coefficient, that.coefficient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hoseDiameter, coefficient);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "Coefficient{" +
                "hoseDiameter=" + hoseDiameter +
                ", coefficient=" + coefficient +
                '}';
    }

    @JsonPOJOBuilder
    public static class Builder {
        private Double hoseDiameter;
        private Double coefficient;

        public Builder withHoseDiameter(Double hoseDiameter) {
            this.hoseDiameter = hoseDiameter;
            return this;
        }

        public Builder withCoefficient(Double coefficient) {
            this.coefficient = coefficient;
            return this;
        }

        public Coefficient build() {
            return new Coefficient(hoseDiameter, coefficient);
        }
    }
}
