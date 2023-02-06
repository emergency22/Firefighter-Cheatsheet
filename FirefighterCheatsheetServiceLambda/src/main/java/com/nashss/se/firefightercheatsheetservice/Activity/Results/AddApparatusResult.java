package com.nashss.se.firefightercheatsheetservice.Activity.Results;

import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;

import java.util.List;

public class AddApparatusResult {
    private final List<ApparatusModel> apparatusModelList;

    private AddApparatusResult(List<ApparatusModel> apparatusModelList) {
        this.apparatusModelList = apparatusModelList;
    }

    public List<ApparatusModel> getApparatusModelList() {
        return apparatusModelList;
    }

    @Override
    public String toString() {
        return "AddApparatusResult{" +
                "apparatus=" + apparatusModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static AddApparatusResult.Builder builder() {
        return new AddApparatusResult.Builder();
    }

    public static class Builder {
        private List<ApparatusModel> apparatusModelList;

        public AddApparatusResult.Builder withApparatusModelList(List<ApparatusModel> apparatusModelList) {
            this.apparatusModelList = apparatusModelList;
            return this;
        }

        public AddApparatusResult build() {
            return new AddApparatusResult(apparatusModelList);
        }
    }
}
