package com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.AddApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.GetApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.AddApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Converters.ModelConverter;

import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;

/**
 * Implementation of the AddSongToPlaylistActivity for the MusicPlaylistService's AddSongToPlaylist API.
 *
 * This API allows the customer to add a song to their existing playlist.
 */

public class AddApparatusActivity {
    private final Logger log = LogManager.getLogger();
    private final ApparatusDao apparatusDao;

    /**
     * Instantiates a new AddSongToPlaylistActivity object.
     *
     * @param apparatusDao PlaylistDao to access the playlist table.
     */
    @Inject
    public AddApparatusActivity(ApparatusDao apparatusDao) {
        this.apparatusDao = apparatusDao;
    }

    /**
     * This method handles the incoming request by adding an additional song
     * to a playlist and persisting the updated playlist.
     * <p>
     * It then returns the updated song list of the playlist.
     * <p>
     * If the playlist does not exist, this should throw a PlaylistNotFoundException.
     * <p>
     * If the album track does not exist, this should throw an AlbumTrackNotFoundException.
     *
     * @param addApparatusRequest request object containing the playlist ID and an asin and track number
     *                                 to retrieve the song data
     * @return addSongToPlaylistResult result object containing the playlist's updated list of
     *                                 API defined {@link }s
     */
    public AddApparatusResult handleRequest(final AddApparatusRequest addApparatusRequest) {

        //What I want to happen: Take in all values from the request, and transfer each value separately to the dao. The dao will build an apparatus from the values,
        //and save it to the apparatus table, which will return the updated LIST of apparatus to be viewed in the User Interface.

        String userName = addApparatusRequest.getUserName();
        String apparatusTypeAndNumber = addApparatusRequest.getApparatusTypeAndNumber();
        String fireDept = addApparatusRequest.getFireDept();

        List<Apparatus> apparatusList = apparatusDao.addApparatus(userName, apparatusTypeAndNumber, fireDept);
        List<ApparatusModel> apparatusModelList = new ModelConverter().toApparatusModelList(apparatusList);
        return AddApparatusResult.builder()
                .withApparatusModelList(apparatusModelList)
                .build();

    }
}