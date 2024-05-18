/**
 * Confirmacao de exclusao de um voluntario
 * @autor Iago Suzart
 * @param id 
 */ 

 function confirmar(id){
	let resposta = confirm("Confirma que deseja excluir deste contato?")
	if(resposta === true){
		window.location.href = "delete?id=" + id
	}
 }