package com.dao;

import com.model.HotelModel;
import com.utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    public List<HotelModel> getAllHotels() {
        List<HotelModel> list = new ArrayList<>();
        String sql = "SELECT * FROM hotels ORDER BY hotel_id DESC";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public HotelModel getHotelById(int hotelId) {
        String sql = "SELECT * FROM hotels WHERE hotel_id = ?";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hotelId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addHotel(HotelModel h) {
        String sql = "INSERT INTO hotels (name, city, address, description, image_url, rating) VALUES (?,?,?,?,?,?)";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, h.getName());
            ps.setString(2, h.getCity());
            ps.setString(3, h.getAddress());
            ps.setString(4, h.getDescription());
            ps.setString(5, h.getImageUrl());
            ps.setDouble(6, h.getRating());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateHotel(HotelModel h) {
        String sql = "UPDATE hotels SET name=?, city=?, address=?, description=?, image_url=?, rating=? WHERE hotel_id=?";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, h.getName());
            ps.setString(2, h.getCity());
            ps.setString(3, h.getAddress());
            ps.setString(4, h.getDescription());
            ps.setString(5, h.getImageUrl());
            ps.setDouble(6, h.getRating());
            ps.setInt(7, h.getHotelId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHotel(int hotelId) {
        String sql = "DELETE FROM hotels WHERE hotel_id = ?";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hotelId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private HotelModel mapRow(ResultSet rs) throws SQLException {
        HotelModel h = new HotelModel();
        h.setHotelId(rs.getInt("hotel_id"));
        h.setName(rs.getString("name"));
        h.setCity(rs.getString("city"));
        h.setAddress(rs.getString("address"));
        h.setDescription(rs.getString("description"));
        h.setImageUrl(rs.getString("image_url"));
        h.setRating(rs.getDouble("rating"));
        return h;
    }
}
