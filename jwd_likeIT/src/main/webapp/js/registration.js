function checkPasswords() {
  var  psw1 = document.getElementById("psw1").value;
  var  psw2 = document.getElementById("psw2").value;
  var  tooltip = document.getElementById("psw-error");

  if  (psw1 != psw2) {
    tooltip.innerHTML = "Введенные пароли не совпадают";
    return false;
  }
  else {
    tooltip.innerHTML = "";
    return true;
  }
}

function addEmail(){
  var  firstEmail = document.getElementById("email");
  var secondEmail = document.createElement("div");
  secondEmail.id = "second-email";

  var label = document.createElement("label");
  var text =  document.createElement("span");
   text.innerHTML = "Еще email";

  var emailInput = document.getElementById("first-email").cloneNode(true);
  emailInput.name = "second-email";
  label.appendChild(text);
  label.appendChild(document.createElement("br"));
  label.appendChild(emailInput);

  var button = document.createElement("span");
  button.innerHTML = '<button id="remove-email" onclick="removeEmail()">-</button>';
  secondEmail.appendChild(label);
  secondEmail.appendChild(button);

  firstEmail.appendChild(secondEmail);

  document.getElementById("add-email").style.display = "none";
}

function removeEmail(){
  var parent = document.getElementById("email");
  var child = document.getElementById("second-email");
  parent.removeChild(child);
  document.getElementById("add-email").style.display = "inline";
}
