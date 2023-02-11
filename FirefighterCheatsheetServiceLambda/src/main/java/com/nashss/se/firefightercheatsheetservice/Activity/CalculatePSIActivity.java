package com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.CalculatePSIRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.CalculatePSIResult;
import com.nashss.se.firefightercheatsheetservice.Converters.ModelConverter;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the CalculatePSIActivity for the FirefighterCheatSheetService's CalculatePSI API.
 *
 * This API allows the customer to calculate a PSI for a particular hose on an Apparatus.
 */
public class CalculatePSIActivity {
    private final Logger log = LogManager.getLogger();
    private final ApparatusDao apparatusDao;

    /**
     * Instantiates a new CalculatePSIActivity object.
     *
     * @param apparatusDao ApparatusDao to access the apparatus table.
     */
    @Inject
    public CalculatePSIActivity(ApparatusDao apparatusDao) {
        this.apparatusDao = apparatusDao;
    }

    /**
     * This method handles the incoming request by retrieving an individual apparatus from the database and calculates
     * a hose's respective PSI, based on the hose's values;
     * <p>
     * <p>
     * If the apparatus does not exist, this should throw a CannotCalculatePSIException.
     *
     * @param calculatePSIRequest request object containing the user's name, apparatusTypeAndNumber, name, color,
     *  length, hose diameter, and water quantity in gallons per minute.
     * @return AddHoseResult result object containing the API defined
     */
    public CalculatePSIResult handleRequest(final CalculatePSIRequest calculatePSIRequest) {
        log.info("CalculatePSIeActivity: handleRequest method: Received CalculatePSIRequest: ", calculatePSIRequest);

        String fireDept = calculatePSIRequest.getFireDept();
        String apparatusTypeAndNumber = calculatePSIRequest.getApparatusTypeAndNumber();
        int hoseIndexNumber = calculatePSIRequest.getHoseIndexNumber();

        List<Apparatus> apparatusList = apparatusDao.calculatePSI(fireDept, apparatusTypeAndNumber, hoseIndexNumber);
        log.info("CalculatePSIActivity: handleRequest method: Before ModelConverter.");
        ApparatusModel apparatusModel = new ModelConverter().toIndividualApparatusModel(apparatusList);
        return CalculatePSIResult.builder()
                .withApparatusModel(apparatusModel)
                .build();
    }
}
