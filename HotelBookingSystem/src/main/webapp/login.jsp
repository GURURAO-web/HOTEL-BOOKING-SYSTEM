<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
  <form class="form-box" action="${pageContext.request.contextPath}/login" method="post">
    <h2>Welcome Back</h2>

    <c:if test="${not empty error}">
      <div class="alert alert-error">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
      <div class="alert alert-success">${success}</div>
    </c:if>

    <div class="field">
      <label for="email">Email</label>
      <input type="email" id="email" name="email" required>
    </div>

    <div class="field">
      <label for="password">Password</label>
      <input type="password" id="password" name="password" required>
    </div>

    <button class="btn" type="submit" style="width:100%;">Login</button>

    <p style="text-align:center;margin-top:14px;font-size:14px;">
      New here? <a href="${pageContext.request.contextPath}/register.jsp" style="color:var(--primary);font-weight:600;">Create an account</a>
    </p>
  </form>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
