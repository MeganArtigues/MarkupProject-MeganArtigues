function removeErrorMsg() {
	var element = document.getElementById('msg');
	if (element != null){
		document.getElementById('errorDiv').removeChild(element)
	}
}

function addErrorMsg(error) {
	var p = document.createElement("p");
	var text = document.createTextNode(error);
	p.appendChild(text);
	p.setAttribute("style", "color: red;")
	var element = document.getElementById("errorDiv");
	element.appendChild(p);
}
