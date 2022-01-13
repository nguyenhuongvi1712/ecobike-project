package ecoBike.entity.rental;

import ecoBike.entity.bike.Bike;
import ecoBike.entity.user.User;

public class Rental {
    private long id;
    private Bike rentalBike;
    private long timeStart; // timestamp
    private long timeEnd; // timestamp
    private int totalUpToNow;

    public Rental(long id, Bike rentalBike, long timeStart, long timeEnd) {
        this.id = id;
        this.rentalBike = rentalBike;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public Rental(long id, Bike rentalBike, long timeStart) {
        this.id = id;
        this.rentalBike = rentalBike;
        this.timeStart = timeStart;
    }

    public Rental(long id, Bike rentalBike, long timeStart, long timeEnd, int totalUpToNow) {
        this.id = id;
        this.rentalBike = rentalBike;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.totalUpToNow = totalUpToNow;
    }

    public Rental(){};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bike getRentalBike() {
        return rentalBike;
    }

    public void setRentalBike(Bike rentalBike) {
        this.rentalBike = rentalBike;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getTotalUpToNow() {
        return totalUpToNow;
    }

    public void setTotalUpToNow(int totalUpToNow) {
        this.totalUpToNow = totalUpToNow;
    }
}
