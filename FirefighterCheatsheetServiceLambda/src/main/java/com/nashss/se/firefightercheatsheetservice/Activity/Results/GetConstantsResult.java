package com.nashss.se.firefightercheatsheetservice.Activity.Results;

import com.nashss.se.firefightercheatsheetservice.Models.ConstantModel;

import java.util.List;

public class GetConstantsResult {
    private final List<ConstantModel> constantModelList;

    private GetConstantsResult(List<ConstantModel> constantModelList) {
        this.constantModelList = constantModelList;
    }

    public List<ConstantModel> getConstantModelList() {
        return constantModelList;
    }

    @Override
    public String toString() {
        return "GetConstantsResult{" +
                "constants=" + constantModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<ConstantModel> constantModelList;

        public Builder withConstantModelList(List<ConstantModel> constantModelList) {
            this.constantModelList = constantModelList;
            return this;
        }

        public GetConstantsResult build() {
            return new GetConstantsResult(constantModelList);
        }
    }
}
