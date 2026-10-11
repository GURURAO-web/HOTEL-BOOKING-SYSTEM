
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container">

  <form class="form-box" action="${pageContext.request.contextPath}/register" method="post">

    <h2>Create an Account</h2>

    <c:if test="${not empty error}">

      <div class="alert alert-error">${error}</div>

    </c:if>

    <div class="field">

      <label for="fullName">Full Name</label>

      <input type="text" id="fullName" name="fullName" required>

    </div>

    <div class="field">

      <label for="email">Email</label>

      <input type="email" id="email" name="email" required>

    </div>

    <div class="field">

      <label for="password">Password</label>

      <input type="password" id="password" name="password" required minlength="6">

    </div>

    <div class="field">

      <label for="confirmPassword">Confirm Password</label>

      <input type="password" id="confirmPassword" name="confirmPassword" required minlength="6">

    </div>

    <!--
      No role dropdown here on purpose. Whether this account becomes a USER or an
      ADMIN is decided entirely by the value typed below (RegisterServlet checks it
      against a secret access code). Leave it empty for a normal user account.
    -->

    <div class="field">

      <label for="adminCode">Staff Access Code (optional)</label>

      <input type="password" id="adminCode" name="adminCode" placeholder="Leave blank if you're a guest">

      <small>Only hotel staff need to fill this in &mdash; it silently grants admin access when correct.</small>

    </div>

    <button class="btn" type="submit" style="width:100%;">Register</button>

    <p style="text-align:center;margin-top:14px;font-size:14px;">

      Already have an account? <a href="${pageContext.request.contextPath}/login.jsp" style="color:var(--primary);font-weight:600;">Login</a> 

    </p>

  </form>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
