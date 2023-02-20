package test.java.com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.GetApparatusActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.GetApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.GetApparatusResult;
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

public class GetApparatusActivityTest {
    @Mock
    private ApparatusDao apparatusDao;

    private GetApparatusActivity getApparatusActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        getApparatusActivity = new GetApparatusActivity(apparatusDao);
    }

    @Test
    public void handleRequest_givenValidGetApparatusRequest_returnsGetApparatusResult() {
        // GIVEN a valid GetApparatusRequest
        String userName = "userName";
        GetApparatusRequest request = GetApparatusRequest.builder()
                .withUserName(userName)
                .build();

        String apparatusTypeAndNumber = "Engine 1";
        String fireDept = "MCFD";
        List<Hose> hostList = new ArrayList<>();

        ApparatusModel apparatusModel = ApparatusModel.builder()
                .withUserName(userName)
                .withApparatusTypeAndNumber(apparatusTypeAndNumber)
                .withFireDept(fireDept)
                .withHoseList(hostList)
                .build();

        List<ApparatusModel> apparatusModelList = new ArrayList<>();
        apparatusModelList.add(apparatusModel);

        GetApparatusResult result = GetApparatusResult.builder()
                .withApparatusModelList(apparatusModelList)
                .build();

        Apparatus apparatus = new Apparatus(userName, apparatusTypeAndNumber, fireDept, hostList);
        List<Apparatus> apparatusList = new ArrayList<>();
        apparatusList.add(apparatus);

        when(apparatusDao.getApparatus(userName)).thenReturn(apparatusList);

        //WHEN calling handleRequest with the valid request
        GetApparatusResult finalResult = getApparatusActivity.handleRequest(request);

        // THEN
        assertEquals(userName, finalResult.getApparatusModelList().get(0).getUserName());
        assertEquals(apparatusTypeAndNumber, finalResult.getApparatusModelList().get(0).getApparatusTypeAndNumber());
        assertEquals(fireDept, finalResult.getApparatusModelList().get(0).getFireDept());
    }
}
