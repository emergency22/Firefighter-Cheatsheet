package com.nashss.se.firefightercheatsheetservice.Activity.Results;

import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;

public class GetApparatusResult {
    private final ApparatusModel apparatus;

    private GetApparatusResult(ApparatusModel apparatus) {
        this.apparatus = apparatus;
    }

    public ApparatusModel getApparatus() {
        return apparatus;
    }

    @Override
    public String toString() {
        return "GetApparatusResult{" +
                "apparatus=" + apparatus +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ApparatusModel apparatus;

        public Builder withApparatus(ApparatusModel apparatus) {
            this.apparatus = apparatus;
            return this;
        }

        public GetApparatusResult build() {
            return new GetApparatusResult(apparatus);
        }
    }
}