// Call the dataTables jQuery plugin
$(document).ready(function() {
  actualizarUsuarioEmail();
  $('#usuarios').DataTable();
  updateEmailUser();
});

function actualizarUsuarioEmail(){
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

async function cargarUsuarios(){
  const request = await fetch('api/users', {
    method: 'GET',
    headers: getHeaders()
  });

  const usuarios = await request.json();

  let listadoHTML = '';
  for(let usuario of usuarios){

      let deleteButton = '<a href="#" onclick="deleteUser('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
      let phone = usuario.telephone == null ? '-' : usuario.telephone;
      let userHTML = '<tr><td>'+usuario.id+'</td><td>'+usuario.name+' '+usuario.lastName+'</td><td>'
            +usuario.email+'</td><td>'+phone
            +'</td><td>'+deleteButton+'</td></tr>';
      listadoHTML+=userHTML;
  }
   document.querySelector('#usuarios tbody').outerHTML= listadoHTML;
}

function getHeaders(){
return {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Autorization': localStorage.token
    }
}

async function deleteUser(id){

    if(!confirm('Do you want to delete?')){
        return;
    }

   const request = await fetch('api/users/'+id, {
       method: 'DELETE',
       headers: getHeaders()
     });
     location.reload();
}
