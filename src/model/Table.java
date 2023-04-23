package model;

public class Table {

    private final int tableNumber;
    private int numOfSeats;
    private boolean isSmokingArea;
    private boolean isBooked;

    public Table(int tableNumber, int numOfSeats, boolean isSmokingArea, boolean isBooked) {
        this.tableNumber = tableNumber;
        setNumOfSeats(numOfSeats);
        setSmokingArea(isSmokingArea);
        setBooked(isBooked);
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public boolean isSmokingArea() {
        return isSmokingArea;
    }

    public void setSmokingArea(boolean smokingArea) {
        isSmokingArea = smokingArea;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

}
