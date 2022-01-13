package ecoBike.entity.bike;

import ecoBike.entity.dock.Dock;

public class TwinBike extends Bike{
    public static final int VALUE = 550000;
    public TwinBike(){
        super();
    }
    public TwinBike(String barcode, long id, boolean status, String licensePlate, String imageUrl , String type, Dock dock){
        super(barcode,id,status,licensePlate,imageUrl,type,dock);
    }

}
