package test.java.com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.AddApparatusActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.AddHoseActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.AddApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.AddHoseRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.AddApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.AddHoseResult;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Hose;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;
import com.nashss.se.firefightercheatsheetservice.Utils.HOSE_DIAMETERS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AddHoseActivityTest {
    @Mock
    private ApparatusDao apparatusDao;

    private AddHoseActivity addHoseActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        addHoseActivity = new AddHoseActivity(apparatusDao);
    }

    @Test
    public void handleRequest_givenValidAddHoseRequest_returnsValidAddHoseResult() {
        // GIVEN a valid AddHoseRequest
        String userName = "userName";
        String apparatusTypeAndNumber = "apparatusTypeAndNumber";
        String fireDept = "fireDept";
        String name = "name";
        String color = "color";
        int length = 150;
        Double diameter = 1.75;
        int gallons = 200;
        AddHoseRequest request = AddHoseRequest.builder()
                .withApparatusTypeAndNumber(apparatusTypeAndNumber)
                .withFireDept(fireDept)
                .withName(name)
                .withColor(color)
                .withLength(length)
                .withDiameter(diameter)
                .withGallons(gallons)
                .build();

        List<Hose> hoseList = new ArrayList<>();

        Hose hose = Hose.builder()
                .withName(name)
                .withColor(color)
                .withLength(length)
                .withHoseDiameter(diameter)
                .withWaterQuantityInGallons(gallons)
                .withPumpDischargePressure(0)
                .build();

        hoseList.add(hose);

        ApparatusModel apparatusModel = ApparatusModel.builder()
                .withApparatusTypeAndNumber(apparatusTypeAndNumber)
                .withFireDept(fireDept)
                .withHoseList(hoseList)
                .build();

        AddHoseResult result = AddHoseResult.builder()
                .withApparatusModel(apparatusModel)
                .build();

        Apparatus apparatus = new Apparatus(userName, apparatusTypeAndNumber, fireDept, hoseList);
        List<Apparatus> apparatusList = new ArrayList<>();
        apparatusList.add(apparatus);

        when(apparatusDao.addHose(fireDept, apparatusTypeAndNumber, name, color, length, diameter, gallons)).thenReturn(apparatusList);

        //WHEN calling handleRequest with the valid request
        AddHoseResult finalResult = addHoseActivity.handleRequest(request);

        // THEN
        assertEquals(fireDept, finalResult.getApparatusModel().getFireDept());
        assertEquals(apparatusTypeAndNumber, finalResult.getApparatusModel().getApparatusTypeAndNumber());
        assertEquals(name, finalResult.getApparatusModel().getHoseList().get(0).getName());
    }
}