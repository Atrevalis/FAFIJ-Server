package fafij.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters {

    private static SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static Date stringToDate(String date) throws ParseException {
        return format.parse(date);
    }

    public static String dateToStrinng(Date date) throws ParseException{
        return format.format(date);
    }
}
