package com.model;

import java.io.Serializable;

public class RoomModel implements Serializable {
    private int roomId;
    private int hotelId;
    private String roomType;
    private double pricePerNight;
    private int capacity;
    private int totalRooms;
    private int availableRooms;

    // populated by joins, optional
    private String hotelName;

    public RoomModel() {}

    public RoomModel(int roomId, int hotelId, String roomType, double pricePerNight,
                      int capacity, int totalRooms, int availableRooms) {
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
    }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getTotalRooms() { return totalRooms; }
    public void setTotalRooms(int totalRooms) { this.totalRooms = totalRooms; }

    public int getAvailableRooms() { return availableRooms; }
    public void setAvailableRooms(int availableRooms) { this.availableRooms = availableRooms; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
}
