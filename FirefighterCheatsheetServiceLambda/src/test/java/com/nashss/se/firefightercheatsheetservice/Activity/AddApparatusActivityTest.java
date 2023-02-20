package com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.AddApparatusActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.AddApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.AddApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Hose;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AddApparatusActivityTest {
    @Mock
    private ApparatusDao apparatusDao;
    private AddApparatusActivity addApparatusActivity;

    /**
     * Initial set up for test methods.
     */
    @BeforeEach
    public void setUp() {
        openMocks(this);
        addApparatusActivity = new AddApparatusActivity(apparatusDao);
    }

    @Test
    public void handleRequest_givenValidAddApparatusRequest_returnsValidAddApparatusResult() {
        // GIVEN a valid AddApparatusRequest
        String userName = "userName";
        String apparatusTypeAndNumber = "apparatusTypeAndNumber";
        String fireDept = "fireDept";
        AddApparatusRequest request = AddApparatusRequest.builder()
                .withUserName(userName)
                .withApparatusTypeAndNumber(apparatusTypeAndNumber)
                .withFireDept(fireDept)
                .build();

        List<Hose> hoseList = new ArrayList<>();

        ApparatusModel apparatusModel = ApparatusModel.builder()
                .withUserName(userName)
                .withApparatusTypeAndNumber(apparatusTypeAndNumber)
                .withFireDept(fireDept)
                .withHoseList(hoseList)
                .build();

        List<ApparatusModel> apparatusModelList = new ArrayList<>();
        apparatusModelList.add(apparatusModel);

        AddApparatusResult result = AddApparatusResult.builder()
                .withApparatusModelList(apparatusModelList)
                .build();

        Apparatus apparatus = new Apparatus(userName, apparatusTypeAndNumber, fireDept, hoseList);
        List<Apparatus> apparatusList = new ArrayList<>();
        apparatusList.add(apparatus);

        when(apparatusDao.addApparatus(userName, apparatusTypeAndNumber, fireDept)).thenReturn(apparatusList);

        //WHEN calling handleRequest with the valid request
        AddApparatusResult finalResult = addApparatusActivity.handleRequest(request);

        // THEN
        assertEquals(userName, finalResult.getApparatusModelList().get(0).getUserName());
        assertEquals(apparatusTypeAndNumber, finalResult.getApparatusModelList().get(0).getApparatusTypeAndNumber());
        assertEquals(fireDept, finalResult.getApparatusModelList().get(0).getFireDept());
    }
}
