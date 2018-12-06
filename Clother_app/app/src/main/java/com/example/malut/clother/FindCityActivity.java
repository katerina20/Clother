package com.example.malut.clother;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class FindCityActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    List<String> citiesArray;
    String input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_city);

        citiesArray = getCities();

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(new ArrayAdapter<>(this,
                R.layout.my_list_item, citiesArray));


        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                input = autoCompleteTextView.getText().toString();
                input = input + "\n";
                writeFile(input);
                Intent intent = new Intent(FindCityActivity.this, SearchWindow.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.slide_inleft, R.anim.static_anim);
                finish();
            }
        });
    }



    private void writeFile(String city){

        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(SearchWindow.FILENAME,  MODE_APPEND)));
            // пишем данные
            bw.write(city);
            // закрываем поток
            bw.close();
            Log.d("seeee", "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getCities(){

        List<String>lstCities = new ArrayList<>();
        try{
            StringBuilder builder = new StringBuilder();
            InputStream is = getResources().openRawResource(R.raw.city_list);
            GZIPInputStream gzipInputStream = new GZIPInputStream(is);

            InputStreamReader reader = new InputStreamReader(gzipInputStream);
            BufferedReader in = new BufferedReader(reader);

            String readed;
            while ((readed = in.readLine()) != null)
                builder.append(readed);
            lstCities = new Gson().fromJson(builder.toString(),new TypeToken<List<String>>(){}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lstCities;
    }
}
