<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dao.RoomDAO" %>
<%@ page import="com.model.RoomModel" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%
  int roomId = Integer.parseInt(request.getParameter("roomId"));
  RoomModel room = new RoomDAO().getRoomById(roomId);
  request.setAttribute("room", room);
%>
<div class="container">
  <c:if test="${empty room}">
    <div class="alert alert-error">Room not found.</div>
  </c:if>

  <c:if test="${not empty room}">
    <form class="form-box" action="${pageContext.request.contextPath}/bookings" method="post">
      <h2>Book: ${room.roomType}</h2>
      <p style="text-align:center;color:var(--muted);margin-top:-10px;">${room.hotelName}</p>

      <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
      </c:if>

      <p style="font-size:18px;font-weight:700;color:var(--primary);text-align:center;">
        &#8377;${room.pricePerNight} / night
      </p>

      <input type="hidden" name="roomId" value="${room.roomId}">

      <div class="field">
        <label for="checkIn">Check-in</label>
        <input type="date" id="checkIn" name="checkIn" required>
      </div>

      <div class="field">
        <label for="checkOut">Check-out</label>
        <input type="date" id="checkOut" name="checkOut" required>
      </div>

      <button class="btn accent" type="submit" style="width:100%;">Confirm Booking</button>
    </form>
  </c:if>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
