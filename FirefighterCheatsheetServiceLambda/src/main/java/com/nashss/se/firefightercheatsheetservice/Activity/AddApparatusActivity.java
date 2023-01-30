package com.nashss.se.firefightercheatsheetservice.Activity;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.AddApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.AddApparatusResult;
import com.nashss.se.firefightercheatsheetservice.Converters.ModelConverter;

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
     *                                 API defined {@link SongModel}s
     */
    public AddApparatusResult handleRequest(final AddApparatusRequest addApparatusRequest) {
        log.info("Received AddSongToPlaylistRequest {} ", addApparatusRequest);

        String asin = addApparatusRequest.getAsin();
        // Allow NPE when unboxing Integer if track number is null (getTrackNumber returns Integer)
        int trackNumber = addApparatusRequest.getTrackNumber();

        Playlist playlist = apparatusDao.getPlaylist(addApparatusRequest.getId());

        if (!playlist.getCustomerId().equals(addApparatusRequest.getCustomerId())) {
            throw new SecurityException("You must own a playlist to add songs to it.");
        }

        AlbumTrack albumTrackToAdd = albumTrackDao.getAlbumTrack(asin, trackNumber);

        LinkedList<AlbumTrack> albumTracks = (LinkedList<AlbumTrack>) (playlist.getSongList());
        if (addApparatusRequest.isQueueNext()) {
            albumTracks.addFirst(albumTrackToAdd);
        } else {
            albumTracks.addLast(albumTrackToAdd);
        }

        playlist.setSongList(albumTracks);
        playlist.setSongCount(playlist.getSongList().size());
        playlist = apparatusDao.savePlaylist(playlist);

        List<SongModel> songModels = new ModelConverter().toSongModelList(playlist.getSongList());
        return AddApparatusResult.builder()
                .withSongList(songModels)
                .build();
    }
}