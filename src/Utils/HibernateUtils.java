
package Utils;

import Entities.Datasource;
import Entities.Measurement;
import Entities.MeasurementValue;
import Entities.Snapshots;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtils {

    private static SessionFactory SESSION_FACTORY = null;
    private static ServiceRegistry SERVICE_REGISTRY = null;

    private static HibernateUtils instance;
    private static Session mSession;

    private HibernateUtils() {
        try {
            Configuration configuration = getConfiguration();

            SERVICE_REGISTRY = new ServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).buildServiceRegistry();
            SESSION_FACTORY = configuration.buildSessionFactory(SERVICE_REGISTRY);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static HibernateUtils getInstance() {
        if (instance == null) {
            synchronized (HibernateUtils.class) {
                if (instance == null) {
                    instance = new HibernateUtils();
                }
            }
        }
        return instance;
    }

    public Session openSession() {
        mSession = SESSION_FACTORY.openSession();
        mSession.setFlushMode(FlushMode.COMMIT);
        ManagedSessionContext.bind(mSession);

        return mSession;
    }

    public void commitTransaction(Session session) {

        ManagedSessionContext.unbind(HibernateUtils.SESSION_FACTORY);
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    private Configuration getConfiguration() {
        Configuration cfg = new Configuration();

        cfg.addAnnotatedClass(Datasource.class);
        cfg.addAnnotatedClass(Snapshots.class);
        cfg.addAnnotatedClass(Measurement.class);
        cfg.addAnnotatedClass(MeasurementValue.class);

        cfg.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        cfg.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/ffaas");
        cfg.setProperty("hibernate.connection.username", "root");
        cfg.setProperty("hibernate.connection.password", "");
        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        cfg.setProperty("hibernate.hbm2ddl.auto", "update");
        cfg.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        cfg.setProperty("hibernate.current_session_context_class", "thread");

        return cfg;
    }
}
