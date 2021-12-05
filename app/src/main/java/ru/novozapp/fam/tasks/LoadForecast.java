package ru.novozapp.fam.tasks;

import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

import ru.novozapp.fam.activities.MainMapActivity;
import ru.novozapp.fam.callbacks.CallbackForecastLoaded;
import ru.novozapp.fam.weather.WeatherManager;
import ru.novozapp.fam.weather.cmn.ForecastDay;

/**
 * Created by dobrikostadinov on 6/17/15.
 */
public class LoadForecast extends AsyncTask<Void, Void, Void> {

    public static final long ONE_DAY_IN_MILISEC = 1000 * 60 * 60 * 24;
    // private MainMapActivity mMainActivity;
    private CallbackForecastLoaded mCallbackForecastLoaded;
    private List<ForecastDay> mResult;

    public LoadForecast(CallbackForecastLoaded callbackForecastLoaded) {
        // mMainActivity = mainActivity;
        mCallbackForecastLoaded = callbackForecastLoaded;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mCallbackForecastLoaded.showLoadingWeather();
        // mMainActivity.showLoadingDialog();
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (WeatherManager.getInstance().getResult() != null)   mResult = WeatherManager.getInstance().getResult();
        else    mResult = WeatherManager.getInstance().getForecast();
        for (int i = 0; i < mResult.size(); i++)   mResult.get(i).setDate(new Date(new Date().getTime() + i * ONE_DAY_IN_MILISEC));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mCallbackForecastLoaded.doneLoadingForecast(mResult);
    }
}