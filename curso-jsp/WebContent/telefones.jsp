<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>

	<a href="index.jsp">Inicio</a>
	<a href="LoginServlet">Sair</a>

	<center>
		<h1>Cadastro de telefones</h1>
		<h3 style="color: orange;">${msg}</h3>
	</center>

	<form action="TelefoneServlet" method="POST" id="formTelefone" onsubmit="return validarCampos()">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td class="label">Usuário:</td>
						<td><input type="text" readonly="readonly" id="idUsuario" name="idUsuario" value="${usuarioEscolhido.id}" /></td>
						<td><input type="text" readonly="readonly" id="loginUsuario" name="loginUsuario" value="${usuarioEscolhido.login}" /></td>
					</tr>
					
					<tr>
						<td>Número:</td>
						<td><input type="text" id="numero" name="numero" value="${telefone.numero}" /></td>
						<td>
							<select id="tipo" name="tipo">
								<option>Casa</option>
								<option>Celular</option>
								<option>Trabalho</option>
							</select>
						</td>
					</tr>

					<tr>
						<td></td>
						<td>
							<input type="button" value="Voltar" onclick="location.href = 'UsuarioServlet'" />
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
					<th>Número</th>
					<th>Tipo</th>
					<th>Ecluir</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${telefones}" var="t">
					<tr>
						<td><c:out value="${t.id}"></c:out></td>
						<td><c:out value="${t.numero}"></c:out></td>
						<td><c:out value="${t.tipo}"></c:out></td>
						<td>
							<a href="TelefoneServlet?acao=deletar&id=${t.id}&idUsuario=${usuarioEscolhido.id}">
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
			if (document.getElementById("numero").value == '') {
				alert('Informe o numero de telefone');
				return false;
			} else if (document.getElementById("tipo").value == '') {
				alert('Informe o tipo de telefone');
				return false;
			}

			return true;
		}
	</script>

</body>
</html>