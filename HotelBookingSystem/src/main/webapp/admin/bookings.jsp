<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jspf/admin-header.jspf" %>
<%
  if (request.getAttribute("bookings") == null) {
    request.getRequestDispatcher("/admin/bookings").forward(request, response);
    return;
  }
%>
<h1>All Bookings</h1>
<table>
  <thead>
    <tr>
      <th>ID</th><th>Guest</th><th>Email</th><th>Hotel</th><th>Room</th>
      <th>Check-in</th><th>Check-out</th><th>Total</th><th>Status</th><th>Action</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="b" items="${bookings}">
      <tr>
        <td>${b.bookingId}</td>
        <td>${b.userFullName}</td>
        <td>${b.userEmail}</td>
        <td>${b.hotelName}</td>
        <td>${b.roomType}</td>
        <td>${b.checkIn}</td>
        <td>${b.checkOut}</td>
        <td>&#8377;${b.totalPrice}</td>
        <td class="status-${b.status}">${b.status}</td>
        <td>
          <c:if test="${b.status eq 'CONFIRMED'}">
            <a class="btn small danger" href="${pageContext.request.contextPath}/admin/bookings?action=cancel&id=${b.bookingId}&roomId=${b.roomId}"
               onclick="return confirm('Cancel this booking?');">Cancel</a>
          </c:if>
        </td>
      </tr>
    </c:forEach>
    <c:if test="${empty bookings}">
      <tr><td colspan="10" style="text-align:center;color:var(--muted);">No bookings yet.</td></tr>
    </c:if>
  </tbody>
</table>
<%@ include file="/WEB-INF/jspf/admin-footer.jspf" %>
