/**
 * Validacao de Formulario
 * @author Iago Suzart 
 */

 function validar(){
	let nome = formContato.nome.value
	let telefone = formContato.telefone.value
	
	if(nome === ""){
		alert("Preencha o campo 'Nome'")
		formContato.nome.focus()
		return false
	}else if(telefone === ""){
		alert("Preencha o campo 'Telefone'")
		formContato.telefone.focus()
		return false
	}else {
		document.forms["formContato"].submit()
	}
 }
 