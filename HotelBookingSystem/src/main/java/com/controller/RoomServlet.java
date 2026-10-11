package com.controller;

import com.dao.RoomDAO;
import com.model.RoomModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Admin-facing room management (used from admin/rooms.jsp). */
@WebServlet("/rooms")
public class RoomServlet extends HttpServlet {

    private final RoomDAO roomDAO = new RoomDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String idParam = req.getParameter("id");

        if ("delete".equals(action) && idParam != null) {
            roomDAO.deleteRoom(Integer.parseInt(idParam));
        }
        resp.sendRedirect(req.getContextPath() + "/admin/rooms.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        RoomModel r = new RoomModel();
        r.setHotelId(Integer.parseInt(req.getParameter("hotelId")));
        r.setRoomType(req.getParameter("roomType"));
        r.setPricePerNight(Double.parseDouble(req.getParameter("pricePerNight")));
        r.setCapacity(Integer.parseInt(req.getParameter("capacity")));
        r.setTotalRooms(Integer.parseInt(req.getParameter("totalRooms")));
        r.setAvailableRooms(Integer.parseInt(req.getParameter("availableRooms")));

        if ("update".equals(action)) {
            r.setRoomId(Integer.parseInt(req.getParameter("roomId")));
            roomDAO.updateRoom(r);
        } else {
            roomDAO.addRoom(r);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/rooms.jsp");
    }
}
