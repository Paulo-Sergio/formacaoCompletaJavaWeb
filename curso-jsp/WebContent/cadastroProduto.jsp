<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de produto</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>

	<center>
		<h1>Cadastro de produto</h1>
		<h3 style="color: orange;">${msg}</h3>
	</center>
	
	<form action="ProdutoServlet" method="POST" id="formProduto">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td class="label">Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" value="${produto.id}" /></td>
					</tr>
					<tr>
						<td class="label">Nome:</td>
						<td><input type="text" id="nome" name="nome" value="${produto.nome}" /></td>
					</tr>
					<tr>
						<td class="label">Quantidade:</td>
						<td><input type="text" id="quantidade" name="quantidade" value="${produto.quantidade}" /></td>
					</tr>
					<tr>
						<td class="label">Valor:</td>
						<td><input type="text" id="valor" name="valor" value="${produto.valor}"></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="submit" value="Cancelar"
								onclick="document.getElementById('formProduto').action = 'ProdutoServlet?acao=reset'" />
							<input type="submit" value="Salvar" />
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	
	<div class="container">
		<table class="responsive-table">
			<caption>Produtos cadastrados</caption>
			<thead>
				<tr>
					<th>#</th>
					<th>Nome</th>
					<th>Quantidade</th>
					<th>Valor R$</th>
					<th>Delete</th>
					<th>Editar</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${produtos}" var="p">
					<tr>
						<td><c:out value="${p.id}"></c:out></td>
						<td><c:out value="${p.nome}"></c:out></td>
						<td><c:out value="${p.quantidade}"></c:out></td>
						<td><c:out value="${p.valor}"></c:out></td>
						<td>
							<a href="ProdutoServlet?acao=editar&id=${p.id}">
								<img alt="Editar" title="Editar" src="resources/img/editar.png" width="20px" height="20px">
							</a>
						</td>
						<td>
							<a href="ProdutoServlet?acao=deletar&id=${p.id}">
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
		if (document.getElementById("nome").value == '') {
			alert('Informe o Nome');
			return false;
		} else if (document.getElementById("quantidade").value == '') {
			alert('Informe o Quantidade');
			return false;
		} else if (document.getElementById("valor").value == '') {
			alert('Informe o Valor R$');
			return false;
		}
		return true;
	}
	</script>

</body>
</html>