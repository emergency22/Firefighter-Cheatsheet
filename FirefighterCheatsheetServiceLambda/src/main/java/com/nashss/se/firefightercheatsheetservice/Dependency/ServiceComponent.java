package com.nashss.se.firefightercheatsheetservice.Dependency;

import com.nashss.se.firefightercheatsheetservice.Activity.AddApparatusActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.DeleteApparatusActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.GetApparatusActivity;

import com.nashss.se.firefightercheatsheetservice.Activity.GetIndividualApparatusActivity;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return AddSongToPlaylistActivity
     */
    GetApparatusActivity provideGetApparatusActivity();

    AddApparatusActivity provideAddApparatusActivity();

    DeleteApparatusActivity provideDeleteApparatusActivity();

    GetIndividualApparatusActivity provideGetIndividualApparatusActivity();

    DeleteHoseActivity provideDeleteHoseActivity();

}