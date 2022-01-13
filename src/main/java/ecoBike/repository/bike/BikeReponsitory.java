package ecoBike.repository.bike;

import ecoBike.entity.bike.Bike;
import ecoBike.entity.db.ECOBikeDB;
import ecoBike.entity.dock.Dock;
import ecoBike.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BikeReponsitory extends Repository {
    public BikeReponsitory() throws SQLException {
        super();
    }
//    private Statement stm;
//    public BikeReponsitory() throws SQLException {
//        stm = ECOBikeDB.getConnection().createStatement();
//    }

    public List findAllByDockId (long dockId) throws SQLException {
        String dId = "\"" + dockId + "\"";
        ResultSet res = stm.executeQuery("select * from Bike where dockId = " + dockId +";");
        ArrayList medium = new ArrayList<>();
        while (res.next()){
            Bike bike = new Bike();
            medium.add(setValue(res,bike));
        }
        return medium;
    }

    public List findAvailableByDockId(long dockId) throws SQLException {
        String dId = "\"" + dockId + "\"";
        ResultSet res = stm.executeQuery("select * from Bike where status = true dockId = " + dockId +";");
        ArrayList medium = new ArrayList<>();
        while (res.next()){
            Bike bike = new Bike();
            medium.add(setValue(res,bike));
        }
        return medium;
    }

    public Bike getBikeById(long id) throws SQLException {
        try {
            String qId = "\"" + id + "\"";
            String sql = "SELECT * FROM Bikes join Dock on Bikes.dockID=Dock.id  where Bikes.id=" + qId + ";";
            ResultSet res = stm.executeQuery(sql);
//            Dock dock = new Dock(res.getInt("dockId"), res.getString("name"), res.getString("address"), res.getInt("numOfPoint"), res.getInt("maxCapacity"), res.getInt("numOfAvailableBike") , res.getDouble("area"));
            if (res.next()) {
                Bike bike = new Bike();
                return setValue(res,bike);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Bike getBikeByBarcode(String barcode) throws SQLException {
        try {
            barcode = "\"" + barcode + "\"";
            String sql = "SELECT * FROM Bikes join Dock on Bikes.dockID=Dock.id  where Bikes.barcode=" + barcode + ";";
            ResultSet res = stm.executeQuery(sql);
//            Dock dock = new Dock(res.getInt("dockId"), res.getString("name"), res.getString("address"), res.getInt("numOfPoint"), res.getInt("maxCapacity"), res.getInt("numOfAvailableBike") , res.getDouble("area"));
            if (res.next()) {
                Bike bike = new Bike();
                return setValue(res,bike);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bike setValue(ResultSet res, Bike bike) throws SQLException {
        bike.setLicensePlate(res.getString("licensePlate"));
        bike.setId(res.getLong("id"));
        bike.setLicensePlate(res.getString("licensePlate"));
        bike.setImageUrl(res.getString("imageUrl"));
        bike.setBarcode(res.getString("barcode"));
        bike.setStatus(res.getBoolean("status"));
        bike.setType(res.getString("type"));
        Dock dock = new Dock();
        dock.setId(res.getInt("dockId"));
        dock.setName(res.getString("name"));
        dock.setMaxCapacity(res.getInt("maxCapacity"));
        dock.setNumOfAvailableBike(res.getInt("numOfAvailableBike"));
        dock.setAddress(res.getString("address"));
        dock.setArea(res.getDouble("area"));
        dock.setNumOfPoint(res.getInt("numOfPoint"));
        bike.setDock(dock);
        return bike;
    }

    public void updateBikeAfterReturn (Bike bike, long id) throws SQLException {
        String sql = "Update Bikes set status = true , dockId = " + id + " where id =" + bike.getId()+";";
        stm.execute(sql);
    }
}
