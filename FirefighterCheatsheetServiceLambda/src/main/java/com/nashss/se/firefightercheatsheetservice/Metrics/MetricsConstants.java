package com.nashss.se.firefightercheatsheetservice.Metrics;

/**
 * Constant values for use with metrics.
 */
public class MetricsConstants {
    public static final String GETAPPARATUS_APPARTATUSLISTNOTFOUND_COUNT = "GetApparatus.ApparatusListNotFoundException.Count";
    public static final String DELETEAPPARATUS_APPARTATUSFOUND_COUNT = "DeleteApparatus.ApparatusNotFoundException.Count";
    public static final String ADDAPPARATUS_COUNT = "AddApparatusException.Count";
    public static final String DELETEHOSE_COUNT = "CannotDeleteHoseException.Count";
    public static final String ADDHOSE_COUNT = "CannotAddHoseException.Count";

    public static final String CALC_HOSE_COUNT = "CannotCalculatePSIException.Count";
    public static final String GETINDIVIDUALAPPARATUS_APPARAUTSNOTFOUND_COUNT = "GetIndividualApparatus.ApparatusListNotFoundException.Count";





    public static final String SERVICE = "Service";
    public static final String SERVICE_NAME = "FirefighterCheatsheetService";
    public static final String NAMESPACE_NAME = "U3/FirefighterCheatsheetService";
}