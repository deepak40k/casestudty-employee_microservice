package com.casestudy.skilltracker.employee.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtility {
    public static Date getDateBeforeDays(int days)
    {
        LocalDate tenDayBefore = LocalDate.now().minusDays(days);
        Date date = Date.from(tenDayBefore.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }
}
