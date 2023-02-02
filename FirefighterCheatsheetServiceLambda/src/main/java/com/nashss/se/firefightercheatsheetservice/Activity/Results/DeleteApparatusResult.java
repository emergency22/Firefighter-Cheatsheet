package com.nashss.se.firefightercheatsheetservice.Activity.Results;

import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;

import java.util.List;

public class DeleteApparatusResult {
    private final List<ApparatusModel> apparatusModelList;

    private DeleteApparatusResult(List<ApparatusModel> apparatusModelList) {
        this.apparatusModelList = apparatusModelList;
    }

    public List<ApparatusModel> getApparatusModelList() {
        return apparatusModelList;
    }

    @Override
    public String toString() {
        return "DeleteApparatusResult{" +
                "apparatus=" + apparatusModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static DeleteApparatusResult.Builder builder() {
        return new DeleteApparatusResult.Builder();
    }

    public static class Builder {
        private List<ApparatusModel> apparatusModelList;

        public DeleteApparatusResult.Builder withApparatusModelList(List<ApparatusModel> apparatusModelList) {
            this.apparatusModelList = apparatusModelList;
            return this;
        }

        public DeleteApparatusResult build() {
            return new DeleteApparatusResult(apparatusModelList);
        }
    }
}
