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
<fmt:message key="error.link" bundle="${lc}" var="main_page" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<jsp:include page="../fragment/header.jsp" flush="true" />
	<h2>
		<c:out value="${msg}" />
	</h2>
	<br>
	<br>
	<a href="/final-task/index.jsp"><c:out value="${main_page}" /></a>
</body>
</html>