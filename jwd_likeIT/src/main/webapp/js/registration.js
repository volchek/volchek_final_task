function checkPasswords() {
	var psw1 = document.getElementById("psw1").value;
	var psw2 = document.getElementById("psw2").value;
	var tooltip = document.getElementById("psw-error");

	if (psw1 != psw2) {
		tooltip.innerHTML = "Введенные пароли не совпадают";
		return false;
	} else {
		tooltip.innerHTML = "";
		return true;
	}
}

function addEmail() {
	var firstEmail = document.getElementById("email");
	var secondEmail = document.createElement("div");
	secondEmail.id = "second-email";

	var label = document.createElement("label");
	var text = document.createElement("span");
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

function removeEmail() {
	var parent = document.getElementById("email");
	var child = document.getElementById("second-email");
	parent.removeChild(child);
	document.getElementById("add-email").style.display = "inline";
}

function resetFields() {
	var elements = document.getElementsByTagName("input");
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "date") {
			elements[i].value = "1970-01-01";
		} else if (elements[i].name == "avatar" || elements[i].name == "email" || elements[i].name == "second-email") {
			elements[i].value = "";
		} else if (elements[i].type == "text" || elements[i].type == "password") {
			elements[i].value = "";
		} else if (elements[i].type == "radio") {
			elements[i].checked = false;
		}
	}
	var tooltip = document.getElementById("psw-error");
	tooltip.innerHTML = "";

	var opt = document.getElementById("status").options;
	for (var i = 0; i < opt.length; i++) {
		opt[i].selected = false;
	}
}
