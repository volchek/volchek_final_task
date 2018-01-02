<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="user.title" bundle="${lc}" var="title" />
<fmt:message key="user.surname" bundle="${lc}" var="surname" />
<fmt:message key="user.name" bundle="${lc}" var="name" />
<fmt:message key="user.login" bundle="${lc}" var="login" />
<fmt:message key="user.status" bundle="${lc}" var="status" />
<fmt:message key="user.link" bundle="${lc}" var="link" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<c:import url="fragment/header.jsp"></c:import>
	<c:import url="menu.jsp"></c:import>
	<h1>
		<c:out value="${title}" />
	</h1>

	<table border="1" class="userInfo">
		<thead>
			<tr>
				<td><c:out value="${name}" /></td>
				<td><c:out value="${surname}" /></td>
				<td><c:out value="${login}" /></td>
				<td><c:out value="${status}" /></td>
			</tr>
		</thead>
		<tr>
			<td><c:out value="${requestScope.user.name}"></c:out></td>
			<td><c:out value="${requestScope.user.surname}"></c:out></td>
			<td><c:out value="${requestScope.user.login}"></c:out></td>
			<td><c:out value="${requestScope.user.status}"></c:out></td>
		</tr>
	</table>
	<br>
	<a href="jsp/findUser.jsp" class="link"><c:out value="${link}" /></a>
</body>
</html>