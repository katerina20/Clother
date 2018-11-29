package com.example.malut.clother;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class GeneralPage extends AppCompatActivity {

    private ImageButton secondPage;
    private TextView temperature;
    private ImageView iconMain;
    private TextView temperatureApp;
    private TextView precipText;
    private TextView cityNameView;
    String cityName;

    double lat = 46.4288699298;
    double lng = 30.7232187;
    String provider;
    private DarkSkyWeather darkSkyWeather = new DarkSkyWeather();

    int MY_PERMISSION = 0;

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

        cityName = RequestData.coordinatesToCity(lat, lng, this);

        cityNameView.setText(cityName);

        secondPage = findViewById(R.id.movePage);
        secondPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralPage.this, MainActivity.class);
                intent.putExtra("weather", darkSkyWeather);
                intent.putExtra("city", cityName);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
            }
        });

        new GetWeather().execute(RequestData.apiRequest(String.valueOf(lat), String.valueOf(lng)));

//        TextView temp = (TextView) findViewById(R.id.temp);
//        temp.setText(String.valueOf(darkSkyWeather.getCurrently().getTemperature()));


//        Log.d("weather", String.valueOf(darkSkyWeather.getCurrently().getTemperature()));

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

            precipText.setText(String.valueOf(darkSkyWeather.getCurrently().getPrecipProbability()) + "%");

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
}
