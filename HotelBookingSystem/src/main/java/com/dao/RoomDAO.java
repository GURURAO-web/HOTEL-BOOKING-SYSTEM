package com.dao;

import com.model.RoomModel;
import com.utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public List<RoomModel> getRoomsByHotel(int hotelId) {
        List<RoomModel> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE hotel_id = ?";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hotelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public RoomModel getRoomById(int roomId) {
        String sql = "SELECT r.*, h.name AS hotel_name FROM rooms r " +
                     "JOIN hotels h ON r.hotel_id = h.hotel_id WHERE r.room_id = ?";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RoomModel r = mapRow(rs);
                    r.setHotelName(rs.getString("hotel_name"));
                    return r;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addRoom(RoomModel r) {
        String sql = "INSERT INTO rooms (hotel_id, room_type, price_per_night, capacity, total_rooms, available_rooms) VALUES (?,?,?,?,?,?)";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getHotelId());
            ps.setString(2, r.getRoomType());
            ps.setDouble(3, r.getPricePerNight());
            ps.setInt(4, r.getCapacity());
            ps.setInt(5, r.getTotalRooms());
            ps.setInt(6, r.getAvailableRooms());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRoom(RoomModel r) {
        String sql = "UPDATE rooms SET room_type=?, price_per_night=?, capacity=?, total_rooms=?, available_rooms=? WHERE room_id=?";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getRoomType());
            ps.setDouble(2, r.getPricePerNight());
            ps.setInt(3, r.getCapacity());
            ps.setInt(4, r.getTotalRooms());
            ps.setInt(5, r.getAvailableRooms());
            ps.setInt(6, r.getRoomId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRoom(int roomId) {
        String sql = "DELETE FROM rooms WHERE room_id = ?";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Decrements available_rooms by 1, only if a room is actually available (avoids overbooking). */
    public boolean decrementAvailability(int roomId, Connection con) throws SQLException {
        String sql = "UPDATE rooms SET available_rooms = available_rooms - 1 WHERE room_id = ? AND available_rooms > 0";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean incrementAvailability(int roomId, Connection con) throws SQLException {
        String sql = "UPDATE rooms SET available_rooms = available_rooms + 1 WHERE room_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            return ps.executeUpdate() > 0;
        }
    }

    private RoomModel mapRow(ResultSet rs) throws SQLException {
        RoomModel r = new RoomModel();
        r.setRoomId(rs.getInt("room_id"));
        r.setHotelId(rs.getInt("hotel_id"));
        r.setRoomType(rs.getString("room_type"));
        r.setPricePerNight(rs.getDouble("price_per_night"));
        r.setCapacity(rs.getInt("capacity"));
        r.setTotalRooms(rs.getInt("total_rooms"));
        r.setAvailableRooms(rs.getInt("available_rooms"));
        return r;
    }
}
