<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="register.surname" bundle="${lc}" var="surname" />
<fmt:message key="register.name" bundle="${lc}" var="name" />
<fmt:message key="register.birthday" bundle="${lc}" var="birthday" />
<fmt:message key="register.status" bundle="${lc}" var="status" />
<fmt:message key="register.status.student" bundle="${lc}" var="student" />
<fmt:message key="register.status.trainer" bundle="${lc}" var="trainer" />
<fmt:message key="register.status.guru" bundle="${lc}" var="guru" />
<fmt:message key="register.login" bundle="${lc}" var="login" />
<fmt:message key="register.password" bundle="${lc}" var="password" />
<fmt:message key="register.rpt_password" bundle="${lc}"
	var="rpt_password" />
<fmt:message key="register.btn.cancel" bundle="${lc}" var="cancel" />
<fmt:message key="register.btn.finish" bundle="${lc}" var="finish" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css">
<body>
	<form action="../Controller" method="post"
		onsubmit="return checkPasswords()">
		<input type="hidden" name="command" value="REGISTER" />

		<div class="container">
			<label><b><c:out value="${surname}" /></b></label> <input
				type="text" name="surname" required> <label><b><c:out
						value="${name}" /></b></label> <input type="text" name="name" required>

			<label><b><c:out value="${birthday}" /></b></label> <input
				type="date" name="date" value="1970-01-01"> <label><b><c:out
						value="${status}" /></b></label> <select name="status">
				<option value="none">------</option>
				<option value="student"><c:out value="${student}" /></option>
				<option value="trainer"><c:out value="${trainer}" /></option>
				<option value="guru"><c:out value="${guru}" /></option>
			</select> <label><b><c:out value="${login}" /></b></label> <input type="text"
				name="login" required> <label><b><c:out
						value="${password}" /></b></label> <input type="password" name="password"
				id="psw1" required> <label><b><c:out
						value="${rpt_password}" /></b></label> <input type="password"
				name="psw-repeat" id="psw2" required>
		</div>
		<div class="clearfix">
			<button type="button" class="cancelbtn"
				onclick="location.href='../index.jsp'">
				<c:out value="${cancel}" />
			</button>
			<button type="submit" class="signupbtn">
				<c:out value="${finish}" />
			</button>
		</div>
	</form>
	<script>
		function checkPasswords() {
			var psw1 = document.getElementById("psw1").value;
			var psw2 = document.getElementById("psw2").value;
			if (psw1 != psw2) {
				alert("Check passwords!");
				return false;
			}
			return true;
		}
	</script>
</body>
</html>