<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spittr</title>
</head>
<body>
	<h1>Your Profile</h1>
	<p>
		<c:out value="${spitter.username}" /> <br />
		<c:out value="${spitter.firstName}" /> <br />
		<c:out value="${spitter.lastName}" /> 
	</p>
</body>
</html>