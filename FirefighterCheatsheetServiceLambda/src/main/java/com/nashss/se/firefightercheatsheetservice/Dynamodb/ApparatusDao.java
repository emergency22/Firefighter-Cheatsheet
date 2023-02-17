package com.nashss.se.firefightercheatsheetservice.Dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Coefficient;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Constant;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Hose;
import com.nashss.se.firefightercheatsheetservice.Exceptions.*;
import com.nashss.se.firefightercheatsheetservice.Metrics.MetricsConstants;
import com.nashss.se.firefightercheatsheetservice.Metrics.MetricsPublisher;
import com.nashss.se.firefightercheatsheetservice.Utils.FrictionLossCalculator;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus.FIRE_DEPT_APP_TYPE_NUM_INDEX;

/**
 * Accesses data for a List of Apparatus using {@link Apparatus} to represent the model in DynamoDB.
 */
@Singleton
public class ApparatusDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    /**
     * Instantiates a ApparatusDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the Apparatus table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public ApparatusDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Returns the List of Apparatus.
     *
     * @param userName the userName associated with the logged in account.
     * @return the List of Apparatus.
     */
    public List<Apparatus> getApparatus(String userName) {
        log.info("getApparatus method called in ApparatusDao with userName: " + userName);

        Apparatus apparatus = new Apparatus();
        apparatus.setUserName(userName);
        DynamoDBQueryExpression<Apparatus> queryExpression = new DynamoDBQueryExpression<Apparatus>()
                .withHashKeyValues(apparatus);
        PaginatedQueryList<Apparatus> apparatusList = this.dynamoDbMapper.query(Apparatus.class, queryExpression);

        if (apparatusList == null) {
            log.info("ApparatusDao: getApparatus method has returned a null apparatusList");
            metricsPublisher.addCount(MetricsConstants.GETAPPARATUS_APPARTATUSLISTNOTFOUND_COUNT, 1);
            throw new ApparatusListNotFoundException("Could not find apparatus for user name: " + userName);
        }
        metricsPublisher.addCount(MetricsConstants.GETAPPARATUS_APPARTATUSLISTNOTFOUND_COUNT, 0);
        return apparatusList;
    }

    /**
     * Returns the List of Apparatus.
     *
     * @param userName the userName associated with the logged in account.
     * @param apparatusTypeAndNumber the apparatus type and number.
     * @return the List of Apparatus.
     */
    public List<Apparatus> deleteApparatus(String userName, String apparatusTypeAndNumber) {
        log.info("ApparatusDao: deleteApparatus method accessed with with userName: " + userName +
                " and apparatusTypeAndNumber: " + apparatusTypeAndNumber);

        Apparatus apparatus = new Apparatus();
        apparatus.setUserName(userName);
        apparatus.setApparatusTypeAndNumber(apparatusTypeAndNumber);

        try {
            dynamoDbMapper.delete(apparatus);
            metricsPublisher.addCount(MetricsConstants.DELETEAPPARATUS_APPARTATUSFOUND_COUNT, 1);
            return this.getApparatus(userName);
        } catch (UnsupportedOperationException e) {
            metricsPublisher.addCount(MetricsConstants.DELETEAPPARATUS_APPARTATUSFOUND_COUNT, 0);
            throw new ApparatusNotFoundException("Apparatus not found", e);
        }
    }

    /**
     * Returns the List of Apparatus.
     *
     * @param userName the userName associated with the logged in account.
     * @param apparatusTypeAndNumber the apparatus type and number associated with the
     * individual apparatus.
     * @param fireDept the fire department associated with the apparatus.
     * @return the list of Apparatus.
     */

    public List<Apparatus> addApparatus(String userName, String apparatusTypeAndNumber, String fireDept) {
        log.info("addApparatus method called in ApparatusDao with userName: " + userName +
                " , apparatusTypeAndNumber: " + apparatusTypeAndNumber + ", and fireDept: " + fireDept);

        Apparatus apparatus = new Apparatus(userName, apparatusTypeAndNumber, fireDept);

        try {
            dynamoDbMapper.save(apparatus);
            metricsPublisher.addCount(MetricsConstants.ADDAPPARATUS_COUNT, 1);
            return this.getApparatus(userName);
        } catch (UnsupportedOperationException e) {
            metricsPublisher.addCount(MetricsConstants.ADDAPPARATUS_COUNT, 0);
            throw new CannotAddApparatusException("Apparatus could not be saved", e);
        }
    }

    /**
     * Returns the List of Apparatus.
     * @param fireDept the fireDept associated with the individual apparatus.
     * @param apparatusTypeAndNumber the apparatus type and number associated with the
     * individual apparatus.
     * @return the individual Apparatus.
     */
    public List<Apparatus> getIndividualApparatus(String fireDept, String apparatusTypeAndNumber) {
        log.info("getIndividualApparatus method called in ApparatusDao with fireDept: " + fireDept + " and " +
            "apparatusTypeAndNumber: " + apparatusTypeAndNumber);

        try {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            valueMap.put(":fireDept", new AttributeValue().withS(fireDept));
            valueMap.put(":apparatusTypeAndNumber", new AttributeValue().withS(apparatusTypeAndNumber));
            DynamoDBQueryExpression<Apparatus> queryExpression = new DynamoDBQueryExpression<Apparatus>()
                    .withIndexName(FIRE_DEPT_APP_TYPE_NUM_INDEX)
                    .withConsistentRead(false)
                    .withKeyConditionExpression("fireDept = :fireDept and apparatusTypeAndNumber = " +
                        ":apparatusTypeAndNumber")
                    .withExpressionAttributeValues(valueMap);

            PaginatedQueryList<Apparatus> apparatusList = dynamoDbMapper.query(Apparatus.class, queryExpression);
            metricsPublisher.addCount(MetricsConstants.GETINDIVIDUALAPPARATUS_APPARAUTSNOTFOUND_COUNT, 1);
            log.info("ApparatusDAO: getIndividualApparatus method successful");
            return apparatusList;
        } catch (UnsupportedOperationException e) {
            metricsPublisher.addCount(MetricsConstants.GETINDIVIDUALAPPARATUS_APPARAUTSNOTFOUND_COUNT, 0);
            log.info("ApparatusDAO: getIndividualApparatus method unsuccessful");
            throw new IndividualApparatusNotFoundException("Individual Apparatus could not be found", e);
        }

    }

    /**
     * Returns the List of Apparatus.
     *
     * @param fireDept the fireDept associated with the apparatus.
     * @param apparatusTypeAndNumber the apparatus type and number associated with the
     * individual apparatus.
     * @param hoseIndexNumber the index of the hose to remove.
     * @return the List of Apparatus.
     */
    public List<Apparatus> deleteHose(String fireDept, String apparatusTypeAndNumber, int hoseIndexNumber) {
        log.info("ApparatusDAO: deleteHose method accessed");


        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":fireDept", new AttributeValue().withS(fireDept));
        valueMap.put(":apparatusTypeAndNumber", new AttributeValue().withS(apparatusTypeAndNumber));
        DynamoDBQueryExpression<Apparatus> queryExpression = new DynamoDBQueryExpression<Apparatus>()
                .withIndexName(FIRE_DEPT_APP_TYPE_NUM_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("fireDept = :fireDept and apparatusTypeAndNumber = :apparatusTypeAndNumber")
                .withExpressionAttributeValues(valueMap);
        log.info("ApparatusDAO: about to query table");

        PaginatedQueryList<Apparatus> apparatusListFromGSI = dynamoDbMapper.query(Apparatus.class, queryExpression);
        log.info("ApparatusDAO: apparatusListFromGSI size: " + apparatusListFromGSI.size());
        Apparatus apparatusFromGSI = apparatusListFromGSI.get(0);


        String userNameFromGSI = apparatusFromGSI.getUserName();
        String apparatusTypeAndNumberFromGSI = apparatusFromGSI.getApparatusTypeAndNumber();
        String fireDeptFromGSI = apparatusFromGSI.getFireDept();
        List<Hose> hoseListFromGSI = new ArrayList<>(apparatusFromGSI.getHoseList());
        
        hoseListFromGSI.remove(hoseIndexNumber);

        Apparatus apparatusWithHoseRemoved = new Apparatus(userNameFromGSI, apparatusTypeAndNumberFromGSI,
            fireDeptFromGSI, hoseListFromGSI);
        
        try {
            dynamoDbMapper.save(apparatusWithHoseRemoved);
            metricsPublisher.addCount(MetricsConstants.DELETEHOSE_COUNT, 1);
            log.info("ApparatusDAO: hose successfully deleted.");
        } catch (UnsupportedOperationException e) {
            metricsPublisher.addCount(MetricsConstants.DELETEHOSE_COUNT, 0);
            throw new CannotDeleteHoseException("Apparatus could not be saved", e);
        }
        
        List<Apparatus> list = new ArrayList<>();
        list.add(apparatusWithHoseRemoved);
        return list;
    }

    /**
     * Returns the List of Apparatus.
     *
     * @param fireDept the fireDept associated with the apparatus.
     * @param apparatusTypeAndNumber the apparatus type and number associated with the
     * individual apparatus.
     * @param name the name of the hose to add.
     * @param color the color of the hose to add.
     * @param length the length in feet of the hose to add.
     * @param diameter the diameter of the hose to add.
     * @param gallons the gallons per minute of the hose to add.
     * @return the List of Apparatus.
     */
    public List<Apparatus> addHose(String fireDept, String apparatusTypeAndNumber, String name, String color,
        int length, Double diameter, int gallons) {
        log.info("ApparatusDAO: addHose method accessed");

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":fireDept", new AttributeValue().withS(fireDept));
        valueMap.put(":apparatusTypeAndNumber", new AttributeValue().withS(apparatusTypeAndNumber));
        DynamoDBQueryExpression<Apparatus> queryExpression = new DynamoDBQueryExpression<Apparatus>()
                .withIndexName(FIRE_DEPT_APP_TYPE_NUM_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("fireDept = :fireDept and apparatusTypeAndNumber = :apparatusTypeAndNumber")
                .withExpressionAttributeValues(valueMap);
        log.info("ApparatusDAO: about to query table");

        PaginatedQueryList<Apparatus> apparatusListFromGSI = dynamoDbMapper.query(Apparatus.class, queryExpression);
        log.info("ApparatusDAO: apparatusListFromGSI size: " + apparatusListFromGSI.size());
        Apparatus apparatusFromGSI = apparatusListFromGSI.get(0);


        String userNameFromGSI = apparatusFromGSI.getUserName();
        String apparatusTypeAndNumberFromGSI = apparatusFromGSI.getApparatusTypeAndNumber();
        String fireDeptFromGSI = apparatusFromGSI.getFireDept();

        List<Hose> hoseListFromGSI = null;
        if (apparatusFromGSI.getHoseList() != null) {
            hoseListFromGSI = new ArrayList<>(apparatusFromGSI.getHoseList());
        } else {
            hoseListFromGSI = new ArrayList<>();
        }

        Hose hoseToAdd = new Hose(name, color, length, diameter, gallons);

        hoseListFromGSI.add(hoseToAdd);

        Apparatus apparatusWithHoseAdded = new Apparatus(userNameFromGSI, apparatusTypeAndNumberFromGSI,
            fireDeptFromGSI, hoseListFromGSI);

        try {
            dynamoDbMapper.save(apparatusWithHoseAdded);
            metricsPublisher.addCount(MetricsConstants.ADDHOSE_COUNT, 1);
            log.info("ApparatusDAO: hose successfully added.");
        } catch (UnsupportedOperationException e) {
            metricsPublisher.addCount(MetricsConstants.ADDHOSE_COUNT, 0);
            throw new CannotAddHoseException("Apparatus could not be saved", e);
        }

        List<Apparatus> list = new ArrayList<>();
        list.add(apparatusWithHoseAdded);
        return list;

    }

    /**
     * Returns the List of Apparatus.
     *
     * @param fireDept the fireDept associated with the apparatus.
     * @param apparatusTypeAndNumber the apparatus type and number associated with the
     * individual apparatus.
     * @param hoseIndexNumber the index number of the hose to calculate.
     * @return the List of Apparatus.
     */
    public List<Apparatus> calculatePSI(String fireDept, String apparatusTypeAndNumber, int hoseIndexNumber) {
        log.info("ApparatusDAO: calculatePSI method accessed");

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":fireDept", new AttributeValue().withS(fireDept));
        valueMap.put(":apparatusTypeAndNumber", new AttributeValue().withS(apparatusTypeAndNumber));
        DynamoDBQueryExpression<Apparatus> queryExpression = new DynamoDBQueryExpression<Apparatus>()
                .withIndexName(FIRE_DEPT_APP_TYPE_NUM_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("fireDept = :fireDept and apparatusTypeAndNumber = :apparatusTypeAndNumber")
                .withExpressionAttributeValues(valueMap);
        log.info("ApparatusDAO: calculatePSI: about to query table");

        PaginatedQueryList<Apparatus> apparatusListFromGSI = dynamoDbMapper.query(Apparatus.class, queryExpression);
        log.info("ApparatusDAO: apparatusListFromGSI size: " + apparatusListFromGSI.size());
        Apparatus apparatusFromGSI = apparatusListFromGSI.get(0);

        String userNameFromGSI = apparatusFromGSI.getUserName();
        String apparatusTypeAndNumberFromGSI = apparatusFromGSI.getApparatusTypeAndNumber();
        String fireDeptFromGSI = apparatusFromGSI.getFireDept();
        List<Hose> hoseListFromGSI = new ArrayList<>(apparatusFromGSI.getHoseList());
        Hose hoseThatNeedsCalc = hoseListFromGSI.get(hoseIndexNumber);
        String name = hoseThatNeedsCalc.getName();
        String color = hoseThatNeedsCalc.getColor();
        int length = hoseThatNeedsCalc.getLength();
        Double hoseDiameter = hoseThatNeedsCalc.getHoseDiameter();
        int gallons = hoseThatNeedsCalc.getWaterQuantityInGallons();

        Coefficient coefficientClass = new Coefficient();
        coefficientClass.setHoseDiameter(hoseDiameter);
        log.info("ApparatusDAO: prior to Mapper.load with hoseDiameter: " + hoseDiameter + " and Coefficient class: " +
            coefficientClass);
        Coefficient coefficientFromTable = dynamoDbMapper.load(coefficientClass);
        log.info("Coefficient from table: " + coefficientFromTable);
        Double doubleCoefficientFromTable = coefficientFromTable.getCoefficient();
        log.info("Double Coefficient from table: " + doubleCoefficientFromTable);


        FrictionLossCalculator calculator = new FrictionLossCalculator(doubleCoefficientFromTable, length, gallons);
        Integer calculatedPSI = calculator.calculateFrictionLoss();

        Hose newHose = new Hose();
        newHose.setName(name);
        newHose.setColor(color);
        newHose.setLength(length);
        newHose.setHoseDiameter(hoseDiameter);
        newHose.setWaterQuantityInGallons(gallons);
        newHose.setPumpDischargePressure(calculatedPSI);

        hoseListFromGSI.set(hoseIndexNumber, newHose);

        Apparatus apparatusWithHoseUpdated = new Apparatus(userNameFromGSI, apparatusTypeAndNumberFromGSI,
            fireDeptFromGSI, hoseListFromGSI);

        try {
            dynamoDbMapper.save(apparatusWithHoseUpdated);
            metricsPublisher.addCount(MetricsConstants.CALC_HOSE_COUNT, 1);
            log.info("ApparatusDAO: hose successfully added.");
        } catch (UnsupportedOperationException e) {
            metricsPublisher.addCount(MetricsConstants.CALC_HOSE_COUNT, 0);
            throw new CannotCalculatePSIException("Apparatus could not be saved", e);
        }

        List<Apparatus> list = new ArrayList<>();
        list.add(apparatusWithHoseUpdated);
        return list;

    }

    /**
     * Returns the List of Constants.
     * @return the List of Constants.
     */
    public List<Constant> getConstants() {
        log.info("ApparatusDao: getConstants() method called.");

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<Constant> constantsList = this.dynamoDbMapper.scan(Constant.class, scanExpression);

        if (constantsList == null) {
            log.info("ApparatusDao: getConstants method has returned a null constantsList");
            metricsPublisher.addCount(MetricsConstants.GETCONSTANTS_CONSTANTSNOTFOUND_COUNT, 1);
            throw new ConstantsNotFoundException("Could not find constants in table.");
        }
        metricsPublisher.addCount(MetricsConstants.GETCONSTANTS_CONSTANTSNOTFOUND_COUNT, 0);
        return constantsList;
    }
}
