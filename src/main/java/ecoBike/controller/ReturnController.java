package ecoBike.controller;

import ecoBike.entity.bike.Bike;
import ecoBike.entity.bike.StandardBike;
import ecoBike.entity.bike.StandardEBike;
import ecoBike.entity.bike.TwinBike;
import ecoBike.entity.rental.Rental;

public class ReturnController extends BaseController{


    /**
     * Return renting fee based on renting time and coefficient of each bike
     * @param coefficient
     * @param second
     * @return
     */
    public int calculateFee(double coefficient, int second) {
        int time = (int) Math.ceil( second / 60);
        // TODO Auto-generated method stub
        if (time < 10)
            return 0;
        else {
            int fee = 0;
            if (time <= 30)
                fee = 10000;
            else {
                fee = (int) (10000 + (Math.ceil((time - 30) / 15) * 3000));
            }
            return (int) (fee * coefficient);
        }
    }

    /**
     * Return amount ( = fee - deposit )
     * @param rental
     * @return
     */
    public int calculateAmount(Rental rental){
        return rental.getTotalUpToNow() - getDeposit(rental.getRentalBike());
    }

    /**
     * Return amount
     * @param bike
     * @param second
     * @return
     */
   public int getAmount (Bike bike , int second) {
       double coefficient = 1;
       if(bike instanceof StandardBike)
           coefficient = 1;
       else if(bike instanceof StandardEBike)
           coefficient = 1.5;
       else if(bike instanceof TwinBike)
           coefficient = 1.5;
       return calculateFee(coefficient,second);
   }

    /**
     * Return deposit of bike
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
