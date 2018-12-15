<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="examplePackage.ConnectionManager" %>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../style/style.css">
<script src="../../js/select.js"></script>
<title>Usuń użytkownika</title>
<link rel="shortcut icon" href="../../img/logo_icon.png" />
</head>
<body>
	<%@ include file="../../navbar.jsp" %>
	<form action="../../RemoveServlet">
	<div class="imgcontainer">
		<img alt="" src="../../img/remove_user_icon.png" style="width: auto; height: 300px">
	</div>
	<div class="container">
				<label for="role2"><b>Wybierz użytkownika do usunięcia</b></label>
				
				<% 
				Connection currentCon = ConnectionManager.getConnection();
				ResultSet rs = null;
						
				PreparedStatement pstmt = currentCon.prepareStatement(
						   "SELECT NazwaUzytkownika FROM Modelarz;");
				rs = pstmt.executeQuery();
				
				%>
				
				
			    <div class="styled-select slate">
 					<select name="userName">
						<%  while(rs.next()){ %>
            				<option><%= rs.getString(1)%></option>
        				<% } %>
					</select>
				</div>
	
				<button type="submit">Usuń użytkownika</button>
	</div>
	</form>
	<%@ include file="../../footer.jsp" %>
</body>
</html>