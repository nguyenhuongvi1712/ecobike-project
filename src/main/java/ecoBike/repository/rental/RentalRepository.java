package ecoBike.repository.rental;

import ecoBike.entity.bike.Bike;
import ecoBike.entity.rental.Rental;
import ecoBike.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RentalRepository extends Repository {
    public RentalRepository() throws SQLException {
    }


    public Rental newRental(Bike rentBike) throws SQLException {
        int affectedRows = stm.executeUpdate("INSERT INTO Rental(bikeId) VALUES (" + rentBike.getId() + ");", Statement.RETURN_GENERATED_KEYS);
        Rental rental = new Rental();
        ResultSet keys = stm.getGeneratedKeys();
        if(keys.next()){
            rental.setId(keys.getLong(1));
        }
        rental.setRentalBike(rentBike);
        return rental;
    }

    public Rental updateTimeStart(Rental rental) throws SQLException {
        stm.execute("Update Rental set totalUpToNow = 0,timeStart = "+ rental.getTimeStart() + " where id =" + rental.getId() + ";");
        stm.execute("Update Bikes set status = false where id =" + rental.getRentalBike().getId() + ";");
        return rental;
    }

    public void updateTotal (Rental rental) throws SQLException {
        stm.execute("Update Rental set timeEnd = "+rental.getTimeEnd() +",totalUpToNow = "+ rental.getTotalUpToNow() + " where id =" + rental.getId() + ";");
    }

    public void cancelRental(Rental rental) throws SQLException {
        System.out.println("rental: "+ rental.getId());
        stm.execute("Delete from Rental where id = "+ rental.getId()+";");
        stm.execute("Update Bikes set status = true where id =" + rental.getRentalBike().getId() + ";");
    }

}
