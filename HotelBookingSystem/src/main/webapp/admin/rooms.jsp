<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dao.HotelDAO" %>
<%@ page import="com.dao.RoomDAO" %>
<%@ page import="com.model.HotelModel" %>
<%@ page import="com.model.RoomModel" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ include file="/WEB-INF/jspf/admin-header.jspf" %>
<%
  List<HotelModel> hotels = new HotelDAO().getAllHotels();
  RoomDAO roomDAO = new RoomDAO();
  List<RoomModel> allRooms = new ArrayList<>();
  for (HotelModel h : hotels) {
    allRooms.addAll(roomDAO.getRoomsByHotel(h.getHotelId()));
  }
  request.setAttribute("hotels", hotels);
  request.setAttribute("allRooms", allRooms);
%>
<h1>Manage Rooms</h1>

<div class="card">
  <h3>Add New Room</h3>
  <form action="${pageContext.request.contextPath}/rooms" method="post">
    <input type="hidden" name="action" value="add">
    <div class="grid">
      <div class="field">
        <label>Hotel</label>
        <select name="hotelId" required>
          <c:forEach var="h" items="${hotels}">
            <option value="${h.hotelId}">${h.name} (${h.city})</option>
          </c:forEach>
        </select>
      </div>
      <div class="field"><label>Room Type</label><input type="text" name="roomType" placeholder="Deluxe Room" required></div>
      <div class="field"><label>Price / night</label><input type="number" step="0.01" name="pricePerNight" required></div>
      <div class="field"><label>Capacity</label><input type="number" name="capacity" value="2" required></div>
      <div class="field"><label>Total Rooms</label><input type="number" name="totalRooms" value="5" required></div>
      <div class="field"><label>Available Rooms</label><input type="number" name="availableRooms" value="5" required></div>
    </div>
    <button class="btn" type="submit">Add Room</button>
  </form>
</div>

<h3>Existing Rooms</h3>
<table>
  <thead><tr><th>ID</th><th>Hotel</th><th>Type</th><th>Price</th><th>Avail/Total</th><th>Actions</th></tr></thead>
  <tbody>
    <c:forEach var="hotel" items="${hotels}">
      <c:forEach var="r" items="${allRooms}">
        <c:if test="${r.hotelId == hotel.hotelId}">
          <tr>
            <td>${r.roomId}</td>
            <td>${hotel.name}</td>
            <td>${r.roomType}</td>
            <td>&#8377;${r.pricePerNight}</td>
            <td>${r.availableRooms} / ${r.totalRooms}</td>
            <td>
              <a class="btn small danger" href="${pageContext.request.contextPath}/rooms?action=delete&id=${r.roomId}"
                 onclick="return confirm('Delete this room type?');">Delete</a>
            </td>
          </tr>
        </c:if>
      </c:forEach>
    </c:forEach>
  </tbody>
</table>
<%@ include file="/WEB-INF/jspf/admin-footer.jspf" %>
