package com.nashss.se.firefightercheatsheetservice.Activity.Results;

import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;

public class CalculatePSIResult {
    private final ApparatusModel apparatusModel;

    private CalculatePSIResult(ApparatusModel apparatusModel) {
        this.apparatusModel = apparatusModel;
    }

    public ApparatusModel getApparatusModel() {
        return apparatusModel;
    }

    @Override
    public String toString() {
        return "CalculatePSIResult{" +
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

        public CalculatePSIResult build() {
            return new CalculatePSIResult(apparatusModel);
        }
    }
}
