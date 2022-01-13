package ecoBike.repository;

import ecoBike.entity.db.ECOBikeDB;

import java.sql.SQLException;
import java.sql.Statement;

public class Repository {
    protected Statement stm;

    /**
     * The responsibility of controller is to alter the state of model layer
     * @throws SQLException
     */
    public Repository() throws SQLException {
        stm = ECOBikeDB.getConnection().createStatement();
    }
}
