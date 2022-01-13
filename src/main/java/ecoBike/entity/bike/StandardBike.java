package ecoBike.entity.bike;

import ecoBike.entity.dock.Dock;

public class StandardBike extends Bike{
    public static final int VALUE = 400000;
    public StandardBike(){
        super();
    }

    public StandardBike(String barcode, long id, boolean status, String licensePlate, String imageUrl , String type, Dock dock){
        super(barcode,id,status,licensePlate,imageUrl,type,dock);
    }
}
