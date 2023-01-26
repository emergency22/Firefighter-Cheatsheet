package com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.GetApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.GetApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;
import com.nashss.se.firefightercheatsheetservice.Converters.ModelConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the GetPlaylistActivity for the MusicPlaylistService's GetPlaylist API.
 *
 * This API allows the customer to get one of their saved playlists.
 */
public class GetApparatusActivity {
    private final Logger log = LogManager.getLogger();
    private final ApparatusDao apparatusDao;

    /**
     * Instantiates a new GetPlaylistActivity object.
     *
     * @param playlistDao PlaylistDao to access the playlist table.
     */
    @Inject
    public GetApparatusActivity(ApparatusDao apparatusDao) {
        this.apparatusDao = apparatusDao;
    }

    /**
     * This method handles the incoming request by retrieving the playlist from the database.
     * <p>
     * It then returns the playlist.
     * <p>
     * If the playlist does not exist, this should throw a PlaylistNotFoundException.
     *
     * @param getPlaylistRequest request object containing the playlist ID
     * @return getPlaylistResult result object containing the API defined {@link PlaylistModel}
     */
    public GetApparatusResult handleRequest(final GetApparatusRequest getApparatusRequest) {
        log.info("Received GetApparatusRequest {}", getApparatusRequest);
        String userName = getApparatusRequest.getUserName();
        List<Apparatus> apparatusList = apparatusDao.getApparatus(userName);     // <-- come back to this
//        for (Apparatus apparatus : apparatusList) {
//            ApparatusModel apparatusModel = new ModelConverter().toApparatusModel(apparatus);
//        }

        List<ApparatusModel> apparatusModels = new ModelConverter().toApparatusModelList(apparatusList);

        return GetApparatusResult.builder()
                .withApparatus(apparatus)    <-- probably needs to be a list of apparatus?
                .build();
    }
}
