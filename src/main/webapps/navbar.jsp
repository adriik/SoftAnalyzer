<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="examplePackage.UserBean" %>
<html>
<head lang="pl-PL">
    <meta charset="UTF-8">
</head>
<body>
<form action="${pageContext.request.contextPath}/LogoutServlet" id="myForm" method="post">

	<ul>

	<li>
		<img src="${pageContext.request.contextPath}/img/logo_title.png" id="logo"></li>
	<li>

	<c:choose>
	    <c:when test="${empty currentSessionUser}">

	    </c:when>    
	    <c:otherwise>
		    
		    	<% String sessionRole = ((UserBean)session.getAttribute("currentSessionUser")).getRole(); %>
			    <% if(sessionRole.equals("admin")){ %>
		 			<li><a href="${pageContext.request.contextPath}/logged/userLogged.jsp">Dodaj XMI</a></li>
	  				<li><a href="${pageContext.request.contextPath}/logged/admin/manage.jsp">Dodaj użytkownika</a></li>
	  				<li><a href="${pageContext.request.contextPath}/logged/admin/remove.jsp">Usuń użytkownika</a></li>
	  			<% }else { %>
			      <li><a href="${pageContext.request.contextPath}/logged/userLogged.jsp">Dodaj XMI</a></li>
		    	<% } %>
				
	       
		</c:otherwise>
	</c:choose>	  
	  
	  <c:choose>
	    <c:when test="${empty currentSessionUser}">
	        <li style="float:right" class="active"><a class="active" href="index.jsp">Zaloguj</a></li>
	        <br />
	    </c:when>    
	    <c:otherwise>
	        <li style="float:right" onclick="myForm.submit();"><a class="active">Wyloguj</a></li>
	        <br />
	    </c:otherwise>
	</c:choose>
	
	  
	</ul>
</form>
</body>
</html>