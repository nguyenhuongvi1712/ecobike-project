package ecoBike.repository.bike;

import java.sql.ResultSet;
import java.sql.SQLException;
import ecoBike.entity.bike.Bike;
import ecoBike.entity.bike.StandardBike;
import ecoBike.repository.bike.BikeReponsitory;

public class StandardBikeRepositoty extends BikeReponsitory {
    public StandardBikeRepositoty() throws SQLException {
        super();
    }

    @Override
    public Bike getBikeById(long id) throws SQLException {
        try {
            String qId = "\"" + id + "\"";
            String sql = "SELECT * FROM Bikes inner join Dock on Bikes.dockId=Dock.id inner join StandardBikes on Bikes.id = StandardBikes.bikeId where Bikes.id=" + qId + ";";
            ResultSet res = stm.executeQuery(sql);

            if (res.next()) {
                StandardBike bike = new StandardBike();
                return setValue(res, bike);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
