<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Your Profile</h1>
<p>
	Username : <c:out value="${spitter.username}" /> <br />
	FirstName : <c:out value="${spitter.firstName}" /> <br />
	LastName : <c:out value="${spitter.lastName}" /> <br />
	Email : <c:out value="${spitter.email}" />
</p>
