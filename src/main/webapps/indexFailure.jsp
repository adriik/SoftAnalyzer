<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="style/style.css">
<link rel="shortcut icon" href="img/logo_icon.png" />
</head>
<body>
	<%@ include file="navbar.jsp" %>
	
	<div class="alert failure">
		<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
	 	Niepoprawne dane logowania.
	</div>
	
	<form action="LoginServlet">
		<div class="imgcontainer">
			<img alt="" src="img/logo.png" style="width: auto; height: 300px">
		</div>

		<div class="container">
			<label for="uname"><b>Nazwa użytkownika</b></label> <input
				type="text" placeholder="Wprowadź nazwę użytkownika" name="un" pattern="[A-Za-z]{3,10}" title="od 3 do 10 liter"
				required> 
				
				<label for="psw"><b>Hasło</b></label> <input
				type="password" placeholder="Wprowadź hasło" name="pw" pattern="[A-Za-z]{3,10}" title="od 3 do 10 znaków" required>

			<button type="submit">Zaloguj</button>
		</div>
	</form>
	
	
<%@ include file="footer.jsp" %>
</body>
</html>