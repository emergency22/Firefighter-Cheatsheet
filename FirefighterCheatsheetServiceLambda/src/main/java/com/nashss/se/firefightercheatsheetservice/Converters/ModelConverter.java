package com.nashss.se.firefightercheatsheetservice.Converters;

import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Constant;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Hose;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;
import com.nashss.se.firefightercheatsheetservice.Models.ConstantModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
/**
 * Converts between Data and API models.
 */
public class ModelConverter {
    private final Logger log = LogManager.getLogger();

    /**
     * Converts a provided {@link Apparatus} into an {@link ApparatusModel} representation.
     *
     * @param apparatus the apparatus to convert
     * @return the converted ApparatusModel
     */
    public ApparatusModel toApparatusModel(Apparatus apparatus) {
        log.info("ModelConverter: toApparatusModel method has run");
        List<Hose> hoseList = null;
        if (apparatus.getHoseList() != null) {
            hoseList = new ArrayList<>(apparatus.getHoseList());
        } else {
            hoseList = new ArrayList<>();
        }

        return ApparatusModel.builder()
                .withUserName(apparatus.getUserName())
                .withApparatusTypeAndNumber(apparatus.getApparatusTypeAndNumber())
                .withFireDept(apparatus.getFireDept())
                .withHoseList(hoseList)
                .build();
    }

    /**
     * Converts a list of Apparatus to a list of ApparatusModels.
     *
     * @param apparatusList The Apparatus to convert to ApparatusModels
     * @return The converted list of ApparatusModels
     */
    public List<ApparatusModel> toApparatusModelList(List<Apparatus> apparatusList) {
        log.info("ModelConverter: toApparatusModelList method has run");
        List<ApparatusModel> apparatusModels = new ArrayList<>();

        for (Apparatus apparatus : apparatusList) {
            apparatusModels.add(toApparatusModel(apparatus));
        }

        return apparatusModels;
    }

    /**
     * Converts a provided List of Apparatus into an ApparatusModel.
     *
     * @param apparatusList the apparatus to convert
     * @return the converted ApparatusModel
     */
    public ApparatusModel toIndividualApparatusModel(List<Apparatus> apparatusList) {
        log.info("ModelConverter: toIndividualApparatusModel method has run");
        List<ApparatusModel> apparatusModelList = toApparatusModelList(apparatusList);
        if (!apparatusModelList.isEmpty()) {
            return apparatusModelList.get(0);
        } else {
            return ApparatusModel.builder().build();
        }
    }

    public List<ConstantModel> toConstantsModelList(List<Constant> constantList) {
        log.info("ModelConverter: toConstantsModelList method has run");
        List<ConstantModel> constantModels = new ArrayList<>();

        for (Constant constant : constantList) {
            constantModels.add(toConstantModel(constant));
        }
        return constantModels;
    }

    public ConstantModel toConstantModel(Constant constant) {
        log.info("ModelConverter: toConstantModel method has run");

        return ConstantModel.builder()
                .withKey(constant.getKey())
                .withHumanValue(constant.getHumanValue())
                .withComputerValue(constant.getComputerValue())
                .build();
    }


}
