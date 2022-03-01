package com.alkhalij.currentweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private TextView tvCity;
    private TextView tvTemp;
    private TextView tvDescription;
    private TextView tvMinTemp;
    private TextView tvMaxTemp;

    LocationManager locMgr;

    private static String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static String APP_ID = "a4be0f1a234d4bad6c50ccd3474de621";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCity = findViewById(R.id.tv_city);
        tvDescription = findViewById(R.id.tv_description);
        tvTemp = findViewById(R.id.tv_temp);
        tvMaxTemp = findViewById(R.id.tv_max_temp);
        tvMinTemp = findViewById(R.id.tv_min_temp);


        getCurrentLocation();
    }

    private void getCurrentLocation() {
        locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locLstr = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                System.out.println(lat);
                System.out.println(lon);
                RequestParams params = new RequestParams();
                params.put("lat", lat);
                params.put("lon", lon);
                params.put("units", "metric");
                params.put("appid", APP_ID);

                getWeatherData(params);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);

            return;
        }
        locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1000, locLstr);
    }

    private void getWeatherData(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                WeatherModel model = new WeatherModel(response);
                tvCity.setText(model.getCity() + "\n" + model.getCountry());
                tvDescription.setText(model.getDescription());
                tvTemp.setText(model.getTemp() + "°C");
                tvMaxTemp.setText(model.getMaxTemp() + "°C");
                tvMinTemp.setText(model.getMinTemp() + "°C");
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getCurrentLocation();
    }
}