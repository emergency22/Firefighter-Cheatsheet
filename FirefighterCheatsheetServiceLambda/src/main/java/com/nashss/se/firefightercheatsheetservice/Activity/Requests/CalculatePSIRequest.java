package com.nashss.se.firefightercheatsheetservice.Activity.Requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CalculatePSIRequest.Builder.class)
public class CalculatePSIRequest {
    private final String userName;
    private final String fireDept;
    private final String apparatusTypeAndNumber;
    private final int hoseIndexNumber;

    private CalculatePSIRequest(String userName, String fireDept, String apparatusTypeAndNumber,
        Integer hoseIndexNumber) {
        this.userName = userName;
        this.fireDept = fireDept;
        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
        this.hoseIndexNumber = hoseIndexNumber;
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

    public int getHoseIndexNumber() {
        return hoseIndexNumber;
    }

    @Override
    public String toString() {
        return "CalculatePSIRequest{" +
                "userName='" + userName + '\'' +
                "fireDept='" + fireDept + '\'' +
                "apparatusTypeAndNumber='" + apparatusTypeAndNumber + '\'' +
                "hoseIndexNumber='" + hoseIndexNumber + '\'' +
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
        private int hoseIndexNumber;

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

        public Builder withHoseIndexNumber(int hoseIndexNumber) {
            this.hoseIndexNumber = hoseIndexNumber;
            return this;
        }

        public CalculatePSIRequest build() {
            return new CalculatePSIRequest(userName, fireDept, apparatusTypeAndNumber, hoseIndexNumber);
        }
    }

}
