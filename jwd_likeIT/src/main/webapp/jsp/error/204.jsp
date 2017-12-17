<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="error.204" bundle="${lc}" var="msg" />
<fmt:message key="user.link" bundle="${lc}" var="find" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<jsp:include page="../fragment/header.jsp" flush="true" />
	<div class="error">
		<c:out value="${msg}" />
	</div>
	<br>
	<br>
	<a href="jsp/findUser.jsp" class="link"><c:out value="${find}" /></a>
</body>
</html>