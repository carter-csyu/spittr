<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spittle</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" >
</head>
<body>
	<div class="spittleView">
		<div class="spittleMessage"><c:out value="${spittle.message}" /></div>
		<div class="spittleTime"><c:out value="${spittle.time}" /></div>
	</div>
</body>
</html>