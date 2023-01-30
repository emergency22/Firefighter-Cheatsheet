package com.nashss.se.firefightercheatsheetservice.Dependency;


import com.nashss.se.firefightercheatsheetservice.Activity.GetApparatusActivity;

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


}