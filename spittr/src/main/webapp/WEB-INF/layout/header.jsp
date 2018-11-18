<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header>
	<a href="<s:url value="/" />">
		<img src="<s:url value="/resources/images/spitter_logo_50.png" />" />
	</a>
	<br/>
  <sec:authorize access="isAuthenticated()">
    Hello <sec:authentication property="principal.Username" scope="request"  />!
    <a href="<s:url value="/logout" />">Logout</a>
  </sec:authorize>
  <sec:authorize access="!isAuthenticated()">
    <a href="<s:url value="/login" />">Login</a>
  </sec:authorize>
</header>
