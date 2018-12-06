package com.example.malut.clother;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.malut.clother.FontSet.TypefaceUtil;
import com.example.malut.clother.Model.DarkSkyWeather;
import com.example.malut.clother.Services.NetworkService;
import com.example.malut.clother.Services.RequestData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GeneralPage extends AppCompatActivity implements LocationListener {

    private LinearLayout ll;
    private ImageButton secondPage;
    private TextView temperature;
    private ImageView iconMain;
    private TextView temperatureApp;
    private TextView precipText;
    private TextView cityNameView;
    String cityName;
    SharedPreferences sPref;
    private ImageView iconMan;
    final String COORDS = "coords";

    LocationManager locationManager;
    String provider;
    static double lat, lng;
    int MY_PERMISSION = 0;

    //    double lat = 46.4288699298;
//    double lng = 30.7232187;
    private DarkSkyWeather darkSkyWeather = new DarkSkyWeather();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TypefaceUtil.overrideFont(getApplicationContext(), "SANS_SERIF", "fonts/anaheim.ttf");
        setContentView(R.layout.activity_general_page);
        //Control
        temperature = (TextView) findViewById(R.id.temp);
        iconMain = (ImageView) findViewById(R.id.icon_main);
        temperatureApp = (TextView) findViewById(R.id.app_temp_gen);
        precipText = (TextView) findViewById(R.id.precip_gen);
        cityNameView = (TextView) findViewById(R.id.city_name_gen);
        ll = (LinearLayout) findViewById(R.id.Layout);
        iconMan = (ImageView) findViewById(R.id.man_icon_gen);

//        cityName = RequestData.coordinatesToCity(lat, lng, this);

//        cityNameView.setText(cityName);

        secondPage = findViewById(R.id.movePage);
        secondPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralPage.this, MainActivity.class);
                intent.putExtra("city", cityName);
                intent.putExtra("weather", darkSkyWeather);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.bottom_in, R.anim.static_anim);
            }
        });

        //Get coordinates
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(GeneralPage.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null)
            Log.e("TAG", "No location");

        List<Double> coorList = loadText();

//        new GetWeather().execute(RequestData.apiRequest(String.valueOf(13.7539800), String.valueOf(100.5014400)));

        new GetWeather().execute(RequestData.apiRequest(String.valueOf(coorList.get(0)), String.valueOf(coorList.get(1))));

//        TextView temp = (TextView) findViewById(R.id.temp);
//        temp.setText(String.valueOf(darkSkyWeather.getCurrently().getTemperature()));


//        Log.d("weather", String.valueOf(darkSkyWeather.getCurrently().getTemperature()));

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GeneralPage.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);


    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        locationManager.removeUpdates(this);
//    }

    @Override
    public void onLocationChanged(Location location) {

        locationManager.removeUpdates(this);

        lat = location.getLatitude();
        lng = location.getLongitude();

        saveText(lat, lng);

        new GetWeather().execute(RequestData.apiRequest(String.valueOf(lat), String.valueOf(lng)));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class GetWeather extends AsyncTask<String, Void, String> {

        ProgressDialog pd = new ProgressDialog(GeneralPage.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Gson gson = new Gson();
            Type mType = new TypeToken<DarkSkyWeather>(){}.getType();
            darkSkyWeather = gson.fromJson(s, mType);
            pd.dismiss();
            temperature.setText(String.valueOf(darkSkyWeather.getCurrently().getTemperature()) + "°" );
            setMainIcon(darkSkyWeather.getCurrently().getIcon());
            temperatureApp.setText(String.valueOf(darkSkyWeather.getCurrently().getApparentTemperature() + "°"));
            int pic = getBackgroundPic(darkSkyWeather.getCurrently().getIcon());
            ll.setBackgroundResource(pic);
            precipText.setText(String.valueOf(darkSkyWeather.getCurrently().getPrecipProbability()) + "%");
            setManIcon(darkSkyWeather.getCurrently().getTemperature());
            cityName = RequestData.coordinatesToCity(darkSkyWeather.getLatitude(), darkSkyWeather.getLongitude(), GeneralPage.this);
            cityNameView.setText(cityName);

        }

        @Override
        protected String doInBackground(String... strings) {

            String json_line = null;
            String urlString = strings[0];

            NetworkService http = new NetworkService();
            json_line = http.getHTTPData(urlString);


//            getWeather = new NetworkService();
//            String s = getWeather.getHTTPData(RequestData.apiRequest(lat, lng));

            return json_line;

        }
    }

    private void setMainIcon (String s){

        String icon = s.replace('-', '_');
        int resId = getResources().getIdentifier(icon , "drawable", getPackageName());
        iconMain.setImageResource(resId);
    }

    private int getBackgroundPic (String s){

        String back_image = "";
        if (s == "clear-day"){
            back_image = "clear_day_back";
        }
        else if (s.equals("clear-night")){
            back_image = "clear_night_back";
        }
        else if (s.equals("cloudy")){
            back_image = "cloudy_night_back";
        }
        else if (s.equals("fog")){
            back_image = "fog_back";
        }
        else if (s.equals("partly-cloudy-day")){
            back_image = "cloudy_day_back";
        }
        else if (s.equals("partly-cloudy-night")){
            back_image = "cloudy_night_back";
        }
        else if (s.equals("rain")){
            back_image = "rain_back";
        }
        else if (s.equals("sleet")){
            back_image = "rain_back";
        }
        else if (s.equals("snow")){
            back_image = "snow_back";
        }
        else if (s.equals("wind")){
            back_image = "cloudy_day_back";
        }
        else if (s.equals("hail")){
            back_image = "rain_back";
        }
        else if (s.equals("thunderstorm")){
            back_image = "rain_back";
        }
        else if (s.equals("tornado")){
            back_image = "tornado";
        }

        int resId = getResources().getIdentifier(back_image, "drawable", getPackageName());;

        return resId;
    }

    private void setManIcon (int t){

        String icon = "";

        if (t >= 25){
            icon = "first_type";
        }
        else if (t >= 20){
            icon = "second_type";
        }
        else if (t >= 15){
            icon = "third_type";
        }
        else if (t >= 5){
            icon = "forth_type";
        }
        else if (t >= -50){
            icon = "fifth_type";
        }

        int resId = getResources().getIdentifier(icon, "drawable", getPackageName());
        iconMan.setImageResource(resId);
    }

    private void saveText(double lt, double ln) {
        String c = lt + "," + ln;
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(COORDS, c);
        ed.apply();
    }

    private List<Double> loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(COORDS, "");
        String[] coordStr = savedText.split(",");
        List<Double> coordList = new ArrayList<>();
        coordList.add(Double.parseDouble(coordStr[0]));
        coordList.add(Double.parseDouble(coordStr[1]));
        return coordList;
    }
}
