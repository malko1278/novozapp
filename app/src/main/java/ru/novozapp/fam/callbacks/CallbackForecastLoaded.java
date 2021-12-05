package ru.novozapp.fam.callbacks;

import java.util.List;

import ru.novozapp.fam.weather.cmn.ForecastDay;

/**
 * Created by dobrikostadinov on 6/17/15.
 */
public interface CallbackForecastLoaded {
    void doneLoadingForecast(List<ForecastDay> result);
    void showLoadingWeather();
}