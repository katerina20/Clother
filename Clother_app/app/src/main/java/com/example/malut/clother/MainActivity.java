package com.example.malut.clother;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.example.malut.clother.FontSet.TypefaceUtil;

public class MainActivity extends AppCompatActivity {

    public final static int DATAHOURLY = 24;
    public final static int DATADAYLY = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hourly_weather_table();

        daily_weather_table();

    }

    private void hourly_weather_table(){

        TableLayout full_table = (TableLayout) findViewById(R.id.full_table_hourly);



        TableRow rowData = new TableRow(this);
        TableRow rowLine = new TableRow(this);
        TableRow rowPrecip = new TableRow(this);
        int pad = 0;

        for (int i = 0; i < DATAHOURLY; i++){

            pad += 5;
            LinearLayout layoutData = new LinearLayout(this);
            layoutData.setOrientation(LinearLayout.VERTICAL);

            TextView hourLabel = new TextView(this);
            hourLabel.setText("09:00");
            hourLabel.setGravity(Gravity.CENTER);


            LinearLayout layoutIconTemp = new LinearLayout(this);
            layoutIconTemp.setOrientation(LinearLayout.VERTICAL);

            ImageView icon = new ImageView(this);
            icon.setImageResource(R.drawable.clear_day);

            TextView temp = new TextView(this);
            temp.setText("22°");
            temp.setGravity(Gravity.CENTER);

            layoutIconTemp.addView(icon);
            layoutIconTemp.addView(temp);

            RelativeLayout relativeLayoutIconTemp = new RelativeLayout(this);
            relativeLayoutIconTemp.setPadding(0, 200, 0,0);

            relativeLayoutIconTemp.addView(layoutIconTemp);

            //Humidity

            TextView precip = new TextView(this);
            precip.setText("20%");
            precip.setGravity(Gravity.CENTER);

            RelativeLayout relativeLayoutHumidity = new RelativeLayout(this);
            RelativeLayout.LayoutParams paramsForHumidity = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams paramsForHumidityNum = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT);


            LinearLayout viewHumidity = new LinearLayout(this);
            LinearLayout.LayoutParams paramsWater = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.WRAP_CONTENT, 100);

            View viewWater = new View(this);
            viewWater.setBackgroundColor(ContextCompat.getColor(this, R.color.colorHumidity));
            //viewHumidity.setBackgroundColor(ContextCompat.getColor(this, R.color.colorHumidity));
            viewWater.setLayoutParams(paramsWater);

            viewHumidity.addView(viewWater);


            paramsForHumidity.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            paramsForHumidity.addRule(RelativeLayout.CENTER_IN_PARENT);
            viewHumidity.setLayoutParams(paramsForHumidity);
            precip.setLayoutParams(paramsForHumidityNum);

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

        rowLine.addView(line);
        full_table.addView(rowData);
        full_table.addView(rowLine);
        full_table.addView(rowPrecip);

    }

    private void daily_weather_table(){

        TableLayout full_table = (TableLayout) findViewById(R.id.full_table_daily);

        TableRow rowDay = new TableRow(this);
        TableRow rowIconTemp = new TableRow(this);


        for (int i = 0; i < DATADAYLY; i++){

            TextView dayLabel = new TextView(this);
            dayLabel.setText("Mon");
            dayLabel.setGravity(Gravity.CENTER);
            TableRow.LayoutParams paramsForDay = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            paramsForDay.span = 2;

            dayLabel.setLayoutParams(paramsForDay);
            rowDay.addView(dayLabel);

            LinearLayout layoutIconTemp = new LinearLayout(this);
            layoutIconTemp.setOrientation(LinearLayout.VERTICAL);

            ImageView icon = new ImageView(this);
            icon.setImageResource(R.drawable.clear_day);

            TextView temp = new TextView(this);
            temp.setText(" 22↑ 20↓");
            temp.setGravity(Gravity.CENTER);

            ImageView divider = new ImageView(this);
            divider.setImageResource(R.drawable.vert_line_daily);

            layoutIconTemp.addView(icon);
            layoutIconTemp.addView(temp);
            rowIconTemp.addView(layoutIconTemp);
            rowIconTemp.addView(divider);

        }

        full_table.addView(rowDay);
        full_table.addView(rowIconTemp);
    }



}
