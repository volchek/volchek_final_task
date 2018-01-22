<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="question.no_questions" bundle="${lc}" var="warning" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<c:import url="jsp/fragment/header.jsp"></c:import>
	<c:import url="jsp/menu.jsp"></c:import>
	
	<div class="${sessionScope.user ? 'warning' : 'hide'}" ><c:out value="${warning}" /></div>

	<div class="question-container myquestions clearfix">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="SHOW_QUESTION" /> <input
				type="hidden" name="command" value="1" />

			<div class="mark">
				<span class="tag">10</span>
			</div>
			<div class="question clearfix">
				<h2>
					<a href="#">TITLE TITLE TITLE TITLE TITLE TITLE TITLE TITLE
						TITLE TITLE TITLE TITLE</a>
				</h2>
				<div>
					<span class="tag">Lang1</span><span class="tag">Lang2</span><span
						class="tag">Lang3</span>
				</div>
				<div>
					<span class="tag">Tag1</span><span class="tag">Tag2</span><span
						class="tag">Tag3</span>
				</div>
				<p>LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM
					LOREM IPSUMLOREM IPSUM LOREM IPSUM LOREM IPSUMLOREM IPSUM LOREM
					IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM v v LOREM
					IPSUM</p>
				<p class="date">TIME</p>
			</div>
		</form>
	</div>


	<div class="question-container myquestions clearfix">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="SHOW_QUESTION" /> <input
				type="hidden" name="command" value="1" />

			<div class="mark">
				<span class="tag">10</span>
			</div>
			<div class="question clearfix">
				<h2>
					<a href="#">TITLE TITLE TITLE TITLE TITLE TITLE TITLE TITLE
						TITLE TITLE TITLE TITLE</a>
				</h2>
				<div>
					<span class="tag">Lang1</span><span class="tag">Lang2</span><span
						class="tag">Lang3</span>
				</div>
				<div>
					<span class="tag">Tag1</span><span class="tag">Tag2</span><span
						class="tag">Tag3</span>
				</div>
				<p>LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM
					LOREM IPSUMLOREM IPSUM LOREM IPSUM LOREM IPSUMLOREM IPSUM LOREM
					IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM v v LOREM
					IPSUM</p>
				<p class="date">TIME</p>
			</div>
		</form>
	</div>


	<c:import url="jsp/fragment/footer.jsp"></c:import>
</body>
</html>