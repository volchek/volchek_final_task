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
	<form action="Controller" method="post">
		<div class="container">
		    <label><b>Логин</b></label>
		    <input type="text" name="login" required>
		
		    <label><b>Пароль</b></label>
		    <input type="password" name="password" required>
    	</div>
    	<div class="clearfix">
     		<button type="button"  class="cancelbtn">Отмена</button>
      		<button type="submit" class="signupbtn">Войти</button>
    	</div>
	</form>	
</body>
</html>