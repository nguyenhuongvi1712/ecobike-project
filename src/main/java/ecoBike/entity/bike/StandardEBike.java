package ecoBike.entity.bike;

import ecoBike.entity.dock.Dock;

public class StandardEBike extends Bike{
    public final static int VALUE = 700000;
    private double battery ;
    private long remainingTimeTs;

    public StandardEBike(){
        super();
    }
    public StandardEBike(String barcode, long id, boolean status, String licensePlate, String imageUrl , String type, Dock dock, double battery , long remainingTimeTs){
        super(barcode,id,status,licensePlate,imageUrl,type,dock);
        this.battery = battery;
        this.remainingTimeTs = remainingTimeTs;
    }

    public double getBattery() {
        return battery;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    public long getRemainingTimeTs() {
        return remainingTimeTs;
    }

    public void setRemainingTimeTs(long remainingTimeTs) {
        this.remainingTimeTs = remainingTimeTs;
    }
}

