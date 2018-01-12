<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="register.personal_info" bundle="${lc}"
	var="personal_info" />
<fmt:message key="register.surname" bundle="${lc}" var="surname" />
<fmt:message key="register.name" bundle="${lc}" var="name" />
<fmt:message key="register.birthday" bundle="${lc}" var="birthday" />
<fmt:message key="register.status" bundle="${lc}" var="status" />
<fmt:message key="register.status.student" bundle="${lc}" var="student" />
<fmt:message key="register.status.trainer" bundle="${lc}" var="trainer" />
<fmt:message key="register.status.guru" bundle="${lc}" var="guru" />
<fmt:message key="register.email" bundle="${lc}" var="email" />
<fmt:message key="register.second_email" bundle="${lc}" var="email_2" />
<fmt:message key="register.image" bundle="${lc}" var="image" />
<fmt:message key="register.btn.cancel" bundle="${lc}" var="cancel" />
<fmt:message key="register.btn.finish" bundle="${lc}" var="finish" />
<fmt:message key="register.language" bundle="${lc}" var="language" />
<fmt:message key="register.level.beginner" bundle="${lc}" var="beginner" />
<fmt:message key="register.level.user" bundle="${lc}" var="user" />
<fmt:message key="register.level.specialist" bundle="${lc}"
	var="specialist" />
<fmt:message key="register.level.expert" bundle="${lc}" var="expert" />
<fmt:message key="register.language.c" bundle="${lc}" var="c" />
<fmt:message key="register.language.cpp" bundle="${lc}" var="cpp" />
<fmt:message key="register.language.csharp" bundle="${lc}" var="csharp" />
<fmt:message key="register.language.java" bundle="${lc}" var="java" />
<fmt:message key="register.language.python" bundle="${lc}" var="python" />
<fmt:message key="register.language.swift" bundle="${lc}" var="swift" />
<fmt:message key="register.language.perl" bundle="${lc}" var="perl" />
<fmt:message key="register.language.php" bundle="${lc}" var="php" />
<fmt:message key="register.language.html" bundle="${lc}" var="html" />
<fmt:message key="register.language.css" bundle="${lc}" var="css" />
<fmt:message key="register.language.js" bundle="${lc}" var="js" />
<fmt:message key="register.language.sql" bundle="${lc}" var="sql" />
<fmt:message key="register.tooltip.login" bundle="${lc}"
	var="tooltip_login" />
<fmt:message key="register.tooltip.password" bundle="${lc}"
	var="tooltip_password" />
	
