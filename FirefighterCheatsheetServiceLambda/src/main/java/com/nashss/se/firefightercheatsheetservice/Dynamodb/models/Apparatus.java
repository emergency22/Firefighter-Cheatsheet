package com.nashss.se.firefightercheatsheetservice.Dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "apparatus")
public class Apparatus {

    private String userName;
    private String apparatusTypeAndNumber;
    private String fireDept;
    private List<Hose> hoseList;

    public Apparatus(String userName, String apparatusTypeAndNumber, String fireDept, List<Hose> hoseList) {
        this.userName = userName;
        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
        this.fireDept = fireDept;
        this.hoseList = hoseList;
    }

    @DynamoDBHashKey(attributeName = "userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @DynamoDBRangeKey(attributeName = "apparatusTypeAndNumber")
    public String getApparatusTypeAndNumber() {
        return apparatusTypeAndNumber;
    }

    public void setApparatusTypeAndNumber(String apparatusTypeAndNumber) {
        this.apparatusTypeAndNumber = apparatusTypeAndNumber;
    }

    @DynamoDBAttribute(attributeName = "fireDept")
    public String getFireDept() {
        return fireDept;
    }

    public void setFireDept(String fireDept) {
        this.fireDept = fireDept;
    }

    @DynamoDBAttribute(attributeName = "fireDept")
    public List<Hose> getHoseList() {
        return hoseList;
    }

    public void setHoseList(List<Hose> hoseList) {
        this.hoseList = hoseList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Apparatus)) return false;
        Apparatus apparatus = (Apparatus) o;
        return Objects.equals(userName, apparatus.userName) && Objects.equals(apparatusTypeAndNumber,
                apparatus.apparatusTypeAndNumber) && Objects.equals(fireDept, apparatus.fireDept);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, apparatusTypeAndNumber, fireDept);
    }
}
