package com.dao;

import com.model.UserModel;
import com.utility.DBConnection;
import com.utility.PasswordUtil;

import java.sql.*;

public class UserDAO {

    /** Registers a new user. Role is decided by the caller (servlet), not the user. */
    public boolean registerUser(UserModel user) {
        String sql = "INSERT INTO users (full_name, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, PasswordUtil.hashPassword(user.getPassword()));
            ps.setString(4, user.getRole());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExists(String email) {
        String sql = "SELECT user_id FROM users WHERE email = ?";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Returns the user if email/password match, otherwise null. */
    public UserModel authenticate(String email, String plainPassword) {
        UserModel user = findByEmail(email);
        if (user != null && PasswordUtil.verifyPassword(plainPassword, user.getPassword())) {
            return user;
        }
        return null;
    }

    public UserModel findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserModel findById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UserModel mapRow(ResultSet rs) throws SQLException {
        UserModel u = new UserModel();
        u.setUserId(rs.getInt("user_id"));
        u.setFullName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setRole(rs.getString("role"));
        u.setCreatedAt(rs.getTimestamp("created_at"));
        return u;
    }
}
