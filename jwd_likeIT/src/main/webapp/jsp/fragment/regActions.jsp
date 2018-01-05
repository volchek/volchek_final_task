<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="actions.my_questions" bundle="${lc}"
	var="my_questions" />
<fmt:message key="actions.my_answers" bundle="${lc}" var="my_answers" />
<fmt:message key="actions.ask_question" bundle="${lc}"
	var="ask_question" />
<fmt:message key="actions.last_questions" bundle="${lc}"
	var="last_questions" />
<fmt:message key="actions.find_user" bundle="${lc}" var="find_user" />
<fmt:message key="actions.find_question" bundle="${lc}"
	var="find_question" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<div class="fullactions">
		<div>
			<a href="../jsp/lastQuestions.jsp"><c:out
					value="${last_questions}" /></a>
		</div>
		<div>
			<a href="../jsp/myQuestions.jsp"><c:out value="${my_questions}" /></a>
		</div>
		<div>
			<a href="../jsp/myAnswers.jsp"><c:out value="${my_answers}" /></a>
		</div>
		<div>
			<a href="../jsp/addQuestion.jsp"><c:out value="${ask_question}" /></a>
		</div>
		<div>
			<a href="../jsp/findQuestion.jsp"><c:out value="${find_question}" /></a>
		</div>
		<div class="find">
			<a href="../jsp/findUser.jsp"><c:out value="${find_user}" /></a>
		</div>


		<!-- <button><c:out value="${find_user}" /></button>  -->
		<!-- 	<div class="question">
			<a href="#"><c:out value="${questions}" /></a>
		</div>
		<div class="answers">
			<a href="#"><c:out value="${answers}" /></a>
		</div>
		<div class="find">
			<a href="../jsp/findUser.jsp"><c:out value="${find}" /></a>
		</div>
				 -->

	</div>


</body>
</html>