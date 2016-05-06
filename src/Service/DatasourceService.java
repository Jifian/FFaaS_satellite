package Service;

import DAO.MainDAO;
import Entities.Datasource;
import Entities.Snapshots;
import Utils.EntityEnum;
import org.hibernate.Session;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by yapca on 26-4-2016.
 */

public class DatasourceService extends MainDAO
{
    public boolean add(Datasource datasource)
    {
        return super.add(datasource);
    }

    public List<Datasource> List()
    {
        return super.getList(EntityEnum.Datasource);
    }

    public void delete(int id)
    {
        super.delete(id);
    }

    public void update(Datasource datasource)
    {
        super.update(datasource);
    }

    public boolean is_existing_datasource(String datasource_name) {
        List<Datasource> datasources = null;
        String query = "SELECT * FROM datasource WHERE datasource.name = '" + datasource_name + "'";
        try
        {
            Session session = hibernate.openSession();
            session.beginTransaction();
            datasources = session.createSQLQuery(query).list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();

            System.out.println(ex.getMessage());
        }

        if(datasources != null) {
            if (datasources.size() <= 0) {
                return false;
            }
        }
        return true;
    }

    public Datasource getDatasource(String datasource_name) {
        Datasource datasource = null;
        String query = "FROM Datasource WHERE name = '" + datasource_name + "'";
        try
        {
            Session session = hibernate.openSession();
            session.beginTransaction();
            datasource = (Datasource)session.createQuery(query).uniqueResult();
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();

            System.out.println(ex.getMessage());
        }

        if(datasource != null) {
                return datasource;
        }
        return null;
    }

    public void addSnapshot(Datasource datasource, Snapshots snapshot)
    {
        datasource.addSnapshot(snapshot);
        update(datasource);
    }
}
