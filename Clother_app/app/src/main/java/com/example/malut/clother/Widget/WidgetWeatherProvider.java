package com.example.malut.clother.Widget;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malut.clother.GeneralPage;
import com.example.malut.clother.Model.DarkSkyWeather;
import com.example.malut.clother.R;
import com.example.malut.clother.Services.NetworkService;
import com.example.malut.clother.Services.RequestData;
import com.example.malut.clother.SplashActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class WidgetWeatherProvider extends AppWidgetProvider {

    SharedPreferences sPref;
    Double lat;
    Double lng;
    public static final String APP_PREFERENCES = "coordinates";
    public static final String APP_PREFERENCES_LNG = "lng";
    public static final String APP_PREFERENCES_LAT = "lat";
    private DarkSkyWeather darkSkyWeather = new DarkSkyWeather();
    private static final String MyOnClick1 = "myOnClickTag1";
    RemoteViews views;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        sPref = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        List<Double> coorList = loadText();

        RemoteViews remoteV = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent intentSync = new Intent(context, WidgetWeatherProvider.class);
        intentSync.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        PendingIntent pendingSync = PendingIntent.getBroadcast(context,0, intentSync, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteV.setOnClickPendingIntent(R.id.widget_button_refresh,pendingSync);

        for (int i : appWidgetIds) {
            appWidgetManager.updateAppWidget(i, remoteV);
        }
//        Intent intent = new Intent(context, SplashActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
////
//        views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
////        views.setOnClickPendingIntent(R.id.widget_button_refresh, pendingIntent);
//
//        for (int i : appWidgetIds) {
//
//            Intent countIntent = new Intent(context, WidgetWeatherProvider.class);
//            countIntent.setAction(MyOnClick1);
//            countIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, i);
//            pendingIntent = PendingIntent.getBroadcast(context, i, countIntent, 0);
//            views.setOnClickPendingIntent(R.id.widget_button_refresh, pendingIntent);
//        }

        new GetWeather(appWidgetManager, appWidgetIds, context).execute(RequestData.apiRequest(String.valueOf(coorList.get(0)), String.valueOf(coorList.get(1))));


    }

//    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){}

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        Toast toast = Toast.makeText(context, "Updating widget", Toast.LENGTH_LONG);
        toast.show();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), WidgetWeatherProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        onUpdate(context, appWidgetManager, appWidgetIds);

//        Bundle extras = intent.getExtras();
//        if(extras!=null) {
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), WidgetWeatherProvider.class.getName());
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
//
//            onUpdate(context, appWidgetManager, appWidgetIds);
//        }
    }


//        @Override
//    public void onReceive(Context context, Intent intent) {
//
//        super.onReceive(context, intent);
//        List<Dou ble> coorList = loadText();
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
//        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), WidgetWeatherProvider.class);
//        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
//        if (MyOnClick1.equals(intent.getAction())) {
//            new GetWeather(appWidgetManager, appWidgetIds, context).execute(RequestData.apiRequest(String.valueOf(coorList.get(0)), String.valueOf(coorList.get(1))));
//        }
//    }

    private class GetWeather extends AsyncTask<String, Void, String> {

        private AppWidgetManager appWidgetManager;
        private int[] appWidgetIds;
        private Context context;

        public GetWeather(AppWidgetManager appWidgetManager, int[] appWidgetIds, Context context) {
            this.appWidgetManager = appWidgetManager;
            this.appWidgetIds = appWidgetIds;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Gson gson = new Gson();
            Type mType = new TypeToken<DarkSkyWeather>(){}.getType();
            darkSkyWeather = gson.fromJson(s, mType);
            setData(appWidgetIds, context, appWidgetManager);
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



    private void setData(int[] appWidgetIds, Context context, AppWidgetManager appWidgetManager){

        for (int appWidgetId : appWidgetIds){
            Intent intent = new Intent(context, SplashActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            views.setTextViewText(R.id.widget_temp, String.valueOf(darkSkyWeather.getCurrently().getTemperature()) + "°");
            views.setTextViewText(R.id.widget_app_temp, String.valueOf(darkSkyWeather.getCurrently().getApparentTemperature()));
            views.setTextViewText(R.id.widget_city_name, RequestData.coordinatesToCity(darkSkyWeather.getLatitude(), darkSkyWeather.getLongitude(), context));
            views.setTextViewText(R.id.widget_precip_gen, String.valueOf(darkSkyWeather.getCurrently().getPrecipProbability()) + "%");
            views.setTextViewText(R.id.widget_update_time, "Update " + RequestData.getTimNow());
            views.setImageViewResource(R.id.widget_man, setManIcon(darkSkyWeather.getCurrently().getTemperature()));
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }

    }


    private List<Double> loadText() {

        List<Double> coordList = new ArrayList<>();
        if (sPref.contains(APP_PREFERENCES_LNG)){
            coordList.add(Double.parseDouble(sPref.getString(APP_PREFERENCES_LAT, "")));
            coordList.add(Double.parseDouble(sPref.getString(APP_PREFERENCES_LNG, "")));
        } else {
            coordList.add(-73.935242);
            coordList.add(40.730610);
        }
        return coordList;
    }

    private int setManIcon (int t){

        if (t >= 25){
            return  R.drawable.first_type;
        }
        else if (t >= 20){
            return  R.drawable.second_type;
        }
        else if (t >= 15){
            return  R.drawable.third_type;
        }
        else if (t >= 5){
            return  R.drawable.forth_type;
        }
        else if (t >= -50){
            return  R.drawable.fifth_type;
        }

        return R.drawable.first_type;
    }
}
