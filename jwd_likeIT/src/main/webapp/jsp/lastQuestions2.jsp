<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="likeitTagLib" prefix="ct"%>

<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="answer.score" bundle="${lc}" var="score" />
<fmt:message key="answer.count" bundle="${lc}" var="count" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css">
</head>
<body>
	<c:import url="fragment/header.jsp"></c:import>
	<c:import url="menu.jsp"></c:import>

	<c:forEach var="item" items="${ requestScope.question_list}">
		<div class="feed clearfix">
			<form action="Controller" method="get" class="clearfix"
				id='question${item.id}'>
				<input type="hidden" name="command" value="FIND_QUESTION_BY_ID" />
				<input type="hidden" name="question_id" value="${item.id}" />

				<div class="question-container">
					<c:set value='question${item.id}' var="elem_id" />
					<a href='javascript:document.getElementById("${elem_id}").submit()'>${item.title}
					</a>
				</div>
				<div class="question-container clearfix">
					<ct:keyword cssClass="lang" keywordList="${item.languages}" />
					<ct:keyword cssClass="lang" keywordList="${item.tags}" />
				</div>
				<div class="info clearfix">
					<div class="question-info clearfix">
						<div class="date">
							<ct:date date="${item.creationDate}" text="${question_info}"
								format="dd-MM-yyyy" />
						</div>
						<div class="author">${item.authorLogin}</div>
					</div>
					<div class="tmp-info clearfix">
						<div class="mark">
							<span class="mark-text">${score} </span>
							<ct:mark averageMark="${item.averageMark}" />
						</div>
						<div class="text">
							<c:out value="${count}" />${fn:length(item.answers)}</div>
					</div>
				</div>
			</form>
		</div>
	</c:forEach>

	<c:import url="fragment/footer.jsp"></c:import>
</body>
</html>