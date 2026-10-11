package com.dao;

import com.model.BookingModel;
import com.utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    private final RoomDAO roomDAO = new RoomDAO();

    /**
     * Creates a booking and decrements room availability atomically.
     * Returns true only if a room slot was actually free.
     */
    public boolean createBooking(BookingModel b) {
        String sql = "INSERT INTO bookings (user_id, room_id, check_in, check_out, total_price, status) VALUES (?,?,?,?,?,?)";
        Connection con = null;
        try {
            con = DBConnection.getDBConnection();
            con.setAutoCommit(false);

            boolean roomTaken = roomDAO.decrementAvailability(b.getRoomId(), con);
            if (!roomTaken) {
                con.rollback();
                return false; // no rooms available
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, b.getUserId());
                ps.setInt(2, b.getRoomId());
                ps.setDate(3, b.getCheckIn());
                ps.setDate(4, b.getCheckOut());
                ps.setDouble(5, b.getTotalPrice());
                ps.setString(6, "CONFIRMED");
                ps.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try { if (con != null) con.rollback(); } catch (SQLException ignored) {}
            return false;
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (SQLException ignored) {}
        }
    }

    public List<BookingModel> getBookingsByUser(int userId) {
        List<BookingModel> list = new ArrayList<>();
        String sql = "SELECT b.*, r.room_type, h.name AS hotel_name FROM bookings b " +
                     "JOIN rooms r ON b.room_id = r.room_id " +
                     "JOIN hotels h ON r.hotel_id = h.hotel_id " +
                     "WHERE b.user_id = ? ORDER BY b.booked_at DESC";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRowWithJoins(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<BookingModel> getAllBookings() {
        List<BookingModel> list = new ArrayList<>();
        String sql = "SELECT b.*, u.full_name AS user_full_name, u.email AS user_email, " +
                     "r.room_type, h.name AS hotel_name FROM bookings b " +
                     "JOIN users u ON b.user_id = u.user_id " +
                     "JOIN rooms r ON b.room_id = r.room_id " +
                     "JOIN hotels h ON r.hotel_id = h.hotel_id " +
                     "ORDER BY b.booked_at DESC";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BookingModel b = mapRowWithJoins(rs);
                b.setUserFullName(rs.getString("user_full_name"));
                b.setUserEmail(rs.getString("user_email"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Cancels a booking and frees up the room slot again. */
    public boolean cancelBooking(int bookingId, int roomId) {
        Connection con = null;
        try {
            con = DBConnection.getDBConnection();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE bookings SET status = 'CANCELLED' WHERE booking_id = ?")) {
                ps.setInt(1, bookingId);
                ps.executeUpdate();
            }
            roomDAO.incrementAvailability(roomId, con);

            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try { if (con != null) con.rollback(); } catch (SQLException ignored) {}
            return false;
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (SQLException ignored) {}
        }
    }

    public boolean updateStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, bookingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private BookingModel mapRowWithJoins(ResultSet rs) throws SQLException {
        BookingModel b = new BookingModel();
        b.setBookingId(rs.getInt("booking_id"));
        b.setUserId(rs.getInt("user_id"));
        b.setRoomId(rs.getInt("room_id"));
        b.setCheckIn(rs.getDate("check_in"));
        b.setCheckOut(rs.getDate("check_out"));
        b.setTotalPrice(rs.getDouble("total_price"));
        b.setStatus(rs.getString("status"));
        b.setBookedAt(rs.getTimestamp("booked_at"));
        b.setRoomType(rs.getString("room_type"));
        b.setHotelName(rs.getString("hotel_name"));
        return b;
    }
}
