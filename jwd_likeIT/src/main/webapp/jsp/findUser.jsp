<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>LikeIT</title>
	<link rel="stylesheet" type="text/css" href="../css/styles.css">
<body>
<body>
	<form action="../Controller" method="get">
		<input type="hidden" name="command" value="FIND_BY_LOGIN" />
		<div class="container">
		    <label><b>Логин</b></label>
		    <input type="text" name="login" required>
		 </div>
		 <div class="clearfix">
     		<button type="button"  class="cancelbtn" onclick="location.href='../index.jsp'">Отмена</button>
      		<button type="submit" class="signupbtn">Найти</button>
    	</div>
	</form>	
</body>
</html>