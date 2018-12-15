<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="examplePackage.UserBean"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
   "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<link rel="stylesheet" href="../style/style.css">
<script class="jsbin"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<title>Zalogowałeś się pomyślnie</title>
<link rel="shortcut icon" href="../img/logo_icon.png" />
</head>

<body>
	<%@ include file="../navbar.jsp" %>
	
	<div class="alert success">
		<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
		 Zapisano nowy plik XMI.
	</div>
	
	<div class="imgcontainer">
		<img alt="" src="../img/logo_xmi.png" style="width: auto; height: 300px">
	</div>

	<div class="container">
		<form action="../FileUploadServlet" method="post"
			enctype="multipart/form-data">


			<div class="image-upload-wrap">
				<input class="file-upload-input" name="fileName" type='file'
					onchange="readURL(this);" accept=".xmi" />
				<div class="drag-text">
					<h3>Przeciągnij lub wybierz plik</h3>
				</div>
			</div>
			<br>
			<button class="file-upload-btn" type="submit">Wyślij</button>
		</form>
	</div>
	
	<%@ include file="../footer.jsp" %>
</body>

</html>