<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="menu.edit" bundle="${lc}" var="edit" />
<fmt:message key="menu.logout" bundle="${lc}" var="logout" />
<fmt:message key="main_menu.login" bundle="${lc}" var="login" />
<fmt:message key="main_menu.registration" bundle="${lc}"
	var="registration" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<c:choose>
		<c:when test="${not empty sessionScope.current_user}">
			<ul id="menu">
				<li><span><c:out value="${current_user.login}"></c:out></span></li>
				<li>
					<!--			<form action="${pageContext.request.contextPath}/Controller" method="post"> -->

					<form action="../Controller" method="post">
						<input type="hidden" name="command" value="EDIT" />
						<button type="submit">
							<c:out value="${edit}" />
						</button>
					</form>
				</li>
				<li>
					<!--			<form action="${pageContext.request.contextPath}/Controller" method="post">	-->
					<form action="../Controller" method="post">
						<input type="hidden" name="command" value="LOGOUT" />
						<button type="submit">
							<c:out value="${logout}" />
						</button>
					</form>
				</li>
			</ul>

		</c:when>
		<c:otherwise>
			<ul id="menu">
				<li><a href="jsp/login.jsp">
					<c:out value="${login}" /></a>
				</li>
				<li><a href="jsp/register.jsp">
					<c:out value="${registration}" /></a>
				</li>
			</ul>
		</c:otherwise>
	</c:choose>

	<ul id="lang">
		<li>
			<form action="${pageContext.request.contextPath}/Controller" method="post">
<!-- 			<form action="Controller" method="post">	 -->
				<input type="hidden" name="command" value="CHANGE_LANG" /> 
 				<input type="hidden" name="address" value="${pageContext.request.requestURL}" />
				<input type="hidden" name="query" value="${pageContext.request.queryString}" />
				<button type="submit" name="local" value="ru_RU">RU</button>
			</form>
		</li>
		<li>
			<form action="${pageContext.request.contextPath}/Controller" method="post">
<!-- 			<form action="Controller" method="post">	 -->
				<input type="hidden" name="command" value="CHANGE_LANG" />
 				<input type="hidden" name="address" value="${pageContext.request.requestURL}" />
				<input type="hidden" name="query" value="${pageContext.request.queryString}" />
				<button type="submit" name="local" value="en_US">EN</button>
			</form>
		</li>
	</ul>

</body>
</html>