package com.example.malut.clother;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.example.malut.clother.Model.Daily.DataForWeek;
import com.example.malut.clother.Model.DarkSkyWeather;
import com.example.malut.clother.Model.Hourly.DataForDay;
import com.example.malut.clother.Services.RequestData;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static int DATAHOURLY = 24;
    public final static int DATADAYLY = 7;
    public DarkSkyWeather darkSkyWeather;

    private TextView todayDate;
    private TextView temperature;
    private TextView temperstureApp;
    private TextView summary;
    private ImageView iconMain;
    private TextView sunrise;
    private TextView sunset;
    private TextView windSpeed;
    private TextView humidity;
    private TextView pressure;
    private TextView city;
    private ImageButton toSearchWindow;
    private Button backToManBtton;
    double lat = 46.4288699298;
    double lng = 30.7232187;

    String cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        cityName = intent.getStringExtra("city");
        darkSkyWeather = (DarkSkyWeather) intent.getExtras().getSerializable("weather");



        todayDate = (TextView) findViewById(R.id.today_date);
        temperature = (TextView) findViewById(R.id.temp_main);
        temperstureApp = (TextView) findViewById(R.id.temp_feels);
        summary = (TextView) findViewById(R.id.summery_main);
        iconMain = (ImageView) findViewById(R.id.icon_main_in_main);
        sunrise = (TextView) findViewById(R.id.sunrise_time);
        sunset = (TextView) findViewById(R.id.sunset_time);
        windSpeed = (TextView) findViewById(R.id.wind_speed);
        humidity = (TextView) findViewById(R.id.humidity);
        pressure = (TextView) findViewById(R.id.pressure);
        city = (TextView) findViewById(R.id.city_gen);
        toSearchWindow = (ImageButton) findViewById(R.id.burger_button);
        backToManBtton = (Button) findViewById(R.id.back_to_man_button);

        todayDate.setText(RequestData.getDateNow());
        temperature.setText(String.valueOf(darkSkyWeather.getCurrently().getTemperature()));
        temperstureApp.setText("Feels like " + String.valueOf(darkSkyWeather.getCurrently().getApparentTemperature()));
        summary.setText(darkSkyWeather.getCurrently().getSummary());
        iconMain.setImageResource(getIconNum(darkSkyWeather.getCurrently().getIcon()));
        city.setText(cityName);

        sunrise.setText(RequestData.unixTimeStampToTime(darkSkyWeather.getDaily().getDataHourlyList().get(0).getSunriseTime()));
        sunset.setText(RequestData.unixTimeStampToTime(darkSkyWeather.getDaily().getDataHourlyList().get(0).getSunsetTime()));
        windSpeed.setText(String.valueOf(darkSkyWeather.getCurrently().getWindSpeed()) + " mps");
        humidity.setText(String.valueOf((int)(darkSkyWeather.getCurrently().getHumidity() * 100)) + "%");
        pressure.setText(String.valueOf(darkSkyWeather.getCurrently().getPressure()) + " mb");

