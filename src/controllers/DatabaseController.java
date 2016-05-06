package controllers;

import Database.FFaaSDatabase;
import Entities.Datasource;
import Entities.Measurement;
import Service.DatasourceService;
import Service.MeasurementService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
/*
SELECT * FROM  measurement AS m
INNER JOIN snapshots AS s ON m.snapshot_id = s.id
INNER JOIN datasource AS d ON s.datasource_id = d.id
WHERE m.datetime = '19-02-2016 01:30' AND d.name = 'MODISA'
*/

/**
 * Created by yapca on 21-4-2016.
 */
public class DatabaseController {
    DatasourceService ds;
    MeasurementService ms;
    List<Datasource> datasources;
    public DatabaseController()
    {
        ds = new DatasourceService();
        ms = new MeasurementService();
        datasources = ds.List();

    }

    public boolean isMeasurementNewInDatabase(String date, String datasource_name) {


        if(!ds.is_existing_datasource(datasource_name))
            ds.add(new Datasource(datasource_name));
        if(ms.isMeasurementNewInDatabase(date, datasource_name))
            return true;
        //FFaaSDatabase.query("SELECT 'id' FROM 'measurement' WHERE 'DateTime' = \"" + date_time + "\"");

        return false;
    }
}
