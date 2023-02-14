package com.nashss.se.firefightercheatsheetservice.Activity.Results;

import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;

public class GetIndividualApparatusResult {
    private final ApparatusModel apparatusModel;

    private GetIndividualApparatusResult(ApparatusModel apparatusModel) {
        this.apparatusModel = apparatusModel;
    }

    public ApparatusModel getApparatusModel() {
        return apparatusModel;
    }

    @Override
    public String toString() {
        return "GetIndividualApparatusResult{" +
                "apparatusModel=" + apparatusModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ApparatusModel apparatusModel;

        public Builder withApparatusModel(ApparatusModel apparatusModel) {
            this.apparatusModel = apparatusModel;
            return this;
        }

        public GetIndividualApparatusResult build() {
            return new GetIndividualApparatusResult(apparatusModel);
        }
    }
}
