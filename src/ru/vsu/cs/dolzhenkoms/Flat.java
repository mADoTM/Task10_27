package ru.vsu.cs.dolzhenkoms;

public class Flat {
    private String district;
    private int countOfRooms;
    private int flatSquare;
    private int kitchenSquare;
    private int cost;

    public Flat() { }

    public Flat(String district, int countOfRooms, int flatSquare, int kitchenSquare, int cost) {
        this.district = district;
        this.countOfRooms = countOfRooms;
        this.flatSquare = flatSquare;

        if(kitchenSquare > flatSquare) {
            this.kitchenSquare = flatSquare;
        } else {
            this.kitchenSquare = kitchenSquare;
        }

        this.cost = cost;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getCountOfRooms() {
        return countOfRooms;
    }

    public void setCountOfRooms(int countOfRooms) {
        this.countOfRooms = countOfRooms;
    }

    public int getFlatSquare() {
        return flatSquare;
    }

    public void setFlatSquare(int flatSquare) {
        this.flatSquare = flatSquare;
    }

    public int getKitchenSquare() {
        return kitchenSquare;
    }

    public void setKitchenSquare(int kitchenSquare) {
        if(kitchenSquare > flatSquare) {
            this.kitchenSquare = flatSquare;
        } else {
            this.kitchenSquare = kitchenSquare;
        }
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "District: " + district + ". Count of rooms - " + countOfRooms + ". Flat square - " + flatSquare + ". Kitchen square - " + kitchenSquare + ". Cost - " + cost;
    }
}
