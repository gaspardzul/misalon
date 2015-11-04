package com.claudia.misalon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaspar on 21/07/15.
 */
public class DateUtils {
    Date date;


    public DateUtils(Date date){
        this.date = date;
    }

    public String getHr(){
        SimpleDateFormat sdf = new SimpleDateFormat("H");
        return sdf.format(date.getTime());
    }



    public String getMin(){
        SimpleDateFormat sdf = new SimpleDateFormat("mm");
        return sdf.format(date.getTime());
    }

    public String getDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(date.getTime());
    }

    public String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-y");
        return sdf.format(date.getTime());
    }

    public String getNow(){
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/y h:mm a");
        return sdf.format(date.getTime());
    }

    public String getMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        int month = date.getMonth();
        String nameMonth ="";

        switch (month) {
            case 0:
                nameMonth = "ENE";
                break;
            case 1:
                nameMonth = "FEB";
                break;
            case 2:
                nameMonth = "MAR";
                break;
            case 3:
                nameMonth = "ABR";
                break;
            case 4:
                nameMonth = "MAY";
                break;
            case 5:
                nameMonth = "JUN";
                break;
            case 6:
                nameMonth = "JUL";
                break;
            case 7:
                nameMonth = "AGO";
                break;
            case 8:
                nameMonth = "SEP";
                break;
            case 9:
                nameMonth = "OCT";
                break;
            case 10:
                nameMonth = "NOV";
                break;
            case 11:
                nameMonth = "DIC";
                break;
        }

        return nameMonth;
    }


}
