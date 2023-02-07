package com.nashss.se.firefightercheatsheetservice.Dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonDeserialize(builder = Apparatus.Builder.class)
@DynamoDBTable(tableName = "apparatus")
public class Apparatus {
    public static final String FIRE_DEPT_APP_TYPE_NUM_INDEX = "FireDeptAndAppTypeNumIndex";

    private String userName;
    private String apparatusTypeAndNumber;
    private String fireDept;
    private List<Hose> hoseList;

    public Apparatus() {}

    public Apparatus(String userName, String apparatusTypeAndNumber, String fireDept, List<Hose> hoseList) {
        this.userName = userName;
        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
        this.fireDept = fireDept;
        this.hoseList = new ArrayList<>(hoseList);
    }

    public Apparatus(String userName, String apparatusTypeAndNumber, String fireDept) {
        this.userName = userName;
        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
        this.fireDept = fireDept;
    }

    @DynamoDBHashKey(attributeName = "userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @DynamoDBRangeKey(attributeName = "apparatusTypeAndNumber")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = FIRE_DEPT_APP_TYPE_NUM_INDEX, attributeName = "apparatusTypeAndNumber")
    public String getApparatusTypeAndNumber() {
        return apparatusTypeAndNumber;
    }

    public void setApparatusTypeAndNumber(String apparatusTypeAndNumber) {
        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
    }

    @DynamoDBAttribute(attributeName = "fireDept")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = FIRE_DEPT_APP_TYPE_NUM_INDEX, attributeName = "fireDept")
    public String getFireDept() {
        return fireDept;
    }

    public void setFireDept(String fireDept) {
        this.fireDept = fireDept;
    }

    @DynamoDBAttribute(attributeName = "hoseList")
    public List<Hose> getHoseList() {
        if (hoseList != null) {
            return new ArrayList<>(hoseList);
        }
        else {
            return new ArrayList<>();
        }
    }

    public void setHoseList(List<Hose> hoseList) {
        this.hoseList = new ArrayList<>(hoseList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Apparatus)) return false;
        Apparatus apparatus = (Apparatus) o;
        return Objects.equals(userName, apparatus.userName) &&
                Objects.equals(apparatusTypeAndNumber, apparatus.apparatusTypeAndNumber) &&
                Objects.equals(fireDept, apparatus.fireDept) &&
                Objects.equals(hoseList, apparatus.hoseList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, apparatusTypeAndNumber, fireDept, hoseList);
    }


    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }


    @JsonPOJOBuilder
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
            this.hoseList = hoseList;
            return this;
        }

        public Apparatus build() {
            return new Apparatus(userName, apparatusTypeAndNumber, fireDept, hoseList);
        }
    }
}
