package ecoBike.repository.dock;

import ecoBike.entity.bike.Bike;
import ecoBike.entity.db.ECOBikeDB;
import ecoBike.entity.dock.Dock;
import ecoBike.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DockRepository extends Repository {
    private Statement stm;
    public DockRepository () throws SQLException {
        stm = ECOBikeDB.getConnection().createStatement();
    }
    public List getAllDock() throws SQLException {
        ResultSet res = stm.executeQuery("select * from Dock");
        ArrayList medium = new ArrayList<>();
        while (res.next()) {
            Dock dock = new Dock();
            dock.setId(res.getInt("id"));
            dock.setName(res.getString("name"));
            dock.setAddress(res.getString("address"));
            dock.setMaxCapacity(res.getInt("maxCapacity"));
            dock.setNumOfAvailableBike(res.getInt("numOfAvailableBike"));
            dock.setNumOfPoint(res.getInt("numOfPoint"));
            dock.setArea(res.getDouble("area"));
            medium.add(dock);
        }
        return medium;
    }
    public Dock findDockById(long id) throws SQLException {
        String qId = "\"" + id + "\"";
        ResultSet res = stm.executeQuery("select * from Dock where id = "+ qId + ";");
        if(res.next()){
            Dock dock = new Dock();
            dock.setId(res.getInt("id"));
            dock.setName(res.getString("name"));
            dock.setAddress(res.getString("address"));
            dock.setArea(res.getDouble("area"));
            return dock;
        }
        return null;
    }

    public List findDockByName(String name) throws SQLException {
        name = "\"" + name + "\"";
        ResultSet res = stm.executeQuery("select * from Dock where name = "+ name + ";");
        ArrayList medium = new ArrayList<>();
        while (res.next()) {
            Dock dock = new Dock();
            dock.setId(res.getInt("id"));
            dock.setName(res.getString("name"));
            dock.setAddress(res.getString("address"));
            medium.add(dock);
        }
        return medium;
    }

    public List getAllBikeAvailable(int dockId) throws SQLException {
        ResultSet res = stm.executeQuery("select * from Bikes where status = true and dockId = " + dockId +";");
        ArrayList medium = new ArrayList<>();
        while (res.next()) {
            Bike bike = new Bike();
            bike.setId(res.getInt("id"));
            bike.setType(res.getString("type"));
            bike.setLicensePlate(res.getString("licensePlate"));
            bike.setBarcode(res.getString("barCode"));
            bike.setImageUrl(res.getString("imageUrl"));
            bike.setStatus(res.getBoolean("status"));
            medium.add(bike);
        }
        return medium;
    }
    public List getAllAvailableDock() throws SQLException {
        ResultSet res = stm.executeQuery("select * from Dock where maxCapacity > 0");
        ArrayList medium = new ArrayList<>();
        while (res.next()) {
            Dock dock = new Dock();
            dock.setId(res.getInt("id"));
            dock.setName(res.getString("name"));
            dock.setAddress(res.getString("address"));
            dock.setMaxCapacity(res.getInt("maxCapacity"));
            dock.setNumOfAvailableBike(res.getInt("numOfAvailableBike"));
            dock.setNumOfPoint(res.getInt("numOfPoint"));
            dock.setArea(res.getDouble("area"));
            medium.add(dock);
        }
        return medium;
    }

    public void updateDock(long id , int maxCapacity , int numOfAvailableBike) throws SQLException {
        stm.execute("update Dock set maxCapacity = "+ maxCapacity+",numOfAvailableBike = "+numOfAvailableBike+" where id = "+id+";");
    }

}
