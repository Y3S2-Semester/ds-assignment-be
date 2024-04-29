package com.microservices.courseservice.core.util;

import java.util.Calendar;
import java.util.Date;

public class ModuleUtils {

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getTime();
    }
}
