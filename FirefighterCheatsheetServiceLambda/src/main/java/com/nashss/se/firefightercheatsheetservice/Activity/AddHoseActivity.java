package com.nashss.se.firefightercheatsheetservice.Activity;


import com.nashss.se.firefightercheatsheetservice.Activity.Requests.AddHoseRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.AddHoseResult;
import com.nashss.se.firefightercheatsheetservice.Converters.ModelConverter;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the AddHoseActivity for the FirefighterCheatSheetService's AddHose API.
 *
 * This API allows the customer to save a hose to an individual apparatus.
 */
public class AddHoseActivity {
    private final Logger log = LogManager.getLogger();
    private final ApparatusDao apparatusDao;

    /**
     * Instantiates a new AddHoseActivity object.
     *
     * @param apparatusDao ApparatusDao to access the apparatus table.
     */
    @Inject
    public AddHoseActivity(ApparatusDao apparatusDao) {
        this.apparatusDao = apparatusDao;
    }

    /**
     * This method handles the incoming request by retrieving an individual apparatus from the database and adds
     * a hose with specific values.
     * <p>
     * <p>
     * If the apparatus does not exist, this should throw a CannotAddHoseException.
     *
     * @param addHoseRequest request object containing the user's name, apparatusTypeAndNumber, name, color,
     *  length, hose diameter, and water quantity in gallons per minute.
     * @return AddHoseResult result object containing the API defined
     */
    public AddHoseResult handleRequest(final AddHoseRequest addHoseRequest) {
        log.info("AddHoseActivity: handleRequest method: Received AddHoseRequest {}", addHoseRequest);

        String fireDept = addHoseRequest.getFireDept();
        String apparatusTypeAndNumber = addHoseRequest.getApparatusTypeAndNumber();
        String name = addHoseRequest.getName();
        String color = addHoseRequest.getColor();
        int length = addHoseRequest.getLength();
        Double diameter = addHoseRequest.getDiameter();
        int gallons = addHoseRequest.getGallons();

        List<Apparatus> apparatusList = apparatusDao.addHose(fireDept, apparatusTypeAndNumber, name, color, length, diameter, gallons);
        log.info("AddHoseActivity: handleRequest method: Before ModelConverter.");
        ApparatusModel apparatusModel = new ModelConverter().toIndividualApparatusModel(apparatusList);
        return AddHoseResult.builder()
                .withApparatusModel(apparatusModel)
                .build();
    }
}
