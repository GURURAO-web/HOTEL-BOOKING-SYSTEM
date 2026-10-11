package com.controller;

import com.dao.BookingDAO;
import com.model.BookingModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/** ADMIN: view every booking across all users, cancel/confirm any booking. */
@WebServlet("/admin/bookings")
public class AdminBookingServlet extends HttpServlet {

    private final BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String idParam = req.getParameter("id");

        if ("cancel".equals(action) && idParam != null) {
            int bookingId = Integer.parseInt(idParam);
            int roomId = Integer.parseInt(req.getParameter("roomId"));
            bookingDAO.cancelBooking(bookingId, roomId);
        }

        List<BookingModel> all = bookingDAO.getAllBookings();
        req.setAttribute("bookings", all);
        req.getRequestDispatcher("/admin/bookings.jsp").forward(req, resp);
    }
}
