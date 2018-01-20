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


	<div class="feed clearfix">
		<div class="question-container">Title Title Title Title Title
			Title Title Title Title Title Title Title Title Title Title Title
			Title Title Title Title Title Title Title</div>
		<div class="question-container clearfix">
			<span class="lang">lang</span><span class="lang">lang</span><span
				class="lang">lang</span> <span class="lang">lang</span><span
				class="lang">lang</span><span class="lang">lang</span>
		</div>
		<div class="info clearfix">
			<div class="question-info clearfix">
				<div class="date">15.01.2018</div>
				<div class="author">User</div>
			</div>
			<div class="tmp-info clearfix">
				<div class="mark">
					<span class="mark-text">Score: </span>5
				</div>
				<div class="text">2 answers</div>
			</div>
		</div>
	</div>
	
		<div class="feed clearfix">
		<div class="question-container">Title Title Title Title Title
			Title Title Title Title Title Title Title Title Title Title Title
			Title Title Title Title Title Title Title</div>
		<div class="question-container clearfix">
			<span class="lang">lang</span><span class="lang">lang</span><span
				class="lang">lang</span> <span class="lang">lang</span><span
				class="lang">lang</span><span class="lang">lang</span>
		</div>
		<div class="info clearfix">
			<div class="question-info clearfix">
				<div class="date">15.01.2018</div>
				<div class="author">User</div>
			</div>
			<div class="tmp-info clearfix">
				<div class="mark">
					<span class="mark-text">Score: </span>5
				</div>
				<div class="text">Answers: 2</div>
			</div>
		</div>
	</div>
	<c:import url="fragment/footer.jsp"></c:import>
</body>
</html>