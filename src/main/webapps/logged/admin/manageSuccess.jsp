<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../style/style.css">
<script src="../../js/select.js"></script>
<title>Zarządzaj użytkownikami</title>
</head>
<body>

	<%@ include file="../../navbar.jsp" %>
	
	<div class="alert success">
  		<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
 		 Dodano nowego użytkownika.
	</div>
	<form action="../../ManageServlet">
		<div class="imgcontainer">
			<img alt="" src="../../img/add_user_icon.png" style="width: auto; height: 300px">
		</div>
		
		<div class="container">
			<label for="firstName"><b>Imię użytkownika</b></label> 
				<input type="text" placeholder="Wprowadź imię użytkownika" name="fn" required> 
			<label for="lastName"><b>Nazwisko</b></label> 
				<input type="text" placeholder="Wprowadź nazwisko użytkownika" name="ln" required>
		
			<label for="uname"><b>Nazwa użytkownika</b></label> 
				<input type="text" placeholder="Wprowadź nazwę użytkownika" name="un" required> 
			<label for="psw"><b>Hasło</b></label> 
				<input type="password" placeholder="Wprowadź hasło" name="pw" required>
			<label for="psw2"><b>Powtórz hasło</b></label> 
				<input type="password" placeholder="Wprowadź ponownie hasło" name="pw2" required>
			
			<label for="role2"><b>Rola użytkownika</b></label>
			 
			
			    <div class="styled-select slate">
 					<select name="role">
						<option value="admin">Administrator</option>
						<option value="modelarz">Modelarz</option>
					</select>
				</div>

			<button type="submit">Dodaj użytkownika</button>
		</div>

	</form>

	<%@ include file="../../footer.jsp" %>
</body>
</html>