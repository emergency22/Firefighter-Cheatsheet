package test.java.com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.GetApparatusActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.GetApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.GetApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Hose;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;
import com.nashss.se.musicplaylistservice.activity.requests.GetPlaylistRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetPlaylistResult;
import com.nashss.se.musicplaylistservice.dynamodb.PlaylistDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Playlist;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        getApparatusActivity = new getApparatusActivity(apparatusDao);
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





        GetPlaylistRequest request = GetPlaylistRequest.builder()
                .withId(expectedId)
                .build();

        // WHEN
        GetPlaylistResult result = getPlaylistActivity.handleRequest(request);

        // THEN
        assertEquals(expectedId, result.getPlaylist().getId());
        assertEquals(expectedName, result.getPlaylist().getName());
        assertEquals(expectedCustomerId, result.getPlaylist().getCustomerId());
        assertEquals(expectedSongCount, result.getPlaylist().getSongCount());
        assertEquals(expectedTags, result.getPlaylist().getTags());
    }
}