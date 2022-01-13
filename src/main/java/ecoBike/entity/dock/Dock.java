package ecoBike.entity.dock;

public class Dock {
    private int id;
    private String name;
    private String address;
    private int numOfPoint;
    private int maxCapacity;
    private int numOfAvailableBike;
    private double area;

    public Dock(int id, String name, String address, int numOfPoint, int maxCapacity) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numOfPoint = numOfPoint;
        this.maxCapacity = maxCapacity;
    }

    public Dock(int id, String name, String address, int numOfPoint, int maxCapacity, int numOfAvailableBike) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numOfPoint = numOfPoint;
        this.maxCapacity = maxCapacity;
        this.numOfAvailableBike = numOfAvailableBike;
    }

    public Dock(int id, String name, String address, int numOfPoint, int maxCapacity, int numOfAvailableBike, double area) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numOfPoint = numOfPoint;
        this.maxCapacity = maxCapacity;
        this.numOfAvailableBike = numOfAvailableBike;
        this.area = area;
    }

    public Dock(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumOfPoint() {
        return numOfPoint;
    }

    public void setNumOfPoint(int numOfPoint) {
        this.numOfPoint = numOfPoint;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getNumOfAvailableBike() {
        return numOfAvailableBike;
    }

    public void setNumOfAvailableBike(int numOfAvailableBike) {
        this.numOfAvailableBike = numOfAvailableBike;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
