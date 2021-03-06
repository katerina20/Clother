package com.example.malut.clother;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.malut.clother.Model.DarkSkyWeather;
import com.example.malut.clother.Services.NetworkService;
import com.example.malut.clother.Services.RequestData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchWindow extends AppCompatActivity implements LocationListener {

    public ArrayList<String> city_array = new ArrayList();
    public ArrayAdapter<String> adapter;
    private FloatingActionButton fab;
    public ListView lvMain;
    public List<String> coordinates;
    public Button backButton;
    public ImageButton geoButton;
    static double lat, lng;
    AlertDialog.Builder ad;
    LocationManager locationManager;
    String provider;
    int MY_PERMISSION = 0;
    public static final String APP_PREFERENCES = "coordinates";
    public static final String APP_PREFERENCES_LNG = "lng";
    public static final String APP_PREFERENCES_LAT = "lat";
    public static final String FILENAME = "CITIES";
    SharedPreferences sPref;
    ProgressDialog prd;
    private DarkSkyWeather darkSkyWeather = new DarkSkyWeather();
//    String[] s = {"Odesa", "Lviv", "Energodar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_search_window);

        backButton = (Button) (findViewById(R.id.backButton));
        geoButton = (ImageButton) findViewById(R.id.get_geo_button);

        readFile();

        lvMain = (ListView) findViewById(R.id.list_search);
        adapter = new ArrayAdapter<String>(this,
                R.layout.my_list_item, city_array);
        lvMain.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMainActivity = new Intent(SearchWindow.this, MainActivity.class);
                openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openMainActivity, 0);
                finish();
            }
        });

        geoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prd = new ProgressDialog(SearchWindow.this);
                //Get coordinates
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                provider = locationManager.getBestProvider(new Criteria(), false);
//
                if (ActivityCompat.checkSelfPermission(SearchWindow.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SearchWindow.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SearchWindow.this, new String[]{
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.SYSTEM_ALERT_WINDOW,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, MY_PERMISSION);
                    return;
                }
//                Location location = locationManager.getLastKnownLocation(provider);
//                if (location == null){
//                    Log.e("TAG", "No location");
//                }
//                if (!RequestData.IF_LOATION_SELECTED) {
//                    locationManager.requestLocationUpdates(provider, 400, 1, this);
//
//                }

//                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(provider, 0, 1, SearchWindow.this);


                prd.setTitle("Getting geoposition...");
                prd.show();

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchWindow.this, FindCityActivity.class);
                startActivity(intent);

            }
        });

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = city_array.get(position);
                city = city.replaceAll(",.*", "");
                try {
                    coordinates = RequestData.cityToCoordinates(city, SearchWindow.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                new SearchWindow.GetWeather().execute(RequestData.apiRequest(coordinates.get(0), coordinates.get(1)));

            }
        });

        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                showDialog(parent, position);
                ad.show();

                return true;
            }
        });
    }

    private void showDialog(AdapterView<?> parent, final int position){

        String messageD = "Do you want to delete " + city_array.get(position) + "?";
        Context context = SearchWindow.this;

        ad = new AlertDialog.Builder(SearchWindow.this);
        ad.setTitle("Delete");
        ad.setMessage(messageD);
        ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });
        ad.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(SearchWindow.this, city_array.get(position) + " deleted",
                        Toast.LENGTH_LONG).show();
                adapter.remove(city_array.get(position));
                adapter.notifyDataSetChanged();
//                city_array.remove(position);
                writeFile();
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(this);

        lat = location.getLatitude();
        lng = location.getLongitude();

        saveText(lat, lng);
        prd.dismiss();

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

        ProgressDialog pd = new ProgressDialog(SearchWindow.this);

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
            Intent intent = new Intent(SearchWindow.this, MainActivity.class);
            intent.putExtra("weather", darkSkyWeather);
            intent.putExtra("city", RequestData.coordinatesToCity(darkSkyWeather.getLatitude(), darkSkyWeather.getLongitude(), SearchWindow.this));
            startActivityForResult(intent, 1);
            overridePendingTransition(R.anim.slide_inleft, R.anim.static_anim);
            finish();
        }

        @Override
        protected String doInBackground(String... strings) {

            String json_line = null;
            String urlString = strings[0];

            NetworkService http = new NetworkService();
            json_line = http.getHTTPData(urlString);


            return json_line;

        }
    }

    @Override
    public void onBackPressed() {

        Intent openMainActivity= new Intent(SearchWindow.this, MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(openMainActivity, 0);

        super.onBackPressed();


    }

    private void readFile() {
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                city_array.add(str);
                Log.d("seeee", str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveText(double lt, double ln) {

        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(APP_PREFERENCES_LAT, String.valueOf(lt));
        editor.putString(APP_PREFERENCES_LNG, String.valueOf(ln));
        editor.apply();
//        String c = lt + "," + ln;
//        sPref = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor ed = sPref.edit();
//        ed.putString(COORDS, c);
//        ed.apply();


    }

    private void writeFile(){

        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE)));
            // пишем данные

            for (int i = 0; i < city_array.size(); i++) {
                // Maybe:
                bw.write(city_array.get(i) + "");
                bw.newLine();
            }
            // закрываем поток
            bw.close();
            Log.d("seeee", "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
