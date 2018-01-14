function resetFields() {
	document.getElementById("title").value = "";

	var elements = document.getElementsByTagName("input");
	for (var i = 0; i < elements.length; i++) {
		elements[i].value = "";
	}

	var x = document.getElementsByClassName("pell-content")[0];
	x.innerHTML = "";
}

function replacePreTags() {
	var text = document.getElementById("text-output").innerHTML;
	var new_text = text.replace(/<\/pre><pre>/g, "\n");
	return new_text;
}	

function addCodeTag(str) {
	var result = str.replace(/<pre>/g, "<pre><code>");
	result = result.replace(/<\/pre>/g, "<\/code><pre>");
	document.getElementById("question").value = result;
	return result;
}

function addFields(btnId1, btnId2, id1, id2) {
	var elem1 = document.getElementById(id1);
	var elem2 = document.getElementById(id2);
	if (elem1.style.display == "inline") {
		document.getElementById(id2).style.display = "inline";
		document.getElementById(btnId1).style.display = "none";
		document.getElementById(btnId2).style.display = "inline-block";
	} else {
		document.getElementById(btnId2).style.display = "inline-block";
		document.getElementById(id1).style.display = "inline";
	}
}

function removeFields(btnId1, btnId2, id1, id2) {
	var elem2 = document.getElementById(id2);
	if (elem2.style.display == "none") {
		var elem1 = document.getElementById(id1);
		elem1.style.display = "none";
		document.getElementById(btnId1).style.display = "inline-block";
		document.getElementById(btnId2).style.display = "none";
		elem1.value = "";
	} else {
		document.getElementById(btnId1).style.display = "inline-block";
		elem2.style.display = "none";
		elem2.value = "";
	}
}
