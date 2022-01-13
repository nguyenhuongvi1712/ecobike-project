package ecoBike.entity.bike;

import ecoBike.entity.dock.Dock;

public class Bike {
    private String barcode ;
    private long id ;
    private boolean status ; // false : is renting
    private String licensePlate ;
    private String imageUrl;
    private Dock dock;
    private String type;

    public Bike(){}

    public Bike(String barcode, long id, boolean status, String licensePlate, String imageUrl, String type, Dock dock) {
        this.barcode = barcode;
        this.id = id;
        this.status = status;
        this.licensePlate = licensePlate;
        this.imageUrl = imageUrl;
        this.dock = dock;
        this.type = type;
    }


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Dock getDock() {
        return dock;
    }

    public void setDock(Dock dock) {
        this.dock = dock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
