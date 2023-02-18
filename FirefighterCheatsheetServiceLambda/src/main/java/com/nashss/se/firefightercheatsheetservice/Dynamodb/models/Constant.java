package com.nashss.se.firefightercheatsheetservice.Dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

@JsonDeserialize(builder = Constant.Builder.class)
@DynamoDBTable(tableName = "constants")
public class Constant {
    private String key;
    private String humanValue;
    private String computerValue;

    /**
     * Empty apparatus constructor.
     */
    public Constant() {}

    /**
     * Apparatus constructor.
     * @param key The key utilized as a Hash Key.
     * @param humanValue Individual human-readable value associated with the key.
     * @param computerValue the equal computer value.
     */
    public Constant(String key, String humanValue, String computerValue) {
        this.key = key;
        this.humanValue = humanValue;
        this.computerValue = computerValue;
    }

    @DynamoDBHashKey(attributeName = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @DynamoDBRangeKey(attributeName = "humanValue")
    public String getHumanValue() {
        return humanValue;
    }

    public void setHumanValue(String humanValue) {
        this.humanValue = humanValue;
    }

    public String getComputerValue() {
        return computerValue;
    }

    public void setComputerValue(String computerValue) {
        this.computerValue = computerValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Constant)) return false;
        Constant constant = (Constant) o;
        return Objects.equals(key, constant.key) && Objects.equals(humanValue, constant.humanValue) && Objects.equals(computerValue, constant.computerValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, humanValue, computerValue);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }


    @JsonPOJOBuilder
    public static class Builder {
        private String key;
        private String humanValue;

        private String computerValue;

        public Builder withKey(String key) {
            this.key = key;
            return this;
        }

        public Builder withHumanValue(String humanValue) {
            this.humanValue = humanValue;
            return this;
        }

        public Builder withComputerValue(String computerValue) {
            this.computerValue = computerValue;
            return this;
        }

        public Constant build() {
            return new Constant(key, humanValue, computerValue);
        }
    }
}
