package ecoBike.entity.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;
import ecoBike.utils.*;

public class ECOBikeDB {
    private static Logger LOGGER = Utils.getLogger(Connection.class.getName());
    private static Connection connect;

    public static Connection getConnection() {
        if (connect == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connect = DriverManager.getConnection(Configs.DB_URL, Configs.DB_USERNAME, Configs.DB_PASSWORD);
                System.out.println("DB connected");

            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return connect;
    }


    public static void main(String[] args) {
        ECOBikeDB.getConnection();
    }
}
