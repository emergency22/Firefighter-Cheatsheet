package com.nashss.se.firefightercheatsheetservice.Activity.Requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@JsonDeserialize(builder = DeleteApparatusRequest.Builder.class)
public class DeleteApparatusRequest {
    private final String userName;
    private final String apparatusTypeAndNumber;

    private DeleteApparatusRequest(String userName, String apparatusTypeAndNumber) {
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
        return "DeleteApparatusRequest{" +
                "userName='" + userName + '\'' +
                ", apparatusTypeAndNumber=" + apparatusTypeAndNumber +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static DeleteApparatusRequest.Builder builder() {
        return new DeleteApparatusRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userName;
        private String apparatusTypeAndNumber;

        public DeleteApparatusRequest.Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public DeleteApparatusRequest.Builder withApparatusTypeAndNumber(String apparatusTypeAndNumber) throws UnsupportedEncodingException {
            this.apparatusTypeAndNumber = URLDecoder.decode(apparatusTypeAndNumber, StandardCharsets.UTF_8.toString());
            return this;
        }

        public DeleteApparatusRequest build() {
            return new DeleteApparatusRequest(userName, apparatusTypeAndNumber);
        }
    }

}

