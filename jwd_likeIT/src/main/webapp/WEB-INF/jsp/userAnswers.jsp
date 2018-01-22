<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="answer.no_answers" bundle="${lc}" var="warning" />
<fmt:message key="answer.author_info" bundle="${lc}" var="signed" />
<fmt:message key="answer.score" bundle="${lc}" var="score" />
<fmt:message key="answer.vote" bundle="${lc}" var="vote" />
<fmt:message key="answer.answer" bundle="${lc}" var="answer" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<c:import url="jsp/fragment/header.jsp"></c:import>
	<c:import url="jsp/menu.jsp"></c:import>


	<div class="${sessionScope.user ? 'warning' : 'hide'}">
		<c:out value="${warning}" />
	</div>

	<div class="question-container myanswers clearfix">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="SHOW_QUESTION" /> <input
				type="hidden" name="command" value="1" />

			<div class="question clearfix">
				<div>
					<span class="tag">Lang1</span><span class="tag">Lang2</span><span
						class="tag">Lang3</span> <span class="tag">Tag1</span><span
						class="tag">Tag2</span><span class="tag">Tag3</span>
				</div>
				<h2>
					<a href="#">TITLE TITLE TITLE TITLE TITLE TITLE TITLE TITLE
						TITLE TITLE TITLE TITLE</a>
				</h2>
				<div>
					<c:out value="${signed}" />
					<span class="date"> 10.01.2018</span> <span class="author">Author</span>
				</div>
			</div>

			<div class="answer">
				<div><c:out value="${ answer }" /></div>
				<div class="text">Текст... Текст... Текст... Текст...
					Текст...Текст... Текст... Текст... Текст... Текст... Текст...
					Текст... Текст... Текст... Текст... Текст... Текст...</div>
				<div>
					<c:out value="${ score }"/><span class="mark average">10</span> <c:out value="${ vote }" /><span>2</span>
				</div>
			</div>
			<br> <br> <br>
		</form>
	</div>


	<c:import url="jsp/fragment/footer.jsp"></c:import>
</body>
</html>