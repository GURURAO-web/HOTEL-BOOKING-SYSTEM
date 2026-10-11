<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%
  if (request.getAttribute("hotels") == null) {
    // page opened directly (not via /hotels servlet) -> fetch via servlet forward
    request.getRequestDispatcher("/hotels").forward(request, response);
    return;
  }
%>
<div class="hero">
  <h1>Find your next stay</h1>
  <p>Handpicked hotels across India, ready to book in minutes.</p>
</div>
<div class="container">
  <h2 style="margin-top:0;">Available Hotels</h2>
  <div class="grid">
    <c:forEach var="hotel" items="${hotels}">
      <div class="card hotel-card">
        <img src="${hotel.imageUrl}" alt="${hotel.name}">
        <span class="badge">&#9733; ${hotel.rating}</span>
        <h3>${hotel.name}</h3>
        <div class="city">${hotel.city}</div>
        <p>${hotel.description}</p>
        <a class="btn" href="${pageContext.request.contextPath}/hotels?action=view&id=${hotel.hotelId}">View Rooms</a>
      </div>
    </c:forEach>
    <c:if test="${empty hotels}">
      <p>No hotels available right now. Please check back later.</p>
    </c:if>
  </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
