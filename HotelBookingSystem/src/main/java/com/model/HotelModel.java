package com.model;

import java.io.Serializable;

public class HotelModel implements Serializable {
    private int hotelId;
    private String name;
    private String city;
    private String address;
    private String description;
    private String imageUrl;
    private double rating;

    public HotelModel() {}

    public HotelModel(int hotelId, String name, String city, String address,
                       String description, String imageUrl, double rating) {
        this.hotelId = hotelId;
        this.name = name;
        this.city = city;
        this.address = address;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
