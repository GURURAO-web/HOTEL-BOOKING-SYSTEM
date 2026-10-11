<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dao.HotelDAO" %>
<%@ page import="com.dao.BookingDAO" %>
<%@ include file="/WEB-INF/jspf/admin-header.jspf" %>
<%
  int hotelCount = new HotelDAO().getAllHotels().size();
  int bookingCount = new BookingDAO().getAllBookings().size();
  request.setAttribute("hotelCount", hotelCount);
  request.setAttribute("bookingCount", bookingCount);
%>
<h1>Admin Dashboard</h1>
<p style="color:var(--muted);">Welcome back, ${sessionScope.fullName}. You're signed in with admin access.</p>

<div class="grid">
  <div class="card">
    <h3>Total Hotels</h3>
    <p style="font-size:32px;font-weight:700;color:var(--primary);">${hotelCount}</p>
    <a class="btn small" href="${pageContext.request.contextPath}/admin/hotels.jsp">Manage Hotels</a>
  </div>
  <div class="card">
    <h3>Total Bookings</h3>
    <p style="font-size:32px;font-weight:700;color:var(--primary);">${bookingCount}</p>
    <a class="btn small" href="${pageContext.request.contextPath}/admin/bookings">Manage Bookings</a>
  </div>
  <div class="card">
    <h3>Rooms</h3>
    <p style="color:var(--muted);">Add or update room types and pricing per hotel.</p>
    <a class="btn small" href="${pageContext.request.contextPath}/admin/rooms.jsp">Manage Rooms</a>
  </div>
</div>
<%@ include file="/WEB-INF/jspf/admin-footer.jspf" %>
