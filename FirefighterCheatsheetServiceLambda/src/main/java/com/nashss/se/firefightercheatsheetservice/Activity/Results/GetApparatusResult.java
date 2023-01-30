package com.nashss.se.firefightercheatsheetservice.Activity.Results;

import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;

import java.util.List;


public class GetApparatusResult {
    private final List<ApparatusModel> apparatusModelList;

    private GetApparatusResult(List<ApparatusModel> apparatusModelList) {
        this.apparatusModelList = apparatusModelList;
    }

    public List<ApparatusModel> getApparatusModelList() {
        return apparatusModelList;
    }

    @Override
    public String toString() {
        return "GetApparatusResult{" +
                "apparatus=" + apparatusModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<ApparatusModel> apparatusModelList;

        public Builder withApparatusModelList(List<ApparatusModel> apparatusModelList) {
            this.apparatusModelList = apparatusModelList;
            return this;
        }

        public GetApparatusResult build() {
            return new GetApparatusResult(apparatusModelList);
        }
    }
}