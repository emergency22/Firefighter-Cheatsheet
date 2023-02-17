package com.nashss.se.firefightercheatsheetservice.Activity.Requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetConstantsRequest.Builder.class)
public class GetConstantsRequest {
    private final String userName;

    private GetConstantsRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "GetConstantsRequest{" +
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

        public GetConstantsRequest build() {
            return new GetConstantsRequest(userName);
        }
    }

}
