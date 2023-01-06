$(document).ready(function() {
 //on ready
});



async function registerUser(){
  let data = {};

  data.name = document.getElementById('txtName').value;
  data.lastName = document.getElementById('txtLastname').value;
  data.email = document.getElementById('txtEmail').value;
  data.password = document.getElementById('txtPassword').value;

  let repeatPassword = document.getElementById('txtRepeatPassword').value;

  if(repeatPassword != data.password){
    alert('The password is different');
    return;
  }

  const request = await fetch('api/users', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  });
  alert("The account was created succesfully");
  window.location.href='login.html';
}
