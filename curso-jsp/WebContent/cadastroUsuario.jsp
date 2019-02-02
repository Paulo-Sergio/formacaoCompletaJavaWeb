<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>

	<center>
		<h1>Cadastro de usuário</h1>
	</center>
	
	<form action="UsuarioServlet" method="POST">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" value="${usuario.id}" /></td>
					</tr>
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login" value="${usuario.login}" /></td>
					</tr>
					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha" value="${usuario.senha}" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar" /></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	
	<div class="container">
		<table class="responsive-table">
			<caption>Usuários cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Login</th>
				<th>Nome</th>
				<th>Editar</th>
				<th>Delete</th>
			</tr>
			<c:forEach items="${usuarios}" var="usuario">
				<tr>
					<td><c:out value="${usuario.id}"></c:out></td>
					<td><c:out value="${usuario.login}"></c:out></td>
					<td><c:out value="${usuario.senha}"></c:out></td>
					<td>
						<a href="UsuarioServlet?acao=editar&login=${usuario.login}">
							<img alt="Editar" title="Editar" src="resources/img/editar.png" width="20px" height="20px">
						</a>
					</td>
					<td>
						<a href="UsuarioServlet?acao=deletar&login=${usuario.login}">
							<img alt="Excluir" title="Excluir" src="resources/img/excluir.png" width="20px" height="20px">
						</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("login").value == '') {
				alert('Informe o Login');
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert('Informe o Senha');
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert('Informe o Nome');
				return false;
			} else if (document.getElementById("telefone").value == '') {
				alert('Informe o Telefone');
				return false;
			}
			
			return true;
		}
	</script>

</body>
</html>