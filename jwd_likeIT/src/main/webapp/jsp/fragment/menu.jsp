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
		<li><a href="../index.jsp"><c:out value="${logout}" /></a></li>
		<li></li>
	</ul>
	<ul id="lang">
		<li><a href="#">RU</a></li>
		<li><a href="#">EN</a></li>
	</ul>
</body>
</html>