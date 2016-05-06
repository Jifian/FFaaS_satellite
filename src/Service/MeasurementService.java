package Service;

import DAO.MainDAO;
import Entities.Datasource;
import Entities.Measurement;
import Utils.EntityEnum;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by yapca on 26-4-2016.
 */
public class MeasurementService extends MainDAO
{
    public boolean add(Measurement measurement)
    {
        return super.add(measurement);
    }

    public List<Measurement> List()
    {
        return getList(EntityEnum.Measurement);
    }

    public boolean isMeasurementNewInDatabase(String date, String datasource)
    {
        List<Measurement> measurements = null;
//        String str_query = "SELECT * FROM measurement AS m " +
//                "INNER JOIN snapshots AS s ON m.snapshot_id = s.id " +
//                "INNER JOIN datasource AS d ON s.datasource_id = d.id " +
//                "WHERE m.datetime = '" + date + "' AND d.name = '" + datasource + "'";

        String str_query = "SELECT * FROM measurement  \n" +

                "INNER JOIN snapshots \n" +
                "ON snapshots.s_id = measurement.snapshot_id  \n" +
                "INNER JOIN datasource \n" +
                "ON datasource.d_id = snapshots.datasource_id  \n" +
                "WHERE measurement.datetime = '" + date + "' AND datasource.name = '" + datasource + "'";
//                "INNER JOIN datasource AS d ON s.datasource_id = d.id " +
                ;
        try
        {
            Session session = hibernate.openSession();
            session.beginTransaction();
            measurements = session.createSQLQuery(str_query).list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();

            System.out.println(ex.getMessage());
        }
        if(measurements != null) {
            if (measurements.size() <= 0) {
                return true;
            }
        }
        return false;
    }

    public void delete(int id)
    {
        super.delete(id);
    }

    public void update(Measurement measurement)
    {
        super.update(measurement);
    }
}
