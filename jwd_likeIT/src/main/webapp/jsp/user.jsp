<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="likeitTagLib" prefix="ct"%>

<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="main_menu.login" bundle="${lc}" var="login" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LikeIt</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<c:import url="fragment/header.jsp"></c:import>
	<c:import url="menu.jsp"></c:import>

	<c:set value="${requestScope.user}" var="user" />

	<c:if test="${user != null}">
		<div class="container clearfix">

			<h1>
				<c:out value="${user.login}" />
			</h1>

			<div class="question clearfix">
				<div>
					<ct:keyword cssClass="tag"
						keywordList="${requestScope.string_languages}" />
				</div>
			</div>

			<p>
				<c:out value="${user.status}" />
			</p>
			<p class="date">
				<ct:date date="${user.registrationDate}" text="date"
					format="dd-MM-yyyy" />
			</p>
			<p>Рейтинг: "${user.rating}"</p>

			<table>
				<tr>
					<td>Вопросов</td>
					<td>Ответов</td>
				</tr>
				<tr>
					<td>${fn:length(requestScope.question_list)}</td>
					<td>${fn:length(requestScope.answer_list)}</td>
				</tr>
			</table>

			<c:choose>
			<c:when test="${(requestScope.question_list != null) and (fn:length(requestScope.question_list)!=0)}">
				<div>Лучшие вопросы</div>
				<div class="question-container myquestions clearfix">

					<c:forEach var="item" items="${ requestScope.question_list}">
					
						<form action="Controller" method="get" class="clearfix"
							id='user_question${item.id}'>
							<input type="hidden" name="command" value="FIND_QUESTION_BY_ID" />
							<input type="hidden" name="question_id" value="${item.id}" />
							<div class="mark clearfix">
								<span><ct:mark
										averageMark="${item.averageMark}" /></span>
							</div>
							<div class="question clearfix">
								<h2>
									<c:set value='user_question${item.id}' var="elem_id" />
									<a
										href='javascript:document.getElementById("${elem_id}").submit()'>${item.title}</a>
								</h2>
								<div>
									<ct:keyword cssClass="tag" keywordList="${item.languages}" />
								</div>
								<div>
									<ct:keyword cssClass="tag" keywordList="${item.tags}" />
								</div>
								<p class="date">
									<ct:date date="${item.creationDate}" text="${question_info}"
										format="dd-MM-yyyy" />
								</p>
							</div>
						</form>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<p>Пользователь пока не задавал вопросов</p>
			</c:otherwise>
			</c:choose>

			<c:choose>
			<c:when test="${(requestScope.answer_list != null) and (fn:length(requestScope.answer_list)!=0)}">
				<div>Лучшие ответы</div>
				<div class="question-container myanswers clearfix">

					<c:forEach var="item" items="${ requestScope.answer_list}">

						<form action="Controller" method="get" class="clearfix"
							id='user_question${item.id}'>
							<input type="hidden" name="command" value="FIND_QUESTION_BY_ID" />
							<input type="hidden" name="question_id" value="${item.id}" />

							<div class="question clearfix">
								<div>
									<ct:keyword cssClass="tag" keywordList="${item.languages}" />
									<ct:keyword cssClass="tag" keywordList="${item.tags}" />
								</div>
								<h2>
									<c:set value='user_question${item.id}' var="elem_id" />
									<a
										href='javascript:document.getElementById("${elem_id}").submit()'>${item.title}</a>
								</h2>
								<div>
									<span class="date"> <ct:date date="${item.creationDate}"
											text="${signed}" format="dd-MM-yyyy" /></span> <span class="author">${item.authorLogin}</span>
								</div>
							</div>
						</form>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<p>Пользователь пока не отвечал на вопросы</p>
			</c:otherwise>
			</c:choose>

		</div>
	</c:if>

	<c:import url="fragment/footer.jsp"></c:import>
</body>
</html>