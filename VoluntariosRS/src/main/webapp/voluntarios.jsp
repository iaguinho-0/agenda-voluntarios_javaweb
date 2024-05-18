<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="utf-8"%>
    <%@ page import = "model.JavaBeans" %>
    <%@ page import="java.util.ArrayList" %>
    <%
    @SuppressWarnings ("unchecked")
    	ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("voluntarios");
    %>
<!DOCTYPE html>
<html lang = "pt-br">
<head>
<meta charset="utf-8">
<title>Voluntários RS</title>
<link rel="icon" href="images/favicon.png">
<link rel="stylesheet" href="style.css"> 
</head>
<body>
	<div>
	<h1>Agenda de voluntários</h1>	
		
		<div class="relatorio">
			<button class="Botao1"> <a href="novo.html">Novo voluntário</a> </button>
			<button class="Botao4"> <a href="report">Relatório</a> </button>
		</div>
		
	<table id="tabela">
		<thead>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Telefone</th>
				<th>E-mail</th>
				<th>Opções</th>
			</tr>
		</thead>
		
		<tbody>
		<% for (int i = 0; i < lista.size(); i++) { %>
			<tr>
				<td><%=lista.get(i).getId()%></td>
				<td><%=lista.get(i).getNome()%></td>
				<td><%=lista.get(i).getTelefone()%></td>
				<td><%=lista.get(i).getEmail()%></td>
				<td class="opcoes"> <button class="Botao2"> <a href="select?id=<%= lista.get(i).getId()%>">Editar </a> <img alt="edit" src="images/edit_icon.png"> </button>
				<button class="Botao3"> <a href="javascript: confirmar(<%= lista.get(i).getId()%>)">Excluir </a> <img alt="trash" src="images/lixo_icon.png"> </button></td>

			</tr>
		<%} %>
		</tbody>
	</table>
	</div>
	<script src="scripts/confirmador.js"></script>
</body>
</html>