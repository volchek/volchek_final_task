<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css">
</head>
<body>
	<c:import url="fragment/header.jsp"></c:import>
	<c:import url="menu.jsp"></c:import>


	<table class="feed">
		<tr>
			<td class="mark">5</td>
			<td class="text">2 Answers</td>
			<td class="question-container">Title Title Title Title Title
				Title Title Title Title Title Title Title Title Title Title<br>
				<span class="lang">lang</span><span class="lang">lang</span><span
				class="lang">lang</span><span class="lang">lang</span><span class="lang">lang</span><span class="lang">lang</span>
			</td>
			<td class="author">User</td>
		</tr>
	</table>
	<c:import url="fragment/footer.jsp"></c:import>
</body>
</html>