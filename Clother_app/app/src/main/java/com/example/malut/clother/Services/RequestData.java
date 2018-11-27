package com.example.malut.clother.Services;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }





}
