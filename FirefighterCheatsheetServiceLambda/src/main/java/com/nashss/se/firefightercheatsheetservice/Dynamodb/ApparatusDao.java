package com.nashss.se.firefightercheatsheetservice.Dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Exceptions.ApparatusListNotFoundException;
import com.nashss.se.firefightercheatsheetservice.Metrics.MetricsConstants;
import com.nashss.se.firefightercheatsheetservice.Metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.List;

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
     * @return the List of Apparatus, or null if none was found.
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