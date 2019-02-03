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
		<h3 style="color: orange;">${msg}</h3>
	</center>
	
	<form action="UsuarioServlet" method="POST" id="formUsuario">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td class="label">Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" value="${usuario.id}" /></td>
					</tr>
					<tr>
						<td class="label">Login:</td>
						<td><input type="text" id="login" name="login" value="${usuario.login}" /></td>
					</tr>
					<tr>
						<td class="label">Nome:</td>
						<td><input type="text" id="nome" name="nome" value="${usuario.nome}" /></td>
					</tr>
					<tr>
						<td class="label">Senha:</td>
						<td><input type="password" id="senha" name="senha" value="${usuario.senha}" /></td>
					</tr>
					<tr>
						<td class="label">Telefone:</td>
						<td><input type="text" id="telefone" name="telefone" value="${usuario.telefone}"></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="submit" value="Cancelar"
								onclick="document.getElementById('formUsuario').action = 'UsuarioServlet?acao=reset'" />
							<input type="submit" value="Salvar" />
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	
	<div class="container">
		<table class="responsive-table">
			<caption>Usuários cadastrados</caption>
			<thead>
				<tr>
					<th>Id</th>
					<th>Login</th>
					<th>Nome</th>
					<th>Fone</th>
					<th>Editar</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${usuarios}" var="u">
					<tr>
						<td><c:out value="${u.id}"></c:out></td>
						<td><c:out value="${u.login}"></c:out></td>
						<td><c:out value="${u.nome}"></c:out></td>
						<td><c:out value="${u.telefone}"></c:out></td>
						<td>
							<a href="UsuarioServlet?acao=editar&id=${u.id}">
								<img alt="Editar" title="Editar" src="resources/img/editar.png" width="20px" height="20px">
							</a>
						</td>
						<td>
							<a href="UsuarioServlet?acao=deletar&id=${u.id}">
								<img alt="Excluir" title="Excluir" src="resources/img/excluir.png" width="20px" height="20px">
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
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