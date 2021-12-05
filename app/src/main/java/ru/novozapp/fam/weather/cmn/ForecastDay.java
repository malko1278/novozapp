package ru.novozapp.fam.weather.cmn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ru.novozapp.fam.utils.UtilDate;

/**
 * Created by dobrikostadinov on 6/16/15.
 */
public class ForecastDay {
    private String weatherDesc;
    private Date date;
    private String temperature;
    private String iconUrl;

    /**
     *
     * @param jSONObject
     */
    public ForecastDay(JSONObject jSONObject) {
        try {
            this.weatherDesc = jSONObject.optJSONArray("weather").getJSONObject(0).optString("main");
            // this.date = jSONObject.optString("dt_txt");
            this.temperature = jSONObject.optJSONObject("temp").getString("day");
            this.iconUrl = jSONObject.optJSONArray("weather").getJSONObject(0).optString("icon");
        } catch (JSONException e) {

        }
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public String getDate() {
        return UtilDate.FORECAST_DATE.format(date);
    }

    public String getTemperature() {
        double tempInDouble = Double.valueOf(temperature);
        return (int)tempInDouble + "\u2103";
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}