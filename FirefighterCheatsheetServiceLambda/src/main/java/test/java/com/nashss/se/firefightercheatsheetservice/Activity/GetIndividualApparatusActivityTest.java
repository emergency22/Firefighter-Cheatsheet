package test.java.com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.GetIndividualApparatusActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.GetIndividualApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.GetIndividualApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetIndividualApparatusActivityTest {
    @Mock
    private ApparatusDao apparatusDao;

    private GetIndividualApparatusActivity getIndividualApparatusActivity;

    /**
     * Initial set up for test methods.
     */
    @BeforeEach
    public void setUp() {
        openMocks(this);
        getIndividualApparatusActivity = new GetIndividualApparatusActivity(apparatusDao);
    }

    @Test
    public void handleRequest_givenValidGetIndividualApparatusRequest_returnsGetIndividualApparatusResult() {
        // GIVEN a valid GetIndividualApparatusRequest
        String fireDept = "fireDept";
        String apparatusTypeAndNumber = "apparatusTypeAndNumber";
        GetIndividualApparatusRequest request = GetIndividualApparatusRequest.builder()
                .withFireDept(fireDept)
                .withApparatusTypeAndNumber(apparatusTypeAndNumber)
                .build();

        Apparatus apparatus = new Apparatus();
        apparatus.setUserName("userName");
        apparatus.setApparatusTypeAndNumber(apparatusTypeAndNumber);
        apparatus.setFireDept(fireDept);
        List<Apparatus> apparatusList = new ArrayList<>();
        apparatusList.add(apparatus);

        when(apparatusDao.getIndividualApparatus(fireDept, apparatusTypeAndNumber)).thenReturn(apparatusList);

        //WHEN calling handleRequest with the valid request
        GetIndividualApparatusResult finalResult = getIndividualApparatusActivity.handleRequest(request);

        // THEN
        assertEquals("userName", finalResult.getApparatusModel().getUserName());
        assertEquals(apparatusTypeAndNumber, finalResult.getApparatusModel().getApparatusTypeAndNumber());
        assertEquals(fireDept, finalResult.getApparatusModel().getFireDept());
    }
}
