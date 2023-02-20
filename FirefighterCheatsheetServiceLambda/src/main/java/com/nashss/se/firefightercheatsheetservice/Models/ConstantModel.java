package com.nashss.se.firefightercheatsheetservice.Models;

public class ConstantModel {
    private final String key;
    private final String humanValue;
    private final String computerValue;

    private ConstantModel(String key, String humanValue, String computerValue) {
        this.key = key;
        this.humanValue = humanValue;
        this.computerValue = computerValue;
    }

    public String getKey() {
        return key;
    }

    public String getHumanValue() {
        return humanValue;
    }

    public String getComputerValue() {
        return computerValue;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

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

    public ConstantModel build() {
        return new ConstantModel(key, humanValue, computerValue);
    }
    }
}

