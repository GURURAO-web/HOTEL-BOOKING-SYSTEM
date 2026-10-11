package com.controller;

import com.dao.HotelDAO;
import com.dao.RoomDAO;
import com.model.HotelModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Public: lists hotels (home.jsp) and shows a single hotel's details + its rooms
 * (hotel-details.jsp).  Also backs the admin hotel-management screen for
 * create/update/delete when accessed with an action parameter.
 */
@WebServlet("/hotels")
public class HotelServlet extends HttpServlet {

    private final HotelDAO hotelDAO = new HotelDAO();
    private final RoomDAO roomDAO = new RoomDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String idParam = req.getParameter("id");

        if ("view".equals(action) && idParam != null) {
            int hotelId = Integer.parseInt(idParam);
            HotelModel hotel = hotelDAO.getHotelById(hotelId);
            req.setAttribute("hotel", hotel);
            req.setAttribute("rooms", roomDAO.getRoomsByHotel(hotelId));
            req.getRequestDispatcher("hotel-details.jsp").forward(req, resp);
            return;
        }

        if ("delete".equals(action) && idParam != null) {
            hotelDAO.deleteHotel(Integer.parseInt(idParam));
            resp.sendRedirect(req.getContextPath() + "/admin/hotels.jsp");
            return;
        }

        List<HotelModel> hotels = hotelDAO.getAllHotels();
        req.setAttribute("hotels", hotels);
        req.getRequestDispatcher("home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        HotelModel h = new HotelModel();
        h.setName(req.getParameter("name"));
        h.setCity(req.getParameter("city"));
        h.setAddress(req.getParameter("address"));
        h.setDescription(req.getParameter("description"));
        h.setImageUrl(req.getParameter("imageUrl"));
        try {
            h.setRating(Double.parseDouble(req.getParameter("rating")));
        } catch (Exception e) {
            h.setRating(0.0);
        }

        if ("update".equals(action)) {
            h.setHotelId(Integer.parseInt(req.getParameter("hotelId")));
            hotelDAO.updateHotel(h);
        } else {
            hotelDAO.addHotel(h);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/hotels.jsp");
    }
}
