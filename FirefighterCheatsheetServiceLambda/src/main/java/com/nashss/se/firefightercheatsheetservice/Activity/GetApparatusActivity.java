package com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.GetApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.GetApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;
import com.nashss.se.firefightercheatsheetservice.Converters.ModelConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the GetApparatusActivity for the FirefighterCheatSheetService's GetApparatus API.
 *
 * This API allows the customer to get one or more of their saved apparatus.
 */
public class GetApparatusActivity {
    private final Logger log = LogManager.getLogger();
    private final ApparatusDao apparatusDao;

    /**
     * Instantiates a new GetApparatusActivity object.
     *
     * @param apparatusDao ApparatusDao to access the apparatus table.
     */
    @Inject
    public GetApparatusActivity(ApparatusDao apparatusDao) {
        this.apparatusDao = apparatusDao;
    }

    /**
     * This method handles the incoming request by retrieving the apparatus from the database.
     * <p>
     * It then returns the list of apparatus.
     * <p>
     * If the apparatus does not exist, this should throw a ApparatusListNotFoundException.
     *
     * @param getApparatusRequest request object containing the user's name.
     * @return getApparatusResult result object containing the API defined {@link ApparatusModel}
     */
    public GetApparatusResult handleRequest(final GetApparatusRequest getApparatusRequest) {
        log.info("Received GetApparatusRequest {}", getApparatusRequest);
        String userName = getApparatusRequest.getUserName();
        List<Apparatus> apparatusList = apparatusDao.getApparatus(userName);     // <-- come back to this
        List<ApparatusModel> apparatusModelList = new ModelConverter().toApparatusModelList(apparatusList);
        return GetApparatusResult.builder()
                .withApparatusModelList(apparatusModelList)
                .build();
    }
}
