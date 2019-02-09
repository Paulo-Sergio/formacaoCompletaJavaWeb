<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de usu�rio</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
<script src="resources/js/jquery-3.2.1.min.js"></script>
</head>
<body>

	<a href="index.jsp">Inicio</a>
	<a href="LoginServlet?acao=logout">Sair</a>

	<center>
		<h1>Cadastro de usu�rio</h1>
		<h3 style="color: orange;">${msg}</h3>
	</center>

	<form action="UsuarioServlet" method="POST" id="formUsuario" onsubmit="return validarCampos()" enctype="multipart/form-data">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td class="label">C�digo:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" value="${usuario.id}" /></td>
						<td class="label">Login:</td>
						<td><input type="text" id="login" name="login" value="${usuario.login}" /></td>
					</tr>

					<tr>
						<td class="label">Nome:</td>
						<td><input type="text" id="nome" name="nome" value="${usuario.nome}" /></td>
						<td class="label">Senha:</td>
						<td><input type="password" id="senha" name="senha" value="${usuario.senha}" /></td>
					</tr>
					
					<tr>
						<td class="label">Telefone:</td>
						<td><input type="text" id="telefone" name="telefone" value="${usuario.telefone}"></td>
						<td class="label">CEP:</td>
						<td><input type="text" id="cep" name="cep" onblur="consultarCep()" value="${usuario.cep}"></td>
					</tr>

					<tr>
						<td class="label">Rua:</td>
						<td><input type="text" id="rua" name="rua" value="${usuario.rua}"></td>
						<td class="label">Bairro:</td>
						<td><input type="text" id="bairro" name="bairro" value="${usuario.bairro}"></td>
					</tr>
					<tr>
						<td class="label">Cidade:</td>
						<td><input type="text" id="cidade" name="cidade" value="${usuario.cidade}"></td>
						<td class="label">Estado:</td>
						<td><input type="text" id="estado" name="estado" value="${usuario.estado}"></td>
					</tr>
					<tr>
						<td class="label">IBGE:</td>
						<td><input type="text" id="ibge" name="ibge" value="${usuario.ibge}"></td>
						<td class="label">Foto:</td>
						<td><input type="file" id="foto" name="foto"></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="button" value="Cancelar"
								onclick="isEdicao() ? location.href = 'UsuarioServlet' : limparCampos()" />
							<input type="submit" value="Salvar" />
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Usu�rios cadastrados</caption>
			<thead>
				<tr>
					<th>Id</th>
					<th>Login</th>
					<th>Foto</th>
					<th>Nome</th>
					<th>Fones</th>
					<th>Editar</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${usuarios}" var="u">
					<tr>
						<td><c:out value="${u.id}"></c:out></td>
						<td><c:out value="${u.login}"></c:out></td>
						<td>
							<a href="UsuarioServlet?acao=download&id=${u.id}">
								<img src="<c:out value="${u.tempFotoUsuario}"/>" alt="Imagem usu�rio" title="Imagem usu�rio" width="32px" height="32px">
							</a>
						</td>
						<td><c:out value="${u.nome}"></c:out></td>
						<td>
							<a href="TelefoneServlet?idUsuario=${u.id}"> 
								<img alt="Telefones" title="Telefones" src="resources/img/telefone.png" width="20px" height="20px">
							</a>
						</td>
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
			console.log('validarCampos()');
			if (document.getElementById("login").value == '') {
				alert('Informe o Login');
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert('Informe o Nome');
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert('Informe o Senha');
				return false;
			}

			return true;
		}
		
		function isEdicao() {
			var id = document.getElementById("id").value;
			if (id == '' || id == 0) {
				console.log('idEdicao() false');
				return false;
			}
			return true;
		}
		
		function limparCampos() {
			document.getElementById("login").value = '';
			document.getElementById("nome").value = '';
			document.getElementById("senha").value = '';
			document.getElementById("telefone").value = '';
		}
		
		/* BUSCAR CEP VIA JQuery */
		function consultarCep() {
			var cep = $('#cep').val()
			
			$.getJSON("https://viacep.com.br/ws/"+cep+"/json", function(dados){
				if (!("erro" in dados)) {
					$("#rua").val(dados.logradouro)
					$("#bairro").val(dados.bairro)
					$("#cidade").val(dados.localidade)
					$("#estado").val(dados.uf)
					$("#ibge").val(dados.ibge)
				} else {
					alert("CEP n�o encontrado!")
				}
			})
		}
	</script>

</body>
</html>