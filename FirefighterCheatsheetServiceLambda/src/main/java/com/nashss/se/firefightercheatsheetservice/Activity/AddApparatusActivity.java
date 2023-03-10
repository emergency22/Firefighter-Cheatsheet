package com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.AddApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.AddApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Converters.ModelConverter;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;

import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.inject.Inject;

/**
* Implementation of the AddApparatusActivity for the Firefighter Cheat Sheet Service's Add Apparatus API.
*
* This API allows the customer to add an Apparatus to their existing account.
*/

public class AddApparatusActivity {
    private final Logger log = LogManager.getLogger();
    private final ApparatusDao apparatusDao;

    /**
     * Instantiates a new AddApparatusActivity object.
     *
     * @param apparatusDao ApparatusDao to access the Apparatus table.
     */
    @Inject
    public AddApparatusActivity(ApparatusDao apparatusDao) {
        this.apparatusDao = apparatusDao;
    }

    /**
     * This method handles the incoming request by adding an additional apparatus to the apparatus table.
     * <p>
     * It then returns the updated list of Apparatus within an AddApparatusResult.
     * <p>
     * If the apparatus cannot be added, this should throw a CannotAddApparatusException.
     * <p>
     *
     * @param addApparatusRequest request object containing the apparatus data to be added
     * @return addApparatusResult result object containing the apparatus table's updated list of Apparatus
     */
    public AddApparatusResult handleRequest(final AddApparatusRequest addApparatusRequest) {

        String userName = addApparatusRequest.getUserName();
        String apparatusTypeAndNumber = addApparatusRequest.getApparatusTypeAndNumber();
        String fireDept = addApparatusRequest.getFireDept();

        List<Apparatus> apparatusList = apparatusDao.addApparatus(userName, apparatusTypeAndNumber, fireDept);
        List<ApparatusModel> apparatusModelList = new ModelConverter().toApparatusModelList(apparatusList);
        return AddApparatusResult.builder()
                .withApparatusModelList(apparatusModelList)
                .build();

    }
}
