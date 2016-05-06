package controllers;

import Entities.Datasource;
import Entities.Measurement;
import Entities.MeasurementValue;
import Entities.Snapshots;
import Service.DatasourceService;
import Service.MeasurementService;
import Service.MeasurementValueService;
import Service.SnapshotService;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by yapca on 13-4-2016.
 */
public class SaveFile {
    static final int EXPECTED_ARR_LENGTH = 3;

    public boolean save(float[][] arr_saving_values, String datasource_name, String level, String measurement_name, String unit, Date date) throws Exception {
        DatasourceService ds = new DatasourceService();
        SnapshotService ss = new SnapshotService();
        MeasurementService ms = new MeasurementService();
        MeasurementValueService mvs = new MeasurementValueService();

        Snapshots snapshot = new Snapshots(level, ds.getDatasource(datasource_name));
        ss.add(snapshot);
        Measurement measurement = new Measurement(measurement_name, unit, date, snapshot);
        ms.add(measurement);

        if(arr_saving_values.length != EXPECTED_ARR_LENGTH)
            throw new Exception("Array does not have expected array length in SaveFile class.");

        mvs.openSession();
        int i = 0;
        for(; i< arr_saving_values[0].length; i++)
        {
            MeasurementValue measurement_value = new MeasurementValue(arr_saving_values[0][i], arr_saving_values[1][i], arr_saving_values[2][i], measurement);
            mvs.addBatch(measurement_value);
            if (i%50 == 0)
            {
                mvs.flush();
                System.out.print(i + "- ");
            }
        }
        System.out.println("\n" + i + "Commit Incomming ");
        mvs.commit();
        return true;
    }
}
