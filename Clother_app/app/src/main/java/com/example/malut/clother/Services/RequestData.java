package com.example.malut.clother.Services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.DataFormatException;

public class RequestData {

    public static String API_KEY = "dfc4a3914ba24e771fd68f9f45d30bfb";
    public static String API_LINK = "https://api.darksky.net/forecast/";

    @NonNull
    public static String apiRequest(String lat, String lng){
        StringBuilder sb = new StringBuilder(API_LINK);
        sb.append(String.format("%s/%s,%s?exclude=minutely,alerts,flags&units=auto", API_KEY, lat, lng));
        return sb.toString();
    }

    public static String unixTimeStampToTime(double unixTimeStamp){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);
        return dateFormat.format(date);
    }

    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String unixTimeStampToDayOfWeek(double unixTimeStamp) {
        DateFormat dateFormat = new SimpleDateFormat("EEE", Locale.US);
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);
        return dateFormat.format(date);
    }

    public static String coordinatesToCity(double lat, double lng, Context c){

        Geocoder geocoder = new Geocoder(c, Locale.US);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cityName = addresses.get(0).getLocality();
        return cityName;

    }




}
