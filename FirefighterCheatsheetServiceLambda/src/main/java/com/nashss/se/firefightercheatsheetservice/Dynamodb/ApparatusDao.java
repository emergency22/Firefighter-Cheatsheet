package com.nashss.se.firefightercheatsheetservice.Dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Hose;
import com.nashss.se.firefightercheatsheetservice.Exceptions.*;
import com.nashss.se.firefightercheatsheetservice.Metrics.MetricsConstants;
import com.nashss.se.firefightercheatsheetservice.Metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Accesses data for a List of Apparatus using {@link Apparatus} to represent the model in DynamoDB.
 */
@Singleton
public class ApparatusDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    public static final String FIRE_DEPT_APP_TYPE_NUM_INDEX = "FireDeptAndAppTypeNumIndex";

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
     * Returns the {@link List<Apparatus>} corresponding to the specified userName.
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

//        Map<String, AttributeValue> valueMap = new HashMap<>();
//        valueMap.put(":userName", new AttributeValue().withS(userName));
//        DynamoDBQueryExpression<Apparatus> queryExpression = new DynamoDBQueryExpression<Apparatus>()
//                .withKeyConditionExpression("userName = :userName")
//                .withExpressionAttributeValues(valueMap);
//        PaginatedQueryList<Apparatus> apparatusList = this.dynamoDbMapper.query(Apparatus.class, queryExpression);


        if (apparatusList == null) {
            log.info("ApparatusDao: getApparatus method has returned a null apparatusList");
            metricsPublisher.addCount(MetricsConstants.GETAPPARATUS_APPARTATUSLISTNOTFOUND_COUNT, 1);
            throw new ApparatusListNotFoundException("Could not find apparatus for user name: " + userName);
        }
        metricsPublisher.addCount(MetricsConstants.GETAPPARATUS_APPARTATUSLISTNOTFOUND_COUNT, 0);
        return apparatusList;
    }

    /**
     * Returns the {@link List<Apparatus>} corresponding to the specified userName.
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
     * Returns the {@link List<Apparatus> corresponding to the specified userName.
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

//        Hose hose1 = new Hose("Preconnect 3", "Red", 200, 1.5, 200);
//        Hose hose2 = new Hose("Preconnect 4", "Red", 200, 1.75, 150);
//        List<Hose> hoseList = new ArrayList<>();
//        hoseList.add(hose1);
//        hoseList.add(hose2);
//
//        Apparatus apparatus = new Apparatus(userName, apparatusTypeAndNumber, fireDept, hoseList);

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
     * Returns the {@link Apparatus corresponding to the specified userName and
     * apparatusTypeAndNumber.
     *
     * @param userName the userName associated with the logged in account.
     * @param apparatusTypeAndNumber the apparatus type and number associated with the
     * individual apparatus.
     * @return the individual Apparatus.
     */
    public List<Apparatus> getIndividualApparatus(String fireDept, String apparatusTypeAndNumber) {
        log.info("getIndividualApparatus method called in ApparatusDao with fireDept: " + fireDept + " and apparatusTypeAndNumber: " + apparatusTypeAndNumber);

        try {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            valueMap.put(":fireDept", new AttributeValue().withS(fireDept));
            valueMap.put(":apparatusTypeAndNumber", new AttributeValue().withS(apparatusTypeAndNumber));
            DynamoDBQueryExpression<Apparatus> queryExpression = new DynamoDBQueryExpression<Apparatus>()
                    .withIndexName(FIRE_DEPT_APP_TYPE_NUM_INDEX)
                    .withConsistentRead(false)
                    .withKeyConditionExpression("fireDept = :fireDept and apparatusTypeAndNumber = :apparatusTypeAndNumber")
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
     * Returns the {@link Apparatus corresponding to the specified userName and
     * apparatusTypeAndNumber.
     *
     * @param userName the userName associated with the logged in account.
     * @param apparatusTypeAndNumber the apparatus type and number associated with the
     * individual apparatus.
     * @param hoseIndexNumber the index of the hose to remove.
     * @return the individual Apparatus.
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

        Apparatus apparatusWithHoseRemoved = new Apparatus(userNameFromGSI, apparatusTypeAndNumberFromGSI, fireDeptFromGSI, hoseListFromGSI);
        
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
     * Returns the {@link Apparatus corresponding to the specified userName and
     * apparatusTypeAndNumber.
     *
     * @param userName the userName associated with the logged in account.
     * @param apparatusTypeAndNumber the apparatus type and number associated with the
     * individual apparatus.
     * @param name the name of the hose to add.
     * @param color the color of the hose to add.
     * @param length the length in feet of the hose to add.
     * @param hoseDiameter the hose diameter of the hose to add.
     * @param waterQuantityInGallons the gallons per minute of the hose to add.
     * @return the individual Apparatus.
     */
    public List<Apparatus> addHose(String fireDept, String apparatusTypeAndNumber, String name, String color, int length, Double hoseDiameter, int waterQuantityInGallons) {
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
        List<Hose> hoseListFromGSI = new ArrayList<>(apparatusFromGSI.getHoseList());

        Hose hoseToAdd = new Hose(name, color, length, hoseDiameter, waterQuantityInGallons);

        hoseListFromGSI.add(hoseToAdd);

        Apparatus apparatusWithHoseAdded = new Apparatus(userNameFromGSI, apparatusTypeAndNumberFromGSI, fireDeptFromGSI, hoseListFromGSI);

        try {
            dynamoDbMapper.save(apparatusWithHoseAdded);
            metricsPublisher.addCount(MetricsConstants.ADDHOSE_COUNT, 1);
            log.info("ApparatusDAO: hose successfully deleted.");
        } catch (UnsupportedOperationException e) {
            metricsPublisher.addCount(MetricsConstants.ADDHOSE_COUNT, 0);
            throw new CannotAddHoseException("Apparatus could not be saved", e);
        }

        List<Apparatus> list = new ArrayList<>();
        list.add(apparatusWithHoseAdded);
        return list;

    }


    /**
     * Saves (creates or updates) the given playlist.
     *
     * @param playlist The playlist to save
     * @return The Playlist object that was saved
     */
//    public Playlist savePlaylist(Playlist playlist) {
//        this.dynamoDbMapper.save(playlist);
//        return playlist;
//    }

    /**
     * Perform a search (via a "scan") of the playlist table for playlists matching the given criteria.
     *
     * Both "playlistName" and "tags" attributes are searched.
     * The criteria are an array of Strings. Each element of the array is search individually.
     * ALL elements of the criteria array must appear in the playlistName or the tags (or both).
     * Searches are CASE SENSITIVE.
     *
     * @param criteria an array of String containing search criteria.
     * @return a List of Playlist objects that match the search criteria.
     */
//    public List<Playlist> searchPlaylists(String[] criteria) {
//        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
//
//        if (criteria.length > 0) {
//            Map<String, AttributeValue> valueMap = new HashMap<>();
//            String valueMapNamePrefix = ":c";
//
//            StringBuilder nameFilterExpression = new StringBuilder();
//            StringBuilder tagsFilterExpression = new StringBuilder();
//
//            for (int i = 0; i < criteria.length; i++) {
//                valueMap.put(valueMapNamePrefix + i,
//                        new AttributeValue().withS(criteria[i]));
//                nameFilterExpression.append(
//                        filterExpressionPart("playlistName", valueMapNamePrefix, i));
//                tagsFilterExpression.append(
//                        filterExpressionPart("tags", valueMapNamePrefix, i));
//            }
//
//            dynamoDBScanExpression.setExpressionAttributeValues(valueMap);
//            dynamoDBScanExpression.setFilterExpression(
//                    "(" + nameFilterExpression + ") or (" + tagsFilterExpression + ")");
//        }
//
//        return this.dynamoDbMapper.scan(Playlist.class, dynamoDBScanExpression);
//    }
//
//    private StringBuilder filterExpressionPart(String target, String valueMapNamePrefix, int position) {
//        String possiblyAnd = position == 0 ? "" : "and ";
//        return new StringBuilder()
//                .append(possiblyAnd)
//                .append("contains(")
//                .append(target)
//                .append(", ")
//                .append(valueMapNamePrefix).append(position)
//                .append(") ");
//    }
}