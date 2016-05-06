package Service;

import DAO.MainDAO;
import Entities.MeasurementValue;
import Utils.EntityEnum;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by yapca on 26-4-2016.
 */
public class MeasurementValueService extends MainDAO
{
    private Session session;
    private Transaction tx;
    StatusTransaction status = StatusTransaction.CLOSED;
    private enum StatusTransaction { CLOSED, OPEN, BUSY }
    public boolean add(MeasurementValue measurement_value)
    {
        return super.add(measurement_value);
    }

    public List<MeasurementValue> List()
    {
        return getList(EntityEnum.MeasurementValue);
    }

    public void delete(int id)
    {
        super.delete(id);
    }

    public void update(MeasurementValue measurement_value)
    {
        super.update(measurement_value);
    }

    public void openSession() {
        try {
            if(status.equals(StatusTransaction.CLOSED)) {
                session = hibernate.openSession();
                tx = session.beginTransaction();
                status = StatusTransaction.OPEN;
            } else if (status.equals(StatusTransaction.OPEN))
            {
                    throw new Exception("transaction is already open, cant open it again");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBatch(MeasurementValue measurement_value) {
        if(status.equals(StatusTransaction.OPEN)) {
            session.save(measurement_value);
        }
    }

    public void flush() {
        if(status.equals(StatusTransaction.OPEN)) {
            session.flush();
            session.clear();
        }
    }

    public void commit() {
        if (status.equals(StatusTransaction.OPEN))
        {
            tx.commit();
            session.close();
            status = StatusTransaction.CLOSED;
        }
    }
}
