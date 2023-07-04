
///////////////////
// CONTENT SETUP //
///////////////////
function getRoot() {
    return document.getElementById("root");
}

function makeSectionsFromHeaders() {
    var root = getRoot();
    var sections = [];
    var references = new Map();
    var index = 1;
    var section = null;
    while (root.childNodes.length > 0) {
	var node = root.childNodes[0];
	root.removeChild(node);
	if (node.nodeName == "H1") {
	    if (section != null) sections.push(section);
	    section = document.createElement("SECTION");
	    if (node.hasAttribute("id"))
		references.set(node.getAttribute("id"), index);
	    index++;
	}
	if (section != null) section.appendChild(node);
    }
    if (section != null) sections.push(section);
    for (var i = 0; i < sections.length; i++)
	root.appendChild(sections[i]);
    return references;
}

function resolveSlideNumbers(node, references) {
    if (node.nodeName == "A" && node.textContent == "slide" && node.hasAttribute("href")) {
	var href = node.getAttribute("href");
	var sharp = href.lastIndexOf('#');
	if (sharp >= 0) {
	    var id = href.substring(sharp + 1);
	    if (references.has(id)) {
		var n = references.get(id);
		node.textContent = "slide " + n;
	    } else {
		node.textContent = "slide ???";
	    }
	}
    }

    if (node.nodeType == 1) {
	// se è un elemento, visita ricorsivamente tutti i figli
	for (var i = 0; i < node.childNodes.length; i++) {
	    var child = node.childNodes[i];
	    resolveSlideNumbers(child, references);
	}
    }
}

function numberOfSlides() {
    return getRoot().childNodes.length;
}

//////////
// MAIN //
//////////

function initialize_slider() {
    var references = makeSectionsFromHeaders();
    console.log(references);
    resolveSlideNumbers(getRoot(), references);
}
