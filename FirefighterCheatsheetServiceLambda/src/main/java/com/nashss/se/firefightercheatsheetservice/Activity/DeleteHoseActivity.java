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
public class DeleteHoseActivity {
    private final Logger log = LogManager.getLogger();
    private final ApparatusDao apparatusDao;

    /**
     * Instantiates a new GetIndividualApparatusActivity object.
     *
     * @param apparatusDao ApparatusDao to access the apparatus table.
     */
    @Inject
    public DeleteHoseActivity(ApparatusDao apparatusDao) {
        this.apparatusDao = apparatusDao;
    }

    /**
     * This method handles the incoming request by retrieving an individual apparatus from the database and removes
     * the selected hose by index number.
     * <p>
     * <p>
     * If the apparatus does not exist, this should throw a HoseNotFoundException.
     *
     * @param DeleteHoseRequest request object containing the user's name, apparatusTypeAndNumber, and hose index number.
     * @return DeleteHoseResult result object containing the API defined
     */
    public DeleteHoseResult handleRequest(final DeleteHoseRequest deleteHoseRequest) {
        log.info("Received DeleteHoseRequest {}", deleteHoseRequest);

        String userName = deleteHoseRequest.getUserName();
        String fireDept = deleteHoseRequest.getFireDept();
        String apparatusTypeAndNumber = deleteHoseRequest.getApparatusTypeAndNumber();

        List<Apparatus> apparatusList = apparatusDao.getIndividualApparatus(fireDept, apparatusTypeAndNumber);
        ApparatusModel apparatusModel = new ModelConverter().toIndividualApparatusModel(apparatusList);
        return DeleteHoseResult.builder()
                .withApparatusModel(apparatusModel)
                .build();
    }
}
