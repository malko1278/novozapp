package ru.novozapp.fam.settings;

/**
 * Created by Franck Malko on 01/03/2021.
 */
public class AppSettings {
    /**
     * Enable or disable ADMOB
     */
    public static final boolean ENABLE_ADMOB = false;
    /**
     * Define latitude of your location
     */
    public static double LATITUDE = 52.534613;
    /**
     * Define longitude of your location
     */
    public static double LONGITUDE = 31.933387;
    /**
     * Initial zoom of the map
     */
    public static final int MAP_INITIAL_ZOOM = 14;
    /**
     * Radius in meters for location searches
     */
    public static final int GOOGLE_PLACES_LOCATION_RADIUS = 1000;
    /**
     * Radius in meters for string searches
     */
    public static final int GOOGLE_PLACES_SEARCH_RADIUS = 10000;
    /**
     * City for the app
     */
    public static final String TOWN = "Novozubkov";
    /**
     * Country of the app
     */
    public static final String COUNTRY = "Russian";
    public static final String OPEN_WEATHER_MAP_KEY = "fce9c9061edbe28652902e021b58b30a";
    public static final String XMLResourcePath = "about.xml";
}