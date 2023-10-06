let counter;
let divs= [];

function init(){
    counter = 0;
    divs = [];

    divs = document.getElementById('container').children;
    for (let div of divs)
        div.style.visibility = "hidden";

    divs[counter].style.visibility = "visible";
}

function getRandomColor() {

    const rnd_number = Math.floor(Math.random() * 16777215);
    const hexColor = rnd_number.toString(16);
    const paddedHexColor = hexColor.padStart(6, "0");
    return '#'+ paddedHexColor.toString();
}

function displayDiv() {
    divs[counter].style.visibility = "hidden";
    counter = ++counter%divs.length;
    divs[counter].style.visibility = "visible";
}

function addADiv() {
    let newDiv = document.createElement('div');
    newDiv.classList.add('col-sm');
    newDiv.style.backgroundColor = getRandomColor();
    newDiv.style.visibility = 'hidden';
    newDiv.id = 'col_' + (divs.length + 1);

    let containerDiv = document.getElementById('container');
    containerDiv.appendChild(newDiv);
    divs = document.getElementById('container').children;
}
