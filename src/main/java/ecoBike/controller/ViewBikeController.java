package ecoBike.controller;

import ecoBike.common.exception.InvalidBarcodeException;
import ecoBike.common.exception.InvalidCardException;
import ecoBike.common.exception.ViewBikeException;
import ecoBike.entity.bike.Bike;
import ecoBike.entity.bike.StandardBike;
import ecoBike.entity.bike.StandardEBike;
import ecoBike.entity.bike.TwinBike;
import ecoBike.repository.bike.BikeReponsitory;
import ecoBike.repository.bike.StandardBikeRepositoty;
import ecoBike.repository.bike.StandardEBikeRepository;
import ecoBike.repository.bike.TwinBikeRepository;

import java.sql.SQLException;

public class ViewBikeController extends BaseController{
    private BikeReponsitory bikeReponsitory;

    /**
     *
     * @throws SQLException
     */
    public ViewBikeController() throws SQLException {
        bikeReponsitory = new BikeReponsitory();
    }

    /**
     * Return Bike
     * @param id
     * @param type
     * @return
     * @throws ViewBikeException
     */
    public Bike setBike(long id, String type) throws ViewBikeException {
        try {
            if (type.equals("standard-e")){
                return new StandardEBikeRepository().getBikeById(id);
            } else if (type.equals("standard")) {
                return new StandardBikeRepositoty().getBikeById(id);
            } else if (type.equals("twin")) {
                return new TwinBikeRepository().getBikeById(id);
            }
        } catch (ViewBikeException | SQLException e) {
            throw new ViewBikeException("Not found Bike");
        }
        return null;
    }

    /**
     * get deposit of bike based on bike's type
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

    /**
     * calculate time
     * @param timeStart
     * @return
     */
    public int calculateTimeToNow(int timeStart){
        int now = (int) (System.currentTimeMillis()/1000);
        int time = now - timeStart;
        return time;
    }

    /**
     * Find Bike by barcode
     * @param barcode
     * @return
     * @throws SQLException
     */
    public Bike convertBarcode(String barcode) throws SQLException {
        if(!validateBarcode(barcode)) throw new InvalidBarcodeException();
        else {
            Bike bike = this.bikeReponsitory.getBikeByBarcode(barcode);
            if (bike == null){
                throw new InvalidCardException("Barcode is not exit");
            }
            return bike;
        }
    }

    /**
     * Check validate barcode
     * @param barcode
     * @return
     * @throws InvalidBarcodeException
     */
    public boolean validateBarcode(String barcode) throws InvalidBarcodeException {
        try {
            if (barcode == null) return false;
            barcode = barcode.trim();
            String regex = "^CODEBIKE[0-9]+$";
            if (!barcode.matches(regex)) return false;
            return true;
        } catch (Exception e) {
            throw new InvalidBarcodeException();
        }
    }
}
