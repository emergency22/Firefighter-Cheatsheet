package test.java.com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.CalculatePSIActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.CalculatePSIRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.CalculatePSIResult;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Hose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class CalculatePSIActivityTest {
    @Mock
    private ApparatusDao apparatusDao;

    private CalculatePSIActivity calculatePSIActivity;

    /**
     * Initial set up for test methods.
     */
    @BeforeEach
    public void setUp() {
        openMocks(this);
        calculatePSIActivity= new CalculatePSIActivity(apparatusDao);
    }

    @Test
    public void handleRequest_givenValidCalculatePSIRequest_returnsValidCalculatePSIResult() {
        // GIVEN a valid CalculatePSIRequest
        CalculatePSIRequest request = CalculatePSIRequest.builder()
                .withUserName("userName")
                .withApparatusTypeAndNumber("apparatusTypeAndNumber")
                .withFireDept("fireDept")
                .withHoseIndexNumber(0)
                .build();

        List<Apparatus> apparatusList = new ArrayList<>();
        Apparatus apparatus = new Apparatus();
        List<Hose> hoseList = new ArrayList<>();
        Hose hose = new Hose();
        hose.setPumpDischargePressure(100);
        apparatus.setFireDept("fireDept");
        apparatus.setApparatusTypeAndNumber("apparatusTypeAndNumber");
        hoseList.add(hose);
        apparatus.setHoseList(hoseList);
        apparatusList.add(apparatus);

        when(apparatusDao.calculatePSI("fireDept", "apparatusTypeAndNumber", 0)).thenReturn(apparatusList);

        //WHEN calling handleRequest with the valid request
        CalculatePSIResult finalResult = calculatePSIActivity.handleRequest(request);

        // THEN
        assertEquals("fireDept", finalResult.getApparatusModel().getFireDept());
        assertEquals("apparatusTypeAndNumber", finalResult.getApparatusModel().getApparatusTypeAndNumber());
        assertEquals(100, finalResult.getApparatusModel().getHoseList().get(0).getPumpDischargePressure());
    }
}
