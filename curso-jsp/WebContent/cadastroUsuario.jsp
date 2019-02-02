<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de usuário</title>
</head>
<body>

	<h1>Cadastro de usuário</h1>
	
	<form action="UsuarioServlet" method="POST">
		<table>
			<tr>
				<td>Login:</td>
				<td><input type="text" id="login" name="login" /></td>
			</tr>
			<tr>
				<td>Senha:</td>
				<td><input type="password" id="password" name="password" /></td>
			</tr>
		</table>
		<input type="submit" value="Salvar" />
	</form>
	
	<table>
		<c:forEach items="${usuarios}" var="usuario">
			<tr>
				<td><c:out value="${usuario.login}"></c:out></td>
				<td><c:out value="${usuario.senha}"></c:out></td>
				<td><a href="UsuarioServlet?acao=delete&login=${usuario.login}">Excluir</a></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>