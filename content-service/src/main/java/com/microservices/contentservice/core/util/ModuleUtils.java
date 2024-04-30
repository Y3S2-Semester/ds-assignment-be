package com.microservices.contentservice.core.util;

import java.util.Calendar;
import java.util.Date;

public class ModuleUtils {

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        // MONTH starts with 0 i.e. ( 0 - Jan)
        calendar.setTime(new Date());

        return calendar.getTime();
    }
}
