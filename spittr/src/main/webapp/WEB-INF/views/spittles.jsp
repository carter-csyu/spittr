<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div class="listTitle">
	<h1>Recent Spittles</h1>
	
	<sec:authorize access="isAuthenticated()">
	<div class="spittleForm">
		<s:url value="/spittles" var="spittle_url" />
		<sf:form modelAttribute="spittle" method="post"
			action="${spittle_url}">
			<sf:hidden path="latitude" />
			<sf:hidden path="longitude" />
			<sf:textarea path="message" cols="50" rows="5" />
			<br />
			<input type="submit" value="Add" />
		</sf:form>
	</div>
	</sec:authorize>

	<div class="spittleList">
		<ul>
			<c:forEach items="${spittleList}" var="spittle">
				<li id="spittle_<c:out value="spittle.id" />">
					<div class="spittleMessage">
						<c:out value="${spittle.message}" />
					</div>
					<div>
						<span class="spittleTime"><c:out value="${spittle.time}" /></span>
						<span class="spittleLocation">
							(<c:out value="${spittle.latitude}" />, <c:out value="${spittle.longitude}" />)
						</span>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
