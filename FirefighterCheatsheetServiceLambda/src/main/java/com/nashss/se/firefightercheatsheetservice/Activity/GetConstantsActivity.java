package com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.GetConstantsRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.GetConstantsResult;
import com.nashss.se.firefightercheatsheetservice.Converters.ModelConverter;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Constant;
import com.nashss.se.firefightercheatsheetservice.Models.ConstantModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.inject.Inject;

/**
 * Implementation of the GetConstantsActivity for the FirefighterCheatSheetService's GetApparatus API.
 *
 * This API allows the customer to get all the Constants in the constants table.
 */
public class GetConstantsActivity {
    private final Logger log = LogManager.getLogger();
    private final ApparatusDao apparatusDao;

    /**
     * Instantiates a new GetConstantsActivity object.
     *
     * @param apparatusDao ApparatusDao to access the apparatus table.
     */
    @Inject
    public GetConstantsActivity(ApparatusDao apparatusDao) {
        this.apparatusDao = apparatusDao;
    }

    /**
     * This method handles the incoming request by retrieving the constants from the database.
     * <p>
     * It then returns the list of constants.
     * <p>
     * If the constants do not exist, this should throw a ConstantsNotFoundException.
     *
     * @param getConstantsRequest request object containing essentially nothing.
     * @return getConstantsResult result object containing the API defined {@link ConstantModel}
     */
    public GetConstantsResult handleRequest(final GetConstantsRequest getConstantsRequest) {
        log.info("Received GetConstantRequest {}", getConstantsRequest);
        List<Constant> constantsList = apparatusDao.getConstants();
        List<ConstantModel> constantsModels = new ModelConverter().toConstantsModelList(constantsList);
        return GetConstantsResult.builder()
                .withConstantModelList(constantsModels)
                .build();
    }
}
