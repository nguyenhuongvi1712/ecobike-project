package ecoBike.repository.bike;

import ecoBike.entity.bike.Bike;
import ecoBike.entity.bike.TwinBike;
import ecoBike.repository.bike.BikeReponsitory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TwinBikeRepository extends BikeReponsitory {
    public TwinBikeRepository() throws SQLException {
    }

    @Override
    public Bike getBikeById(long id) throws SQLException {
        try {
            String qId = "\"" + id + "\"";
            String sql = "SELECT * FROM Bikes inner join Dock on Bikes.dockId=Dock.id inner join TwinBikes on Bikes.id = TwinBikes.bikeId where Bikes.id=" + qId + ";";
            ResultSet res = stm.executeQuery(sql);

            if (res.next()) {
                TwinBike bike = new TwinBike();
                return setValue(res, bike);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
