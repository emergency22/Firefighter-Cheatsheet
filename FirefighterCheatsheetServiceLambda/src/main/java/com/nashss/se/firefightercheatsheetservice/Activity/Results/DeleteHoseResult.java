package com.nashss.se.firefightercheatsheetservice.Activity.Results;

import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;

public class DeleteHoseResult {
    private final ApparatusModel apparatusModel;

    private DeleteHoseResult(ApparatusModel apparatusModel) {
        this.apparatusModel = apparatusModel;
    }

    public ApparatusModel getApparatusModel() {
        return apparatusModel;
    }

    @Override
    public String toString() {
        return "DeleteHoseResult{" +
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

        public DeleteHoseResult build() {
            return new DeleteHoseResult(apparatusModel);
        }
    }
}
