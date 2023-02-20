package com.nashss.se.firefightercheatsheetservice.Dependency;

import com.nashss.se.firefightercheatsheetservice.Activity.AddApparatusActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.AddHoseActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.DeleteApparatusActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.DeleteHoseActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.GetApparatusActivity;
import com.nashss.se.firefightercheatsheetservice.Activity.GetConstantsActivity;
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
     * @return GetApparatusActivity
     */
    GetApparatusActivity provideGetApparatusActivity();

    /**
     * Provides the relevant activity.
     * @return AddApparatusActivity
     */
    AddApparatusActivity provideAddApparatusActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteApparatusActivity
     */
    DeleteApparatusActivity provideDeleteApparatusActivity();

    /**
     * Provides the relevant activity.
     * @return GetIndividualApparatusActivity
     */
    GetIndividualApparatusActivity provideGetIndividualApparatusActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteHoseActivity
     */
    DeleteHoseActivity provideDeleteHoseActivity();

    /**
     * Provides the relevant activity.
     * @return AddHoseActivity
     */
    AddHoseActivity provideAddHoseActivity();

    /**
     * Provides the relevant activity.
     * @return CalculatePSIActivity
     */
    GetConstantsActivity provideGetConstantsActivity();

}
