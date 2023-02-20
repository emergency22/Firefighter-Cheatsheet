package com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.DeleteHoseActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.DeleteHoseRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.DeleteHoseResult;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class DeleteHoseActivityTest {
    @Mock
    private ApparatusDao apparatusDao;

    private DeleteHoseActivity deleteHoseActivity;

    /**
     * Initial set up for test methods.
     */
    @BeforeEach
    public void setUp() {
        openMocks(this);
        deleteHoseActivity = new DeleteHoseActivity(apparatusDao);
    }

    @Test
    public void handleRequest_givenValidDeleteHoseRequest_returnsValidDeleteHoseResult() {
        // GIVEN a valid DeleteHoseequest
        String fireDept = "fireDept";
        String apparatusTypeAndNumber = "apparatusTypeAndNumber";
        int hoseIndexNumber = 0;

        DeleteHoseRequest request = DeleteHoseRequest.builder()
                .withFireDept(fireDept)
                .withApparatusTypeAndNumber(apparatusTypeAndNumber)
                .withHoseIndexNumber(hoseIndexNumber)
                .build();

        List<Apparatus> apparatusList = new ArrayList<>();
        Apparatus apparatus = new Apparatus();
        apparatus.setFireDept(fireDept);
        apparatus.setHoseList(new ArrayList<>());
        apparatusList.add(apparatus);

        when(apparatusDao.deleteHose(fireDept, apparatusTypeAndNumber, hoseIndexNumber)).thenReturn(apparatusList);

        //WHEN calling handleRequest with the valid request
        DeleteHoseResult finalResult = deleteHoseActivity.handleRequest(request);

        // THEN the resulting list should be empty and fireDept should match
        assertTrue(finalResult.getApparatusModel().getHoseList().isEmpty());
        assertEquals(fireDept, finalResult.getApparatusModel().getFireDept());
    }
}
