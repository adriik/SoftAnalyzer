<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../style/style.css">
<script src="../../js/select.js"></script>
<title>Zarządzaj użytkownikami</title>
<link rel="shortcut icon" href="../../img/logo_icon.png" />
</head>
<body>

	<%@ include file="../../navbar.jsp" %>
	<form action="../../ManageServlet">
		<div class="imgcontainer">
			<img alt="" src="../../img/add_user_icon.png" style="width: auto; height: 300px">
		</div>


		<div class="container">
			<label for="firstName"><b>Imię użytkownika</b></label> 
				<input type="text" placeholder="Wprowadź imię użytkownika" name="fn" style="text-transform: capitalize;" pattern="[A-Za-z]{3,20}" title="od 3 do 20 liter" required> 
			<label for="lastName"><b>Nazwisko</b></label> 
				<input type="text" placeholder="Wprowadź nazwisko użytkownika" name="ln" style="text-transform: capitalize;" pattern="[A-Za-z]{3,20}" title="od 3 do 20 liter" required>
		
			<label for="uname"><b>Nazwa użytkownika</b></label> 
				<input type="text" placeholder="Wprowadź nazwę użytkownika" name="un" pattern="[A-Za-z]{3,10}" title="od 3 do 10 liter" required> 
			<label for="psw"><b>Hasło</b></label> 
				<input type="password" id="psw" placeholder="Wprowadź hasło" name="pw" pattern="[A-Za-z]{3,10}" title="od 3 do 10 znaków" required>
			<label for="psw2"><b>Powtórz hasło</b></label> 
				<input type="password" id="psw2" placeholder="Wprowadź ponownie hasło" name="pw2" required>
			
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
	
	<script type="text/javascript">
		var password = document.getElementById("psw")
		  , confirm_password = document.getElementById("psw2");
	
		function validatePassword(){
		  if(password.value != confirm_password.value) {
		    confirm_password.setCustomValidity("Hasło się nie zgadza");
		  } else {
		    confirm_password.setCustomValidity('');
		  }
		}
	
		password.onchange = validatePassword;
		confirm_password.onkeyup = validatePassword;
	</script>

<%@ include file="../../footer.jsp" %>
</body>
</html>