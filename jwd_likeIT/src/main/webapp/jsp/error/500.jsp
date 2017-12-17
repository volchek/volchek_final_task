<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="error.500" bundle="${lc}" var="msg" />
<fmt:message key="error.link" bundle="${lc}" var="return" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<div class="../fragment/header">LikeIT</div>
	<h2>
		<c:out value="${msg}" />
	</h2>
	<br>
	<br>
	<a href="../index.jsp"><c:out value="${return}" /></a>
</body>
</html>