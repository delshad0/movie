package com.example.lenovo.Album1.Activity.recyclerview;

import java.util.Calendar;

/**
 * Created by LENOVO on 9/20/2017.
 */

public class DateClass {
    public String setDate(){
        Calendar c=Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
         int month = c.get(Calendar.MONTH);
        int hour=c.get(Calendar.HOUR);

        String date= String.valueOf(year+"/"+month+"/"+hour);
        return  date;
}
}
