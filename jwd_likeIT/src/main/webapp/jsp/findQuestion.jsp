<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="find.btn.find" bundle="${lc}" var="find" />
<fmt:message key="find.id_question" bundle="${lc}" var="id" />
<fmt:message key="find.languages" bundle="${lc}" var="languages" />
<fmt:message key="find.tags" bundle="${lc}" var="tags" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css">
<link rel="stylesheet" type="text/css" href="../css/awesomplete.css">
</head>
<body>
	<c:import url="fragment/header.jsp"></c:import>
	<c:import url="menu.jsp"></c:import>
	
	<c:set var="lang_list" value="${applicationScope.languages}" />
	<c:set var="tag_list" value="${applicationScope.tags}" />

	<div class="finder">
		<form action="${pageContext.request.contextPath}/Controller"
			method="get">
			<input type="hidden" name="command" value="FIND_QUESTION_BY_ID" />
			<div class="container">
				<label><c:out value="${id}" /><input type="text"
					name="question_id" pattern="[0-9]+" required></label>
				<button type="submit" class="signupbtn">
					<c:out value="${find}" />
				</button>
			</div>
		</form>
	</div>

	<div class="finder">
		<form action="${pageContext.request.contextPath}/Controller"
			method="get">
			<input type="hidden" name="command" value="FIND_QUESTION_BY_LANGUAGE" />
			<div class="container">
				<label><c:out value="${languages}" /><br>
				<input type="text"
					name="language" class="tag" required id="lang1"
					style="display: inline" />
					<input type="text" name="language"
					class="tag" id="lang2" style="display: none" />
					<input type="text"
					name="language" class="tag" id="lang3" style="display: none" />
					<a href="#" id="add-language" class="add-tag"
					onclick="addFields('add-language', 'remove-language', 'lang2', 'lang3')">
						+ </a>
					<a href="#" id="remove-language" class="add-tag"
					style="display: none"
					onclick="removeFields('add-language', 'remove-language', 'lang2', 'lang3')">
						- </a> </label> <br>
				<button type="submit" class="signupbtn">
					<c:out value="${find}" />
				</button>
			</div>

		</form>
	</div>

	<div class="finder">
		<form action="${pageContext.request.contextPath}/Controller"
			method="get">
			<input type="hidden" name="command" value="FIND_QUESTION_BY_TAG" />
			<div class="container">
				<label><c:out value="${tags}" /> <br> <input type="text" name="tag"
					id="tag1" class="tag awesomplete" style="display: inline" required />
					<input type="text" name="tag" id="tag2" class="tag awesomplete"
					style="display: none" /> <input type="text" name="tag" id="tag3"
					class="tag awesomplete" style="display: none" /> <a href="#"
					id="add-tag" class="add-tag"
					onclick="addFields('add-tag', 'remove-tag', 'tag2', 'tag3')"> +
				</a> <a href="#" id="remove-tag" class="add-tag" style="display: none"
					onclick="removeFields('add-tag', 'remove-tag', 'tag2', 'tag3')">
						- </a>
				</label> <br>
				<button type="submit" class="signupbtn">
					<c:out value="${find}" />
				</button>
			</div>

		</form>
	</div>
	<c:import url="fragment/footer.jsp"></c:import>

	<script src="${pageContext.request.contextPath}/js/add_element.js"></script>
	<script src="${pageContext.request.contextPath}/js/awesomplete.js"></script>
	<script>
		var lang_list = ${lang_list};
		for (var i = 1; i < 4; i++){
			var id = "lang" + i;
			var input = document.getElementById(id);
			new Awesomplete(input, { list : ${lang_list} });
		}
		for (var i = 1; i < 4; i++){
			var id = "tag" + i;
			var input = document.getElementById(id);
			new Awesomplete(input, { list: ${tag_list} });
		}
    </script>

</body>
</html>