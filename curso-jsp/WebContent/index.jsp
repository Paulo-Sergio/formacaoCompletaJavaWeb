<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/estilo.css" />
</head>
<body>

	<div class="login-page">
		<div class="form">
			<form action="LoginServlet" method="POST">
				Login: <input type="text" id="login" name="login" /> <br />
				Senha: <input type="text" id="senha" name="senha" /> <br />
				
				<input type="submit" value="Logar" />
			</form>
		</div>
	</div>

</body>
</html>