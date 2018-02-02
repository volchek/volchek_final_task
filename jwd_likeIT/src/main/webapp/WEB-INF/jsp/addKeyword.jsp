<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="find.languages" bundle="${lc}" var="languages" />
<fmt:message key="find.tags" bundle="${lc}" var="tags" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/awesomplete.css">
</head>
<body>
	<c:import url="jsp/fragment/header.jsp"></c:import>
	<c:import url="jsp/menu.jsp"></c:import>

	<c:set var="lang_list" value="${applicationScope.languages}" />
	<c:set var="tag_list" value="${applicationScope.tags}" />

	<div class="adder clearfix">
		<div class="container left">
			<form action="${pageContext.request.contextPath}/Controller"
				method="post">
				<input type="hidden" name="command" value="ADD_LANGUAGE" /> <label><c:out
						value="${languages}" /><br> <input type="text"
					name="language" class="lang" required id="lang"
					style="display: inline" /> </label><br>
				<button type="submit" class="signupbtn">
					<c:out value="${find}" />
				</button>
			</form>
		</div>
		<div class="container right">
			<form action="${pageContext.request.contextPath}/Controller"
				method="post">
				<input type="hidden" name="command" value="UPDATE_LANGUAGE_LIST" />
				<button type="submit" class="cancelbtn">UPDATE ALL</button>
			</form>
		</div>
	</div>

	<div class="adder clearfix">
		<div class="container left">
			<form action="${pageContext.request.contextPath}/Controller"
				method="post">
				<input type="hidden" name="command" value="ADD_TAG" /> <label><c:out
						value="${tags}" /> <br> <input type="text" name="tag"
					id="tag" class="lang awesomplete" style="display: inline" required />
				</label> <br>
				<button type="submit" class="signupbtn">
					<c:out value="${find}" />
				</button>
			</form>
		</div>
		<div class="container right">
			<form action="${pageContext.request.contextPath}/Controller"
				method="post">
				<input type="hidden" name="command" value="UPDATE_TAG_LIST" />
				<button type="submit" class="cancelbtn">UPDATE ALL</button>
			</form>
		</div>
	</div>

	<c:import url="jsp/fragment/footer.jsp"></c:import>

	<script src="${pageContext.request.contextPath}/js/awesomplete.js"></script>
	<script>
		var lang_list = ${lang_list};
		new Awesomplete(document.getElementById("lang"), { list : ${lang_list} });
		new Awesomplete(document.getElementById("tag"), { list: ${tag_list} });
    </script>

</body>
</html>