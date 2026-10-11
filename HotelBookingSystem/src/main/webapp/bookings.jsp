<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%
  if (request.getAttribute("bookings") == null) {
    request.getRequestDispatcher("/bookings").forward(request, response);
    return;
  }
%>
<div class="container">
  <h1>My Bookings</h1>
  <table>
    <thead>
      <tr>
        <th>Hotel</th><th>Room</th><th>Check-in</th><th>Check-out</th>
        <th>Total</th><th>Status</th><th>Action</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="b" items="${bookings}">
        <tr>
          <td>${b.hotelName}</td>
          <td>${b.roomType}</td>
          <td>${b.checkIn}</td>
          <td>${b.checkOut}</td>
          <td>&#8377;${b.totalPrice}</td>
          <td class="status-${b.status}">${b.status}</td>
          <td>
            <c:if test="${b.status eq 'CONFIRMED'}">
              <a class="btn small danger" href="${pageContext.request.contextPath}/bookings?action=cancel&id=${b.bookingId}&roomId=${b.roomId}"
                 onclick="return confirm('Cancel this booking?');">Cancel</a>
            </c:if>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty bookings}">
        <tr><td colspan="7" style="text-align:center;color:var(--muted);">No bookings yet. <a href="${pageContext.request.contextPath}/home.jsp">Browse hotels</a></td></tr>
      </c:if>
    </tbody>
  </table>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
