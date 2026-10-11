<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
  <c:if test="${empty hotel}">
    <div class="alert alert-error">Hotel not found.</div>
  </c:if>
  <c:if test="${not empty hotel}">
    <div class="card">
      <img src="${hotel.imageUrl}" alt="${hotel.name}" style="width:100%;max-height:320px;object-fit:cover;border-radius:10px;">
      <h1>${hotel.name} <span class="badge">&#9733; ${hotel.rating}</span></h1>
      <p class="city">${hotel.city} &mdash; ${hotel.address}</p>
      <p>${hotel.description}</p>
    </div>

    <h2>Rooms</h2>
    <div class="grid">
      <c:forEach var="room" items="${rooms}">
        <div class="card">
          <h3>${room.roomType}</h3>
          <p>Capacity: ${room.capacity} guests</p>
          <p>Available: ${room.availableRooms} / ${room.totalRooms}</p>
          <p style="font-size:20px;font-weight:700;color:var(--primary);">&#8377;${room.pricePerNight} <span style="font-size:13px;color:var(--muted);font-weight:400;">/ night</span></p>
          <c:choose>
            <c:when test="${room.availableRooms > 0}">
              <a class="btn accent" href="${pageContext.request.contextPath}/book-room.jsp?roomId=${room.roomId}">Book Now</a>
            </c:when>
            <c:otherwise>
              <button class="btn" disabled style="opacity:0.5;cursor:not-allowed;">Sold Out</button>
            </c:otherwise>
          </c:choose>
        </div>
      </c:forEach>
    </div>
  </c:if>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
