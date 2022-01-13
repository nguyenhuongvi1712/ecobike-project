package ecoBike.controller;

import ecoBike.entity.bike.Bike;
import ecoBike.entity.bike.StandardBike;
import ecoBike.entity.bike.StandardEBike;
import ecoBike.entity.bike.TwinBike;
import ecoBike.entity.invoice.Invoice;
import ecoBike.entity.rental.Rental;
import ecoBike.repository.bike.BikeReponsitory;
import ecoBike.repository.invoice.InvoiceRepository;
import ecoBike.repository.rental.RentalRepository;

import java.sql.SQLException;

public class RentBikeController extends BaseController{
    private RentalRepository rentalRepository;
    private BikeReponsitory bikeReponsitory;

    /**
     *
     * @throws SQLException
     */
    public RentBikeController() throws SQLException {
        rentalRepository = new RentalRepository();
        bikeReponsitory = new BikeReponsitory();
    }

    /**
     * Check available of bike
     * @param bike
     * @return
     * @throws SQLException
     */
    public boolean checkAvailableBike(Bike bike) throws SQLException {
        Bike checkBike = this.bikeReponsitory.getBikeById(bike.getId());
        return checkBike.isStatus();

    }

    /**
     * Create new Rental after rent bike
     * @param rented
     * @return
     * @throws SQLException
     */
    public Rental createRental(Bike rented) throws SQLException {
        Rental rental = this.rentalRepository.newRental(rented);
        return rental;
    }

    /**
     * Delete rental when user click on button cancel
     * @param rental
     * @throws SQLException
     */

    public void deleteRental (Rental rental) throws SQLException {
        this.rentalRepository.cancelRental(rental);
    }

    /**
     * Get deposit of bike for rent bike
     * @param bike
     * @return
     */

    public int getDeposit(Bike bike) {
        if(bike instanceof StandardBike)
            return (int) (StandardBike.VALUE * 0.4);
        else if(bike instanceof StandardEBike)
            return (int) (StandardEBike.VALUE * 0.4);
        else if(bike instanceof TwinBike)
            return (int) (TwinBike.VALUE * 0.4);
        else
            return 0;
    }
}
