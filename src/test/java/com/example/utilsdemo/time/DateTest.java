package com.example.utilsdemo.time;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateTest {

    public static void main(String[] args) {
//        long diff = getTimeDiff(new Date(new Date().getTime() - 600000), new Date());
//        if (diff>3){
//            System.out.println(diff);
//        }

        Map<String, Object> hashMap = new HashMap<String, Object>();

        hashMap.put("key", "value");
        hashMap.remove("key");

        if (hashMap != null && !hashMap.isEmpty()) {
            System.out.println("hashMap不为空");
        }


    }

    public static long getTimeDiff(Date begin, Date end) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(begin);
        calendar2.setTime(end);
        long milliseconds1 = calendar1.getTimeInMillis();
        long milliseconds2 = calendar2.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        return diff / (60 * 1000);
    }
}
