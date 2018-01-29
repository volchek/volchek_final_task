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
	var result = str.replace(/<pre>/g, "<pre><code>").replace(/<\/pre>/g, "<\/code><\/pre>");
	document.getElementById("question").value = result;
	return result;
}

