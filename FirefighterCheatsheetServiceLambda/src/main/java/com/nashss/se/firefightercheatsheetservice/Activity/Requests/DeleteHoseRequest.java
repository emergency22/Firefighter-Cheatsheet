package com.nashss.se.firefightercheatsheetservice.Activity.Requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteHoseRequest.Builder.class)
public class DeleteHoseRequest {
    private final String userName;
    private final String fireDept;
    private final String apparatusTypeAndNumber;
    private final int hoseIndexNumber;

    private DeleteHoseRequest(String userName, String fireDept, String apparatusTypeAndNumber,
        Integer hoseIndexNumber) {
        this.userName = userName;
        this.fireDept = fireDept;
        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
        this.hoseIndexNumber =  hoseIndexNumber;
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
        return "DeleteHoseRequest{" +
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
        private Integer hoseIndexNumber;

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

        public Builder withHoseIndexNumber(Integer hoseIndexNumber) {
            this.hoseIndexNumber = hoseIndexNumber;
            return this;
        }

        public DeleteHoseRequest build() {
            return new DeleteHoseRequest(userName, fireDept, apparatusTypeAndNumber, hoseIndexNumber);
        }
    }

}
