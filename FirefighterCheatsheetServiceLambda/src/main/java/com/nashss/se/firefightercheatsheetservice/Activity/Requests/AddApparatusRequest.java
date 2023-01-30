package com.nashss.se.firefightercheatsheetservice.Activity.Requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddApparatusRequest.Builder.class)
public class AddApparatusRequest {
    private final String userName;
    private final String apparatusTypeAndNumber;
    private final String fireDept;

    private AddApparatusRequest(String userName, String apparatusTypeAndNumber, String fireDept) {
        this.userName = userName;
        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
        this.fireDept = fireDept;
    }

    public String getUserName() {
        return userName;
    }

    public String getApparatusTypeAndNumber() {
        return apparatusTypeAndNumber;
    }

    public String getFireDept() {
        return fireDept;
    }

    @Override
    public String toString() {
        return "AddApparatusRequest{" +
                "userName='" + userName + '\'' +
                ", apparatusTypeAndNumber='" + apparatusTypeAndNumber + '\'' +
                ", fireDept=" + fireDept +
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
        private String fireDept;

        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder withApparatusTypeAndNumber(String apparatusTypeAndNumber) {
            this.apparatusTypeAndNumber = apparatusTypeAndNumber;
            return this;
        }

        public Builder withFireDept(String fireDept) {
            this.fireDept = fireDept;
            return this;
        }

        public AddApparatusRequest build() {
            return new AddApparatusRequest(userName, apparatusTypeAndNumber, fireDept);
        }
    }
}