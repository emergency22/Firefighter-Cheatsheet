package test.java.com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.DeleteApparatusActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.DeleteApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.DeleteApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class DeleteApparatusActivityTest {
    @Mock
    private ApparatusDao apparatusDao;

    private DeleteApparatusActivity deleteApparatusActivity;

    /**
     * Initial set up for test methods.
     */
    @BeforeEach
    public void setUp() {
        openMocks(this);
        deleteApparatusActivity = new DeleteApparatusActivity(apparatusDao);
    }

    @Test
    public void handleRequest_givenValidDeleteApparatusRequest_returnsValidDeleteApparatusResult() throws UnsupportedEncodingException {
        // GIVEN a valid DeleteApparatusRequest
        String userName = "userName";
        String apparatusTypeAndNumber = "apparatusTypeAndNumber";
        DeleteApparatusRequest request = DeleteApparatusRequest.builder()
                .withUserName(userName)
                .withApparatusTypeAndNumber(apparatusTypeAndNumber)
                .build();

        List<Apparatus> apparatusList = new ArrayList<>();

        when(apparatusDao.deleteApparatus(userName, apparatusTypeAndNumber)).thenReturn(apparatusList);

        //WHEN calling handleRequest with the valid request
        DeleteApparatusResult finalResult = deleteApparatusActivity.handleRequest(request);

        // THEN the resulting list should be empty
        assertTrue(finalResult.getApparatusModelList().isEmpty());
    }
}
