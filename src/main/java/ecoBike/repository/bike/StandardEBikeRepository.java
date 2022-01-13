package ecoBike.repository.bike;

import ecoBike.entity.bike.Bike;
import ecoBike.entity.bike.StandardEBike;
import ecoBike.repository.bike.BikeReponsitory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StandardEBikeRepository extends BikeReponsitory {
    public StandardEBikeRepository() throws SQLException {
    }
    @Override
    public Bike getBikeById(long id) throws SQLException {
        try {
            String qId = "\"" + id + "\"";
            String sql = "SELECT * FROM Bikes inner join Dock on Bikes.dockId=Dock.id inner join StandardEBikes on Bikes.id = StandardEBikes.bikeId where Bikes.id=" + qId + ";";
            ResultSet res = stm.executeQuery(sql);

            if (res.next()) {
                StandardEBike bike = new StandardEBike();
                bike = (StandardEBike) setValue(res, bike);
                bike.setBattery(res.getDouble("battery"));
                bike.setRemainingTimeTs(res.getLong("remainingTimeTs"));
                return bike;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
