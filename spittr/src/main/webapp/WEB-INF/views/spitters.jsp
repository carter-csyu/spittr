<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Your Profile</h1>

<ul>
  <c:forEach items="${spitterList}" var="spitter">
  <li>
    <c:out value="${spitter.username}" /> / 
    <c:out value="${spitter.firstName}" /> / 
    <c:out value="${spitter.lastName}" /> / 
    <c:out value="${spitter.email}" />
  </li>
  </c:forEach>
</ul>
