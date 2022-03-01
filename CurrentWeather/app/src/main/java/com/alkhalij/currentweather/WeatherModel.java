package com.alkhalij.currentweather;

import org.json.JSONObject;

public class WeatherModel {

    private String city;
    private String country;
    private String description;
    private double temp;
    private double minTemp;
    private double maxTemp;

    public WeatherModel(JSONObject json) {
        city = json.optString("name");
        country = json.optJSONObject("sys").optString("country");
        description = json.optJSONArray("weather").optJSONObject(0).optString("description");
        temp = json.optJSONObject("main").optDouble("temp");
        minTemp = json.optJSONObject("main").optDouble("temp_min");
        maxTemp = json.optJSONObject("main").optDouble("temp_max");
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public double getTemp() {
        return temp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }
}