<c:set var="user_status" value="${sessionScope.current_user.status}" />
<c:set var="user_languages" value="${sessionScope.string_languages}" />
<c:set var="user_emails" value="${sessionScope.current_user.email}" />
<fmt:formatDate value = "${sessionScope.current_user.birthday}" pattern = "yyyy-MM-dd" var="user_birthday" />
	
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" href=“css/normalize.css“>
<link href="https://fonts.googleapis.com/css?family=Work+Sans"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Raleway"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<c:import url="jsp/fragment/header.jsp"></c:import>
	<c:import url="jsp/menu.jsp"></c:import>

	<form action="${pageContext.request.contextPath}/Controller" method="post"
		onsubmit="return checkPasswords()">
		<input type="hidden" name="command" value="EDIT_USER" />
		<div class="personal-info">
			<h2>
				<c:out value="${personal_info}" />
			</h2>
			<h4 id="registration-error">
			</h4>
			<label><c:out value="${surname}" /> <br> <input
				type="text" name="surname" pattern="^[\D]+$" value="<c:out value="${sessionScope.current_user.surname}" />" required> </label> <br>
			<label><c:out value="${name}" /> <br> <input
				type="text" name="name" pattern="^[\D]+$" value="<c:out value="${sessionScope.current_user.name}" />" required> </label> <br>
			<label><c:out value="${birthday}" />* <br> <input
				type="date" name="date" min="1900-01-01"
				max="2011-01-01" value="<c:out value="${user_birthday}" />"></label>
				<br>
			<label><c:out value="${status}" />
			<br>
			<select name="status" id="status">
					<option value="none" ${user_status == 'none' ? 'selected' : ''}>------</option>
					<option value="student" ${user_status == 'student' ? 'selected' : ''}><c:out value="${student}" /></option>
					<option value="trainer" ${user_status == 'trainer' ? 'selected' : ''}><c:out value="${trainer}" /></option>
					<option value="guru" ${user_status == 'guru' ? 'selected' : ''}><c:out value="${guru}" /></option>
			</select> </label> <br>
			<label><c:out value="${email}" />* <br> <input
					type="email" name="email" placeholder="user@example.com"
					pattern="^.+@.+\.[a-zA-Z]+$" id="first-email" value="<c:out value="${user_emails[0]}" />" required> </label><br>
			<label><c:out value="${email_2}" /> <br> <input
					type="email" name="second-email" placeholder="user@example.com"
					pattern="^.+@.+\.[a-zA-Z]+$" id="second-email" value="<c:out value="${user_emails[1]}" />"> </label><br> 
		</div>
		<div class="right-side">
			<h2>
				<c:out value="${language}" />
			</h2>
			<div class="lang">
				(1 &ndash;
				<c:out value="${beginner}" />
				; 2 &ndash;
				<c:out value="${user}" />
				; 3 &ndash;
				<c:out value="${specialist}" />
				; 4 &ndash;
				<c:out value="${expert}" />
				)
			</div>
			<table>
				<tr>
					<td><span class="label-lang"><c:out value="${c}" /></span></td>
					<td>
						<div class="vertical-label">
							<label for="c1">1</label><input type="radio" name="c" value="1"
								id="c1" ${user_languages['C'] == 1 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="c2">2</label><input type="radio" name="c" value="2"
								id="c2" ${user_languages['C'] == 2 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="c3">3</label><input type="radio" name="c" value="3"
								id="c3" ${user_languages['C'] == 3 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="c4">4</label><input type="radio" name="c" value="4"
								id="c4" ${user_languages['C'] == 4 ? 'checked' : ''}>
						</div>
					</td>
					<td><span class="label-lang"><c:out value="${perl}" /></span></td>
					<td>
						<div class="vertical-label">
							<label for="perl1">1</label><input type="radio" name="perl"
								value="1" id="perl1" ${user_languages['Perl'] == 1 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="perl2">2</label><input type="radio" name="perl"
								value="2" id="perl2" ${user_languages['Perl'] == 2 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="perl3">3</label><input type="radio" name="perl"
								value="3" id="perl3" ${user_languages['Perl'] == 3 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="perl4">4</label><input type="radio" name="perl"
								value="4" id="perl4" ${user_languages['Perl'] == 4 ? 'checked' : ''}>
						</div>
					</td>
				</tr>
				<tr>
					<td><span class="label-lang"><c:out value="${cpp}" /></span></td>
					<td>
						<div class="vertical-label">
							<label for="cpp1">1</label><input type="radio" name="cpp"
								value="1" id="cpp1" ${user_languages['C++'] == 1 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="cpp2">2</label><input type="radio" name="cpp"
								value="2" id="cpp2" ${user_languages['C++'] == 2 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="cpp3">3</label><input type="radio" name="cpp"
								value="3" id="cpp3" ${user_languages['C++'] == 3 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="cpp4">4</label><input type="radio" name="cpp"
								value="4" id="cpp4" ${user_languages['C++'] == 4 ? 'checked' : ''}>
						</div>
					</td>
					<td><span class="label-lang"><c:out value="${php}" /></span></td>
					<td>
						<div class="vertical-label">
							<label for="php1">1</label><input type="radio" name="php"
								value="1" id="php1" ${user_languages['php'] == 1 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="php2">2</label><input type="radio" name="php"
								value="2" id="php2" ${user_languages['php'] == 2 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="php3">3</label><input type="radio" name="php"
								value="3" id="php3" ${user_languages['php'] == 3 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="php4">4</label><input type="radio" name="php"
								value="4" id="php4" ${user_languages['php'] == 4 ? 'checked' : ''}>
						</div>
					</td>
				</tr>
				<tr>
					<td><span class="label-lang"><c:out value="${csharp}" /></span></td>
					<td>
						<div class="vertical-label">
							<label for="csharp1">1</label><input type="radio" name="csharp"
								value="1" id="csharp1" ${user_languages['C#'] == 1 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="csharp2">2</label><input type="radio" name="csharp"
								value="2" id="csharp2" ${user_languages['C#'] == 2 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="csharp3">3</label><input type="radio" name="csharp"
								value="3" id="csharp3" ${user_languages['C#'] == 3 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="csharp4">4</label><input type="radio" name="csharp"
								value="4" id="csharp4" ${user_languages['C#'] == 4 ? 'checked' : ''}>
						</div>
					</td>
					<td><span class="label-lang"><c:out value="${html}" /></span></td>
					<td>
						<div class="vertical-label">
							<label for="html1">1</label><input type="radio" name="html"
								value="1" id="html1" ${user_languages['HTML'] == 1 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="html2">2</label><input type="radio" name="html"
								value="2" id="html2" ${user_languages['HTML'] == 2 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="html3">3</label><input type="radio" name="html"
								value="3" id="html3" ${user_languages['HTML'] == 3 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="html4">4</label><input type="radio" name="html"
								value="4" id="html4" ${user_languages['HTML'] == 4 ? 'checked' : ''}>
						</div>
					</td>
				</tr>
				<tr>
					<td><span class="label-lang"><c:out value="${java}" /></span></td>
					<td>
						<div class="vertical-label">
							<label for="java1">1</label><input type="radio" name="java"
								value="1" id="java1" ${user_languages['Java'] == 1 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="java2">2</label><input type="radio" name="java"
								value="2" id="java2" ${user_languages['Java'] == 2 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="java3">3</label><input type="radio" name="java"
								value="3" id="java3" ${user_languages['Java'] == 3 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="java4">4</label><input type="radio" name="java"
								value="4" id="java4" ${user_languages['Java'] == 4 ? 'checked' : ''}>
						</div>
					</td>
					<td><span class="label-lang"><c:out value="${css}" /></span></td>
					<td>
						<div class="vertical-label">
							<label for="css1">1</label><input type="radio" name="css"
								value="1" id="css1" ${user_languages['CSS'] == 1 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="css2">2</label><input type="radio" name="css"
								value="2" id="css2" ${user_languages['CSS'] == 2 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="css3">3</label><input type="radio" name="css"
								value="3" id="css3" ${user_languages['CSS'] == 3 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="css4">4</label><input type="radio" name="css"
								value="4" id="css4" ${user_languages['CSS'] == 4 ? 'checked' : ''}>
						</div>
					</td>
				</tr>
				<tr>
					<td><span class="label-lang"><c:out value="${python}" /></span></td>
					<td>
						<div class="vertical-label">
							<label for="python1">1</label><input type="radio" name="python"
								value="1" id="python1" ${user_languages['Python'] == 1 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="python2">2</label><input type="radio" name="python"
								value="2" id="python2" ${user_languages['Python'] == 2 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="python3">3</label><input type="radio" name="python"
								value="3" id="python3" ${user_languages['Python'] == 3 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="python4">4</label><input type="radio" name="python"
								value="4" id="python4" ${user_languages['Python'] == 4 ? 'checked' : ''}>
						</div>
					</td>
					<td><span class="label-lang"><c:out value="${sql}" /></span></td>
					<td>
						<div class="vertical-label">
							<label for="sql1">1</label><input type="radio" name="sql"
								value="1" id="sql1" ${user_languages['Sql'] == 1 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="sql2">2</label><input type="radio" name="sql"
								value="2" id="sql2" ${user_languages['Sql'] == 2 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="sql3">3</label><input type="radio" name="sql"
								value="3" id="sql3" ${user_languages['Sql'] == 3 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="sql4">4</label><input type="radio" name="sql"
								value="4" id="sql4" ${user_languages['Sql'] == 4 ? 'checked' : ''}>
						</div>
					</td>
				</tr>
				<tr>
					<td><span class="label-lang"><c:out value="${swift}" /></span></td>
					<td>
						<div class="vertical-label">
							<label for="swift1">1</label><input type="radio" name="swift"
								value="1" id="swift1" ${user_languages['Swift'] == 1 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="swift2">2</label><input type="radio" name="swift"
								value="2" id="swift2" ${user_languages['Swift'] == 2 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="swift3">3</label><input type="radio" name="swift"
								value="3" id="swift3" ${user_languages['Swift'] == 3 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="swift4">4</label><input type="radio" name="swift"
								value="4" id="swift4" ${user_languages['Swift'] == 4 ? 'checked' : ''}>
						</div>
					</td>
					<td><span class="label-lang"><c:out value="${js}" /></span></td>
					<td>
						<div class="vertical-label">
							<label for="js1">1</label><input type="radio" name="js" value="1"
								id="js1" ${user_languages['JavaScript'] == 1 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="js2">2</label><input type="radio" name="js" value="2"
								id="js2" ${user_languages['JavaScript'] == 2 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="js3">3</label><input type="radio" name="js" value="3"
								id="js3" ${user_languages['JavaScript'] == 3 ? 'checked' : ''}>
						</div>
						<div class="vertical-label">
							<label for="js4">4</label><input type="radio" name="js" value="4"
								id="js4" ${user_languages['JavaScript'] == 4 ? 'checked' : ''}>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="buttons">
			<button type="submit" class="cancelbtn"
				onclick="resetFields()">
				<c:out value="${cancel}" />
			</button>
			<button type="submit" class="signupbtn">
				<c:out value="${finish}" />
			</button>
		</div>
	</form>
	<c:import url="jsp/fragment/footer.jsp"></c:import>
	<script src="js/registration.js"></script>
</body>
</html>