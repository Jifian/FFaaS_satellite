package DAO;

import Entities.MeasurementValue;
import Utils.HibernateUtils;
import org.hibernate.Session;
import Utils.EntityEnum;

import java.util.List;


public abstract class MainDAO
{
    protected HibernateUtils hibernate = HibernateUtils.getInstance();

    protected boolean add(Object obj) {
        boolean isAdded = false;

        try {
            Session session = hibernate.openSession();
            session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
            session.close();

            isAdded = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return isAdded;
    }

    protected boolean update(Object obj) {

        boolean isUpdated = false;

        try
        {
            Session session = hibernate.openSession();
            session.beginTransaction();
            session.saveOrUpdate(obj);
            session.getTransaction().commit();
            session.close();

            isUpdated = true;
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return isUpdated;
    }

    protected boolean delete(Object obj) {

        boolean isDeleted = false;

        if (obj == null) return isDeleted;

        try
        {
            Session session = hibernate.openSession();
            session.beginTransaction();
            session.update(obj);
            session.getTransaction().commit();
            session.close();

            isDeleted = true;
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return isDeleted;
    }

    protected <T> List<T> getList(EntityEnum entity)
    {
        return get(entity);
    }

    private <T> List<T> get(EntityEnum entity) {
        List<T> result = null;

        try
        {
            Session session = hibernate.openSession();
            session.beginTransaction();
            result = (List<T>) session.createQuery(String.format("FROM %s", entity)).list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();

            System.out.println(ex.getMessage());
        }

        return (List<T>) result;
    }


}