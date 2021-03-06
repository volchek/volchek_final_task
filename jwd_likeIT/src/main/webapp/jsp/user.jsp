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
<fmt:message key="user.rating" bundle="${lc}" var="rating" />
<fmt:message key="user.date" bundle="${lc}" var="date" />
<fmt:message key="user.question" bundle="${lc}" var="question" />
<fmt:message key="user.answer" bundle="${lc}" var="answer" />
<fmt:message key="user.best_question" bundle="${lc}" var="best_question" />
<fmt:message key="user.best_answer" bundle="${lc}" var="best_answer" />
<fmt:message key="user.no_questions" bundle="${lc}" var="no_questions" />
<fmt:message key="user.no_answers" bundle="${lc}" var="no_answers" />
<fmt:message key="user.ban" bundle="${lc}" var="ban" />
<fmt:message key="user.unban" bundle="${lc}" var="unban" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LikeIt</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<c:import url="fragment/header.jsp"></c:import>
	<c:import url="menu.jsp"></c:import>

	<c:set value="${requestScope.user}" var="user" />

	<c:if test="${user != null}">
		<div class="container user clearfix">

			<h1>
				<c:out value="${user.login}" />
			</h1>

			<div class="question-container clearfix">
				<ct:keyword cssClass="tag"
					keywordList="${requestScope.string_languages}" />
				<p>
					<ct:date date="${user.registrationDate}" text="${date}"
						format="dd-MM-yyyy" />
				</p>
				<p>
					<c:out value="${rating}" />
					<span>${user.rating}</span>
				</p>

				<c:if test="${sessionScope.current_user.admin}">
					<c:choose>
						<c:when test="${requestScope.user.banned == true}">
							<form action="../Controller" method="post">
								<input type="hidden" name="command" value="BAN_USER">
								<input type="hidden" name="ban" value="false">
								<input type="hidden" name="user_id" value="${requestScope.user.id}">
								<div class="buttons">
									<button type="submit" class="signupbtn">
										<c:out value="${unban}" />
									</button>
								</div>
							</form>
						</c:when>
						<c:otherwise>
							<form action="../Controller" method="post">
								<input type="hidden" name="command" value="BAN_USER">
								<input type="hidden" name="ban" value="true">
								<input type="hidden" name="user_id" value="${requestScope.user.id}">
								<div class="buttons">
									<button type="submit" class="cancelbtn">
										<c:out value="${ban}" />
									</button>
								</div>
							</form>
						</c:otherwise>
					</c:choose>
				</c:if>
				<table>
					<tr>
						<th><c:out value="${question}" /></th>
						<th><c:out value="${answer}" /></th>
					</tr>
					<tr>
						<td>${fn:length(requestScope.question_list)}</td>
						<td>${fn:length(requestScope.answer_list)}</td>
					</tr>
				</table>
			</div>
		</div>

		<c:choose>
			<c:when
				test="${(requestScope.question_list != null) and (fn:length(requestScope.question_list)!=0)}">
				<div class="question-container myquestions clearfix">
					<table class="user-questions">
						<caption>
							<c:out value="${best_question}" />
						</caption>
						<c:forEach var="item" items="${requestScope.question_list}">
							<tr>
								<td>
									<div class="mark">
										<span><ct:mark averageMark="${item.averageMark}" /></span>
									</div>
								</td>
								<td class="text">
									<h4>
										<a
											href="${pageContext.request.contextPath}/questions/${item.id}">${item.title}</a>
									</h4>
								</td>
								<td class="tags"><ct:keyword cssClass="lang"
										keywordList="${item.languages}" /> <ct:keyword
										cssClass="lang" keywordList="${item.tags}" /></td>
								<td>
									<p class="date">
										<ct:date date="${item.creationDate}" text="${question_info}"
											format="dd-MM-yyyy" />
									</p>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:when>
			<c:otherwise>
				<div class="container user">
					<p>
						<c:out value="${user.login}" />
						<c:out value="${no_questions}" />
					</p>
				</div>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when
				test="${(requestScope.answer_list != null) and (fn:length(requestScope.answer_list)!=0)}">
				<div class="question-container myquestions clearfix">
					<table class="user-questions">
						<caption>
							<c:out value="${best_answer}" />
						</caption>
						<c:forEach var="item" items="${requestScope.answer_list}">
							<tr>
								<td>
									<div class="mark">
										<span><ct:mark averageMark="${item.averageMark}" /></span>
									</div>
								</td>
								<td class="text">
									<h4>
										<a
											href="${pageContext.request.contextPath}/questions/${item.id}">${item.title}</a>
									</h4>
								</td>
								<td class="tags"><ct:keyword cssClass="lang"
										keywordList="${item.languages}" /> <ct:keyword
										cssClass="lang" keywordList="${item.tags}" /></td>
								<td><p class="date">
										<ct:date date="${item.creationDate}" text="${signed}"
											format="dd-MM-yyyy" />
									</p></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:when>
			<c:otherwise>
				<div class="container user">
					<p>
						<c:out value="${user.login}" />
						<c:out value="${no_answers}" />
					</p>
				</div>
			</c:otherwise>
		</c:choose>

	</c:if>

	<c:import url="fragment/footer.jsp"></c:import>
</body>
</html>