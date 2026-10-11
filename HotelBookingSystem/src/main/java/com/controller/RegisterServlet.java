package com.controller;

import com.dao.UserDAO;
import com.model.UserModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Handles registration.
 *
 * IMPORTANT (role auto-detection):
 * The registration form never shows a "role" dropdown. Instead there is a single
 * optional field, "adminCode". If the value the user types into that field matches
 * the secret ADMIN_ACCESS_CODE below, the account is created with role = ADMIN.
 * Any other value (including a blank field) results in role = USER.
 *
 * Change ADMIN_ACCESS_CODE to something private, and share it only with staff
 * who should get admin accounts.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String ADMIN_ACCESS_CODE = "HOTELSTAY-ADMIN-2024";

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String adminCode = req.getParameter("adminCode"); // optional, given value decides the role

        // ---- basic validation ----
        if (isBlank(fullName) || isBlank(email) || isBlank(password)) {
            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "Passwords do not match.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        if (userDAO.emailExists(email)) {
            req.setAttribute("error", "An account with this email already exists.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        // ---- role is derived from the given value, never chosen directly ----
        String role = (adminCode != null && adminCode.trim().equals(ADMIN_ACCESS_CODE))
                ? "ADMIN"
                : "USER";

        UserModel user = new UserModel();
        user.setFullName(fullName.trim());
        user.setEmail(email.trim().toLowerCase());
        user.setPassword(password);
        user.setRole(role);

        boolean created = userDAO.registerUser(user);
        if (created) {
            req.setAttribute("success", "Registration successful! Please log in.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Something went wrong. Please try again.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
