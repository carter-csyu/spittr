<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<s:url value="/spittles" var="spittlesUrl" htmlEscape="true">
	<s:param name="max" value="60" />
	<s:param name="count" value="20" />
</s:url>
<s:url value="spitter/register" var="register" htmlEscape="true" />
<s:url value="spitter/{username}" var="chundol42" htmlEscape="true">
	<s:param name="username" value="chundol42" />
</s:url>

<h1><s:message code="spittr.welcome" /></h1>
<a href="${spittlesUrl}">Spittles</a> |
<a href="${register}">Register</a> <br />
