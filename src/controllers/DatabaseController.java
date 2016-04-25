package controllers;

import Database.FFaaSDatabase;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by yapca on 21-4-2016.
 */
public class DatabaseController {
    FFaaSDatabase ffaas;

    public void DatabaseController()
    {
        FFaaSDatabase.getInstance();
    }

    public void isFileNewInDatabase(String file_name) {
        DateTime date_time = new DateTime(0);
        date_time = date_time.withZone(DateTimeZone.UTC);
        // year from file_name minus the year date_time was initialized with
        date_time = date_time.plusYears(Integer.parseInt(file_name.substring(0,4)) - date_time.getYear());

        // set current day minus the 1 day it adds at initialization.
        date_time = date_time.plusDays(Integer.parseInt(file_name.substring(4,7)) - 1);

        //set Time
        date_time = date_time.plusHours(Integer.parseInt(file_name.substring(7,9)))
                .plusMinutes(Integer.parseInt(file_name.substring(9,11)))
                .plusSeconds(Integer.parseInt(file_name.substring(11,13)));
        String str_date_time = DateTimeFormat.forPattern("yyyy-M-d HH:m:s").print(date_time);

        FFaaSDatabase.query("SELECT 'id' FROM 'measurement' WHERE 'DateTime' = \"" + date_time + "\"");
    }
}
