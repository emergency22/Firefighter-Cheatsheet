package com.nashss.se.firefightercheatsheetservice.Models;

import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Hose;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.nashss.se.firefightercheatsheetservice.Utils.CollectionUtils.copyToList;

public class ApparatusModel {
    private final String userName;
    private final String apparatusTypeAndNumber;
    private final String fireDept;
    private List<Hose> hoseList;

    private ApparatusModel(String userName, String apparatusTypeAndNumber, String fireDept, List<Hose> hoseList) {
        this.userName = userName;
        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
        this.fireDept = fireDept;
        this.hoseList = new ArrayList<>(hoseList);
    }

    private ApparatusModel(String userName, String apparatusTypeAndNumber, String fireDept) {
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

    public List<Hose> getHoseList() {
        return copyToList(hoseList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApparatusModel)) {
            return false;
        }
        ApparatusModel that = (ApparatusModel) o;
        return Objects.equals(userName, that.userName) &&
             Objects.equals(apparatusTypeAndNumber, that.apparatusTypeAndNumber) &&
             Objects.equals(fireDept, that.fireDept) &&
             Objects.equals(hoseList, that.hoseList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, apparatusTypeAndNumber, fireDept, hoseList);
    }


        //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userName;
        private String apparatusTypeAndNumber;
        private String fireDept;
        private List<Hose> hoseList;

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

    public Builder withHoseList(List<Hose> hoseList) {
        this.hoseList = copyToList(hoseList);
        return this;
    }

    public ApparatusModel build() {
        return new ApparatusModel(userName, apparatusTypeAndNumber, fireDept, hoseList);
    }
    }
}

