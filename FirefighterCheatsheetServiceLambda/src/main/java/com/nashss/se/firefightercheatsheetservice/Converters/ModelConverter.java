package com.nashss.se.firefightercheatsheetservice.Converters;



import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Hose;
import com.nashss.se.firefightercheatsheetservice.Models.ApparatusModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {
    /**
     * Converts a provided {@link Apparatus} into an {@link ApparatusModel} representation.
     *
     * @param apparatus the playlist to convert
     * @return the converted playlist
     */
    public ApparatusModel toApparatusModel(Apparatus apparatus) {
        List<Hose> hoseList = null;
        if (apparatus.getHoseList() != null) {
            hoseList = new ArrayList<>(apparatus.getHoseList());
        }

        return ApparatusModel.builder()
                .withId(apparatus.getUserName())
                .withName(apparatus.getApparatusTypeAndNumber())
                .withCustomerId(apparatus.getFireDept())
                .withTags(hoseList)
                .build();
    }

    /**
     * Converts a provided AlbumTrack into a SongModel representation.
     *
     * @param albumTrack the AlbumTrack to convert to SongModel
     * @return the converted SongModel with fields mapped from albumTrack
     */
//    public SongModel toSongModel(AlbumTrack albumTrack) {
//        return SongModel.builder()
//                .withAsin(albumTrack.getAsin())
//                .withTrackNumber(albumTrack.getTrackNumber())
//                .withAlbum(albumTrack.getAlbumName())
//                .withTitle(albumTrack.getSongTitle())
//                .build();
//    }

    /**
     * Converts a list of AlbumTracks to a list of SongModels.
     *
     * @param albumTracks The AlbumTracks to convert to SongModels
     * @return The converted list of SongModels
     */
//    public List<SongModel> toSongModelList(List<AlbumTrack> albumTracks) {
//        List<SongModel> songModels = new ArrayList<>();
//
//        for (AlbumTrack albumTrack : albumTracks) {
//            songModels.add(toSongModel(albumTrack));
//        }
//
//        return songModels;
//    }

    /**
     * Converts a list of Apparatus to a list of ApparatusModels.
     *
     * @param apparatusList The Apparatus to convert to ApparatusModels
     * @return The converted list of ApparatusModels
     */
    public List<ApparatusModel> toApparatusModelList(List<Apparatus> apparatusList) {
        List<ApparatusModel> apparatusModels = new ArrayList<>();

        for (Apparatus apparatus : apparatusList) {
            apparatusModels.add(toApparatusModel(apparatus));
        }

        return apparatusModels;
    }
}