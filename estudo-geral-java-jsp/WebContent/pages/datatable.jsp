<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DataTable jQuery</title>

<link rel="stylesheet" href="../resources/datatable/jquery.dataTables.min.css">
<script type="text/javascript" src="../resources/js/jquery-3.2.1.min.js"></script>	
<script src="../resources/datatable/jquery.dataTables.min.js"></script>
	
</head>
<body>

	<table id="minhatabela" class="display" cellspacing="0" width="100%">
		<thead>
			<tr>
				<th>Id</th>
				<th>Login</th>
			</tr>
		</thead>
	</table>
	
	<script type="text/javascript">
		$(document).ready(function() {// faz o processamento na hora que abre
		    $('#minhatabela').DataTable({
		        "processing": true,
		        "serverSide": true,
		        "ajax": "../CarregarDadosDataTableServlet" // URL de retorno dos dados do servidor (RESPOSTA DO SERVIDOR)
		    });
		});
	</script>
</body>
</html>