let divs
let counter;

/**
 * called when button is pressed. It hides the current div and displays the next
 * it cycles on the divs array and increments counter
 */
function displayDiv() {
    document.getElementById(divs[counter]).style.display = "none";
    counter = ++counter%divs.length
    document.getElementById(divs[counter]).style.display = "block";
}

/**
 * it adds a child to the container div and inserts it into the div array
 */
function addADiv() {
    let newDiv = document.createElement('div');
    newDiv.style.backgroundColor = 'yellow';
    newDiv.style.width= '180px';
    newDiv.style.height= '180px';
    newDiv.classList.add('col-sm')
    newDiv.style.display='none';
    let newDivId = 'col_'+(divs.length+1);
    newDiv.id = newDivId;
    let containerDiv = document.getElementById('container');
    containerDiv.appendChild(newDiv);
    divs[divs.length] =  newDivId;
}

function init(){
    counter = 0;
    divs= [];
    divs.push('col_1');
    divs.push('col_2');
    divs.push('col_3');
    divs.push('col_4');
    divs.forEach(function(div){
        document.getElementById(div).style.display = "none";
    })
    // for (let div of divs)
    //     document.getElementById(div).style.display = "none";
    document.getElementById(divs[counter]).style.display = "block";
    document.getElementById('button').onclick= displayDiv;
    document.getElementById('button_add').onclick= addADiv;
}