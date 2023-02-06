package com.nashss.se.firefightercheatsheetservice.Activity;


import com.nashss.se.firefightercheatsheetservice.Activity.Requests.GetIndividualApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.GetIndividualApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Converters.ModelConverter;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the GetIndividualApparatusActivity for the FirefighterCheatSheetService's GetIndividualApparatus API.
 *
 * This API allows the customer to get one their saved apparatus.
 */
public class GetIndividualApparatusActivity {
    private final Logger log = LogManager.getLogger();
    private final ApparatusDao apparatusDao;

    /**
     * Instantiates a new GetIndividualApparatusActivity object.
     *
     * @param apparatusDao ApparatusDao to access the apparatus table.
     */
    @Inject
    public GetIndividualApparatusActivity(ApparatusDao apparatusDao) {
        this.apparatusDao = apparatusDao;
    }

    /**
     * This method handles the incoming request by retrieving an individual apparatus from the database.
     * <p>
     * It then returns the apparatus.
     * <p>
     * If the apparatus does not exist, this should throw a IndividualApparatusNotFoundException.
     *
     * @param getIndividualApparatusRequest request object containing the user's name and apparatusTypeAndNumber.
     * @return getIndividualApparatusResult result object containing the API defined {@link ApparatusModel}
     */
    public GetIndividualApparatusResult handleRequest(final GetIndividualApparatusRequest getIndividualApparatusRequest) {
        log.info("Received GetIndividualApparatusRequest {}", getIndividualApparatusRequest);

        String userName = getIndividualApparatusRequest.getUserName();
        String apparatusTypeAndNumber = getIndividualApparatusRequest.getApparatusTypeAndNumber();

        Apparatus apparatus = apparatusDao.getIndividualApparatus(userName, apparatusTypeAndNumber);
        ApparatusModel apparatusModel = new ModelConverter().toApparatusModel(apparatus);   //fix this
        return GetIndividualApparatusResult.builder()
                .withApparatusModel(apparatusModel)
                .build();
    }
}
