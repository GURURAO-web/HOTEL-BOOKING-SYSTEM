package com.controller;

import com.dao.BookingDAO;
import com.dao.RoomDAO;
import com.model.BookingModel;
import com.model.RoomModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/** Logged-in USER: view own bookings, create a new booking, cancel a booking. */
@WebServlet("/bookings")
public class BookingServlet extends HttpServlet {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final RoomDAO roomDAO = new RoomDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        int userId = (int) session.getAttribute("userId");

        String action = req.getParameter("action");
        String idParam = req.getParameter("id");

        if ("cancel".equals(action) && idParam != null) {
            int bookingId = Integer.parseInt(idParam);
            int roomId = Integer.parseInt(req.getParameter("roomId"));
            bookingDAO.cancelBooking(bookingId, roomId);
            resp.sendRedirect(req.getContextPath() + "/bookings.jsp");
            return;
        }

        List<BookingModel> myBookings = bookingDAO.getBookingsByUser(userId);
        req.setAttribute("bookings", myBookings);
        req.getRequestDispatcher("bookings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        int userId = (int) session.getAttribute("userId");

        int roomId = Integer.parseInt(req.getParameter("roomId"));
        LocalDate checkIn = LocalDate.parse(req.getParameter("checkIn"));
        LocalDate checkOut = LocalDate.parse(req.getParameter("checkOut"));

        if (!checkOut.isAfter(checkIn)) {
            req.setAttribute("error", "Check-out date must be after check-in date.");
            req.getRequestDispatcher("book-room.jsp").forward(req, resp);
            return;
        }

        RoomModel room = roomDAO.getRoomById(roomId);
        if (room == null || room.getAvailableRooms() <= 0) {
            req.setAttribute("error", "Sorry, this room is no longer available.");
            req.getRequestDispatcher("book-room.jsp").forward(req, resp);
            return;
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        double totalPrice = nights * room.getPricePerNight();

        BookingModel booking = new BookingModel();
        booking.setUserId(userId);
        booking.setRoomId(roomId);
        booking.setCheckIn(Date.valueOf(checkIn));
        booking.setCheckOut(Date.valueOf(checkOut));
        booking.setTotalPrice(totalPrice);

        boolean success = bookingDAO.createBooking(booking);

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/bookings.jsp");
        } else {
            req.setAttribute("error", "Booking failed. The room may have just been taken.");
            req.getRequestDispatcher("book-room.jsp").forward(req, resp);
        }
    }
}
