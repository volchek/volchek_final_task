<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="register.personal_info" bundle="${lc}"
	var="personal_info" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" href=“css/normalize.css“>
<link href="https://fonts.googleapis.com/css?family=Work+Sans"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Raleway"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<c:import url="jsp/fragment/header.jsp"></c:import>
	<c:import url="jsp/menu.jsp"></c:import>
	
	<h2>Спасибо! Личные данные обновлены</h2>
	
	<a href="${pageContext.request.contextPath}/index.jsp">Вернуться</a>
	
	<c:import url="jsp/fragment/footer.jsp"></c:import>
</body>
</html>