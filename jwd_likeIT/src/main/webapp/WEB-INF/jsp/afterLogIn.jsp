<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<c:import url="jsp/fragment/header.jsp"></c:import>
	<c:import url="jsp/menu.jsp"></c:import>
	
	<c:import url="jsp/lastQuestions.jsp"></c:import>
	<c:import url="jsp/fragment/footer.jsp"></c:import>
</body>
</html>