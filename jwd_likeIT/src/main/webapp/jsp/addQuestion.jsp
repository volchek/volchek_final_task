<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css">
<body>
<body>
	<c:import url="fragment/header.jsp"></c:import>
	<c:import url="menu.jsp"></c:import>
	<form>
		<input type="text" name="text"> <input type="submit">
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
</body>
</html>