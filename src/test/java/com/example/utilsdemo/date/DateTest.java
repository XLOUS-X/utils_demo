package com.example.utilsdemo.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

    public static void main(String[] args) throws ParseException {
        System.out.println(formatDate("2021-04-02 09:44:38.874"));
    }

    public static String formatDate(String date) throws ParseException {
        SimpleDateFormat from=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat to=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date temp=from.parse(date);
        return to.format(temp);
    }
}
