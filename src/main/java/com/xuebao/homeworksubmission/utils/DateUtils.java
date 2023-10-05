package com.xuebao.homeworksubmission.utils;

import java.text.SimpleDateFormat;

public class DateUtils {
    public static java.sql.Date StringToDate(String sDate) {
        /**
         *str转date方法
         */
//        String str = sDate;
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        java.util.Date d = null;
        System.out.println("xuebaosb：：：：："+sDate);
        long d = 0;
        try {
            d = Long.parseLong(sDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date date = new java.sql.Date(d * 1000);

        System.out.println(date);
        return date;
    }
}