//        Locale aLocale = new Builder().setLanguage("en").setScript("Latn").setRegion("RS").build();

        city.setText(cityName);

        hourly_weather_table();

        daily_weather_table();

        toSearchWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SearchWindow.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.slide_inleft, R.anim.static_anim);
            }
        });

        backToManBtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestData.IF_LOATION_SELECTED = true;
                Intent intent = new Intent(MainActivity.this, GeneralPage.class);
                intent.putExtra("city", cityName);
                intent.putExtra("weather", darkSkyWeather);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.static_anim, R.anim.top_in);
                finish();
            }
        });

    }

    private void hourly_weather_table(){

        TableLayout full_table = (TableLayout) findViewById(R.id.full_table_hourly);

        List<DataForDay> data = darkSkyWeather.getHourly().getDataHourlyList();


        TableRow rowData = new TableRow(this);
        TableRow rowLine = new TableRow(this);
        TableRow rowPrecip = new TableRow(this);
        int pad = 0;
        LinearLayout.LayoutParams paramsIcon = new LinearLayout.LayoutParams (120, 120);
        paramsIcon.setMargins(15, 0, 15, 0);

        RelativeLayout.LayoutParams paramsForHumidityText = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);


        int arr [] = new int[DATAHOURLY];
        for (int i = 1; i < DATAHOURLY + 1; i++){
            arr[i - 1] = data.get(i).getTemperature();
        }

        Arrays.sort(arr);
        int min = arr[0];
        int max = arr[arr.length - 1];
        int delta = Math.abs(max - min);
        int new_array[] = new int[delta + 1];
        int j = 1;
        new_array[0] = arr[0];

        for (int i = 1; i < arr.length; i ++){
            if (arr[i - 1] != arr[i]){
                new_array[j] = arr[i];
                j ++;
//                Log.d("array", String.valueOf(new_array[j - 1]));
            }
        }


        for (int i = 1; i < DATAHOURLY + 1; i++){

            DataForDay weather = data.get(i);
            pad += 5;
            LinearLayout layoutData = new LinearLayout(this);
            layoutData.setOrientation(LinearLayout.VERTICAL);

            TextView hourLabel = new TextView(this);
            hourLabel.setText(RequestData.unixTimeStampToTime(data.get(i).getTime()));
            hourLabel.setGravity(Gravity.CENTER);


            LinearLayout layoutIconTemp = new LinearLayout(this);
            layoutIconTemp.setOrientation(LinearLayout.VERTICAL);

            ImageView icon = new ImageView(this);
            icon.setLayoutParams(paramsIcon);
            icon.setImageResource(getIconNum(weather.getIcon()));
            TextView temp = new TextView(this);
            temp.setText(String.valueOf(weather.getTemperature()) + "°");
            temp.setGravity(Gravity.CENTER);

            layoutIconTemp.addView(icon);
            layoutIconTemp.addView(temp);

            int index = -1;
            int temp_find = weather.getTemperature();
            int flag = 0;

            while (flag == 0){
                index ++;
                if (new_array[index] == temp_find){
                    flag = 1;
                }
            }

            Log.d("array", String.valueOf(new_array));
            int heightIcon;
            if (delta == 0){
                heightIcon = 75;
            } else {
                heightIcon = 150 - ((150 * index) / delta);
            }

            RelativeLayout relativeLayoutIconTemp = new RelativeLayout(this);

            relativeLayoutIconTemp.setPadding(0, heightIcon, 0,0);

            relativeLayoutIconTemp.addView(layoutIconTemp);

            //Humidity

            TextView precip = new TextView(this);

            precip.setGravity(Gravity.CENTER);

            RelativeLayout relativeLayoutHumidity = new RelativeLayout(this);



            LinearLayout viewHumidity = new LinearLayout(this);

            double prec_d = weather.getPrecipProbability() * 100;
            int prec = (int)prec_d;
            precip.setText(String.valueOf(prec) + "%");

//            if (prec < 4) {
//                prec = 3;
//            }

            if (prec == 0){

                prec = 1;
            }
            LinearLayout.LayoutParams paramsWater = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, prec);
            paramsWater.gravity = Gravity.BOTTOM;

            View viewWater = new View(this);
            viewWater.setBackgroundColor(ContextCompat.getColor(this, R.color.colorHumidity));
            //viewHumidity.setBackgroundColor(ContextCompat.getColor(this, R.color.colorHumidity));
            viewWater.setLayoutParams(paramsWater);

            viewHumidity.addView(viewWater);
            RelativeLayout.LayoutParams paramsForHumidity = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);

            paramsForHumidity.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            paramsForHumidityText.addRule(RelativeLayout.CENTER_IN_PARENT);
            viewHumidity.setLayoutParams(paramsForHumidity);
            precip.setLayoutParams(paramsForHumidityText);

            relativeLayoutHumidity.addView(viewHumidity);
            relativeLayoutHumidity.addView(precip);


            layoutData.addView(hourLabel);
            layoutData.addView(relativeLayoutIconTemp);

            ImageView divider = new ImageView(this);
            divider.setImageResource(R.drawable.vert_line);

            ImageView bott_divider = new ImageView(this);
            bott_divider.setImageResource(R.drawable.hum_line);

            rowData.addView(layoutData);
            rowData.addView(divider);
            rowPrecip.addView(relativeLayoutHumidity);
            rowPrecip.addView(bott_divider);

//            tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }

        ImageView line = new ImageView(this);
        line.setImageResource(R.drawable.line);
        TableRow.LayoutParams paramsForLine = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        paramsForLine.span = 48;
        line.setScaleType(ImageView.ScaleType.FIT_XY);
        line.setLayoutParams(paramsForLine);

        rowData.removeViewAt(47);
        rowPrecip.removeViewAt(47);

        rowLine.addView(line);
        full_table.addView(rowData);
        full_table.addView(rowLine);
        full_table.addView(rowPrecip);

    }

    private void daily_weather_table(){

        TableLayout full_table = (TableLayout) findViewById(R.id.full_table_daily);

        TableRow rowDay = new TableRow(this);
        TableRow rowIconTemp = new TableRow(this);

        List <DataForWeek> data = darkSkyWeather.getDaily().getDataHourlyList();

        TableRow.LayoutParams paramsForDay = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams paramsIcon = new LinearLayout.LayoutParams (120, 120);
        paramsIcon.setMargins(30, 0, 30, 0);

        for (int i = 1; i < DATADAYLY + 1; i++){

            DataForWeek weather = data.get(i);
            TextView dayLabel = new TextView(this);
            dayLabel.setText(RequestData.unixTimeStampToDayOfWeek(weather.getTime()));
            dayLabel.setGravity(Gravity.CENTER);

            paramsForDay.span = 2;

            dayLabel.setLayoutParams(paramsForDay);
            rowDay.addView(dayLabel);

            LinearLayout layoutIconTemp = new LinearLayout(this);
            layoutIconTemp.setOrientation(LinearLayout.VERTICAL);



            ImageView icon = new ImageView(this);
            icon.setImageResource(getIconNum(weather.getIcon()));
            icon.setLayoutParams(paramsIcon);

            TextView temp = new TextView(this);
            String temp_num = weather.getTemperatureHigh() + "↑ " + weather.getTemperatureLow() + "↓";
            temp.setText(temp_num);
            temp.setGravity(Gravity.CENTER);

            ImageView divider = new ImageView(this);
            divider.setImageResource(R.drawable.vert_line_daily);

            layoutIconTemp.addView(icon);
            layoutIconTemp.addView(temp);
            rowIconTemp.addView(layoutIconTemp);
            rowIconTemp.addView(divider);
        }

        rowIconTemp.removeViewAt(13);

        full_table.addView(rowDay);
        full_table.addView(rowIconTemp);
    }

    private int getIconNum (String s){

        String icon = s.replace('-', '_');
        int resId = getResources().getIdentifier(icon , "drawable", getPackageName());
        return resId;
    }




}
