<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Voluntários RS</title>
<link rel="icon" href="images/favicon.png">
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<div>
		<img alt="voluntariado" src="images/pen_icon.png">
		<h1>Editar voluntário</h1>
		<form name="formContato" action="update">
			<table>
				<tr>
					<td><input type="text" name="id" id="caixa2" readonly value="<% out.print(request.getAttribute("id")); %>"></td>
				</tr>
				<tr>
					<td><input type="text" name="nome" placeholder="Altere o nome" class="Caixa1" value="<% out.print(request.getAttribute("nome")); %>"  ></td>
				</tr>

				<tr>
					<td><input type="text" name="telefone"
						placeholder="Altere o telefone" class="Caixa1" value="<% out.print(request.getAttribute("telefone")); %>"></td>
				</tr>

				<tr>
					<td><input type="text" name="email"
						placeholder="Altere o e-mail" class="Caixa1" value="<% out.print(request.getAttribute("email")); %>" ></td>
				</tr>
			</table>
			<input type="button" value="Salvar" class="Botao1"
				onclick="validar()">
		</form>
		<script src="scripts/validador.js"></script>
	</div>
</body>
</html>