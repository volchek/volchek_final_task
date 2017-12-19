<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="menu.logout" bundle="${lc}" var="logout" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<div class="header">LikeIT</div>
	<ul id="menu">
		<li><span><c:out value="${user.login}"></c:out></span></li>
		<li>
			<form action="../Controller" method="post">
				<input type="hidden" name="command" value="LOGOUT" />
 				<button type="submit"><c:out value="${logout}" /></button>
			</form>
		</li>
		<li></li>
	</ul>
	<ul id="lang">
		<li>
			<form action="../Controller" method="post">
				<input type="hidden" name="command" value="CHANGE_LANG" />
				<input type="hidden" name="address" value="${pageContext.request.requestURL}"/>
                <input type="hidden" name="query" value="${pageContext.request.queryString}"/>
 				<button type="submit" name="local" value="ru_RU">RU</button>
			</form>
		</li>
		<li>
			<form action="../Controller" method="post">
				<input type="hidden" name="command" value="CHANGE_LANG" />
				<input type="hidden" name="address" value="${pageContext.request.requestURL}"/>
                <input type="hidden" name="query" value="${pageContext.request.queryString}"/>
				<button type="submit" name="local" value="en_US">EN</button>
			</form>
		</li>
	</ul>

</body>
</html>