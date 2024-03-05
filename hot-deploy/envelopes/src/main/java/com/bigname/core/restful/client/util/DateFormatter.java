package com.bigname.core.restful.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Manu on 2/22/2017.
 */
public class DateFormatter {

    /** ISO 8601 - Time Zone GMT */
    public static final String API_DATE_PATTERN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

//    public static final String API_DATE_PATTERN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    private DateFormatter() {
        //should not be instantiated
    }

    public static String formatDate(Date date) {
        Preconditions.checkNotNull(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(API_DATE_PATTERN_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(date);
    }

}
