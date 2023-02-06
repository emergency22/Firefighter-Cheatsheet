package com.nashss.se.firefightercheatsheetservice.Activity.Requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetIndividualApparatusRequest.Builder.class)
public class GetIndividualApparatusRequest {
    private final String userName;
    private final String apparatusTypeAndNumber;

    private GetIndividualApparatusRequest(String userName, String apparatusTypeAndNumber) {
        this.userName = userName;
        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getApparatusTypeAndNumber() {
        return apparatusTypeAndNumber;
    }


    @Override
    public String toString() {
        return "GetIndividualApparatusRequest{" +
                "userName='" + userName + '\'' +
                "apparatusTypeAndNumber='" + apparatusTypeAndNumber + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userName;
        private String apparatusTypeAndNumber;

        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder withApparatusTypeAndNumber(String apparatusTypeAndNumber) {
            this.apparatusTypeAndNumber = apparatusTypeAndNumber;
            return this;
        }

        public GetIndividualApparatusRequest build() {
            return new GetIndividualApparatusRequest(userName, apparatusTypeAndNumber);
        }
    }

}
