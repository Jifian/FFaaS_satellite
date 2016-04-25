package Database;

//import com.mysql.jdbc.Connection;
import java.sql.*;

/**
 * Created by yapca on 21-4-2016.
 */
public class FFaaSDatabase {
    private static FFaaSDatabase instance = null;
    static final String db_url = "jdbc:mysql://localhost/ffaas";
    static final String user = "root";
    static final String pass = "";
    private Connection conn = null;

    public Connection getConnection() throws SQLException {
        if (conn == null)
        {
            conn = DriverManager.getConnection(db_url, user, pass);
        }
        return conn;
    }

    protected FFaaSDatabase() {
        // Exists only to defeat instantiation.

    }
    public static FFaaSDatabase getInstance() {
        if(instance == null) {
            instance = new FFaaSDatabase();
        }
        return instance;
    }

    public static void query(String s) {
    }
}
