<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="error.inserting" bundle="${lc}" var="msg" />
<fmt:message key="error.link" bundle="${lc}" var="link" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
	<c:import url="../fragment/header.jsp" />
	<c:import url="../menu.jsp" />
	<div class="error">
		<h2>
			<c:out value="${msg}" />
		</h2>
	</div>
	<br>
	<br>
	<a href="${pageContext.request.contextPath}/index.jsp" class="link"><c:out
			value="${link}" /></a>
	<c:import url="../fragment/footer.jsp" />
</body>
</html>