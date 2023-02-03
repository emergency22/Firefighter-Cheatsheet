package com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.DeleteApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.DeleteApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Converters.ModelConverter;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class DeleteApparatusActivity {
    private final Logger log = LogManager.getLogger();
    private final ApparatusDao apparatusDao;

    /**
     * Instantiates a new DeleteApparatusActivity object.
     *
     * @param apparatusDao ApparatusDao to access the apparatus table.
     */
    @Inject
    public DeleteApparatusActivity(ApparatusDao apparatusDao) {
        this.apparatusDao = apparatusDao;
    }

    /**
     * This method handles the incoming request by deleting the apparatus from the database.
     * <p>
     * It then returns the list of remaining apparatus.
     * <p>
     * If the apparatus does not exist, this should throw a ApparatusNotFoundException.
     *
     * @param deleteApparatusRequest request object containing the user's name and apparatus type and number.
     * @return deleteApparatusResult result object containing the API defined {@link ApparatusModel}
     */
    public DeleteApparatusResult handleRequest(final DeleteApparatusRequest deleteApparatusRequest) {
        log.info("DeleteApparatusActivity: handleRequest method accessed.", deleteApparatusRequest);
        String userName = deleteApparatusRequest.getUserName();
        String apparatusTypeAndNumber= deleteApparatusRequest.getApparatusTypeAndNumber();
        List<Apparatus> apparatusList = apparatusDao.deleteApparatus(userName, apparatusTypeAndNumber);
        List<ApparatusModel> apparatusModelList = new ModelConverter().toApparatusModelList(apparatusList);
        return DeleteApparatusResult.builder()
                .withApparatusModelList(apparatusModelList)
                .build();
    }
}

