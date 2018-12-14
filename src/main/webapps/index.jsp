<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="style/style.css">

</head>
<body>
	<%@ include file="navbar.jsp" %>
	<form action="LoginServlet">
		<div class="imgcontainer">
			<img alt="" src="img/logo.png" style="width: auto; height: 300px">
		</div>

		<div class="container">
			<label for="uname"><b>Nazwa użytkownika</b></label> <input
				type="text" placeholder="Wprowadź nazwę użytkownika" name="un"
				required> <label for="psw"><b>Hasło</b></label> <input
				type="password" placeholder="Wprowadź hasło" name="pw" required>

			<button type="submit">Zaloguj</button>
		</div>
	</form>

</body>
</html>