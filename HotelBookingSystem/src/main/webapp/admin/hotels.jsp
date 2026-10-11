<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dao.HotelDAO" %>
<%@ page import="com.model.HotelModel" %>
<%@ page import="java.util.List" %>
<%@ include file="/WEB-INF/jspf/admin-header.jspf" %>
<%
  List<HotelModel> hotels = new HotelDAO().getAllHotels();
  request.setAttribute("hotels", hotels);
%>
<h1>Manage Hotels</h1>

<div class="card">
  <h3>Add New Hotel</h3>
  <form action="${pageContext.request.contextPath}/hotels" method="post">
    <input type="hidden" name="action" value="add">
    <div class="grid">
      <div class="field"><label>Name</label><input type="text" name="name" required></div>
      <div class="field"><label>City</label><input type="text" name="city" required></div>
      <div class="field"><label>Address</label><input type="text" name="address"></div>
      <div class="field"><label>Image URL</label><input type="text" name="imageUrl"></div>
      <div class="field"><label>Rating (0-5)</label><input type="number" step="0.1" min="0" max="5" name="rating" value="4.0"></div>
    </div>
    <div class="field"><label>Description</label><textarea name="description" rows="3"></textarea></div>
    <button class="btn" type="submit">Add Hotel</button>
  </form>
</div>

<h3>Existing Hotels</h3>
<table>
  <thead><tr><th>ID</th><th>Name</th><th>City</th><th>Rating</th><th>Actions</th></tr></thead>
  <tbody>
    <c:forEach var="h" items="${hotels}">
      <tr>
        <td>${h.hotelId}</td>
        <td>${h.name}</td>
        <td>${h.city}</td>
        <td>&#9733; ${h.rating}</td>
        <td>
          <a class="btn small" href="${pageContext.request.contextPath}/hotels?action=view&id=${h.hotelId}">View</a>
          <a class="btn small danger" href="${pageContext.request.contextPath}/hotels?action=delete&id=${h.hotelId}"
             onclick="return confirm('Delete this hotel and all its rooms?');">Delete</a>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
<%@ include file="/WEB-INF/jspf/admin-footer.jspf" %>
