package com.sososhopping.customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StaticMethod {
    public static String dateFormatMin(String exdate){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS").parse(exdate);
            return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(date);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String dateFormatDay(String exdate){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS").parse(exdate);
            return new SimpleDateFormat("yyyy/MM/dd").format(date);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
