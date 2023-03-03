package com.nisum.foodcourt.resource.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class DateUtil {

    public Date getCurrentDate() {
        return new Date();
    }

    public Date getCurrentDateByTime(long timeInMilliseconds) {
        return new Date(System.currentTimeMillis() + timeInMilliseconds);
    }

    public Timestamp getCurrentTimeStamp() {
        return new Timestamp(new Date().getTime());
    }
}
