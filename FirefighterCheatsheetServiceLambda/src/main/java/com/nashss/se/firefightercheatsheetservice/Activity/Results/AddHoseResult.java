package com.nashss.se.firefightercheatsheetservice.Activity.Results;

import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;

public class AddHoseResult {
    private final ApparatusModel apparatusModel;

    private AddHoseResult(ApparatusModel apparatusModel) {
        this.apparatusModel = apparatusModel;
    }

    public ApparatusModel getApparatusModel() {
        return apparatusModel;
    }

    @Override
    public String toString() {
        return "AddHoseResult{" +
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

        public AddHoseResult build() {
            return new AddHoseResult(apparatusModel);
        }
    }
}