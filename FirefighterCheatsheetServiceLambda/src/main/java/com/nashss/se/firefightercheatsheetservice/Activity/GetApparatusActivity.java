package com.nashss.se.firefightercheatsheetservice.Activity;


import com.nashss.se.firefightercheatsheetservice.Activity.Requests.GetApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.GetApparatusResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.PlaylistDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Playlist;
import com.nashss.se.musicplaylistservice.models.PlaylistModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetPlaylistActivity for the MusicPlaylistService's GetPlaylist API.
 *
 * This API allows the customer to get one of their saved playlists.
 */
public class GetApparatusActivity {
    private final Logger log = LogManager.getLogger();
    private final GetApparatusDao getApparatusDao;

    /**
     * Instantiates a new GetPlaylistActivity object.
     *
     * @param playlistDao PlaylistDao to access the playlist table.
     */
    @Inject
    public GetApparatusActivity(GetApparatusDao getApparatusDao) {
        this.getApparatusDao = getApparatusDao;
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
    public GetApparatusResult handleRequest(final GetPlaylistRequest getPlaylistRequest) {
        log.info("Received GetPlaylistRequest {}", getPlaylistRequest);
        String requestedId = getPlaylistRequest.getId();
        Playlist playlist = playlistDao.getPlaylist(requestedId);
        PlaylistModel playlistModel = new ModelConverter().toPlaylistModel(playlist);

        return GetApparatusResult.builder()
                .withApparatus(apparatus)
                .build();
    }
}
