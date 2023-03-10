package com.nashss.se.firefightercheatsheetservice.Activity.Requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetApparatusRequest.Builder.class)
public class GetApparatusRequest {
    private final String userName;

    private GetApparatusRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "GetApparatusRequest{" +
                "userName='" + userName + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userName;

        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public GetApparatusRequest build() {
            return new GetApparatusRequest(userName);
        }
    }

}
