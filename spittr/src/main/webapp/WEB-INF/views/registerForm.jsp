<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spittr</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" >
</head>
<body>
	<h1>Register</h1>
	
	<form:form method="post">
		First Name: <input type="text" name="firstName" /><br /> 
		Last Name: <input type="text" name="lastName" /><br />
		UserName: <input type="text" name="username" /><br />
		Password: <input type="password" name="password" /><br />
		
		<input type="submit" value="Register" />
	</form:form>
</body>
</html>