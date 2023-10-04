let divs = []
let counter;

/**
 * it generates a random colour string
 * @returns {string} a random colour string
 */
function getRandomColour() {
    // Generate a random number between 0 and 16777215, which is the maximum value for a hexadecimal color code.
    const randomNumber = Math.floor(Math.random() * 16777215);
    // Convert the random number to a hexadecimal string.
    const hexColor = randomNumber.toString(16);
    // Pad the hexadecimal string with zeros if it is less than 6 characters long.
    const paddedHexColor = hexColor.padStart(6, "0");
    // Return the hexadecimal color code with a leading hash symbol.
    return `#${paddedHexColor}`;
}

/**
 * it adds a div to the container div and recomputes the divs array
 */
function addADiv() {
    let newDiv = document.createElement('div');
    newDiv.style.backgroundColor = getRandomColour()
    newDiv.style.width= '180px';
    newDiv.style.height= '180px';
    newDiv.classList.add('col-sm')
    newDiv.style.visibility='hidden';
    newDiv.id = 'col_' + (divs.length + 1);
    let containerDiv = document.getElementById('container');
    containerDiv.appendChild(newDiv);
    divs = document.getElementById('container').children;
}

/**
 * it makes the current div invisible and the next id visible
 * it increments counter as well
 */
function displayDiv() {
    divs[counter].style.visibility = "hidden";
    counter = ++counter%divs.length
    divs[counter].style.visibility = "visible";
}

/**
 * init function making all elements invisible but the first one
 * it initialises the array divs by taking all the children of the container div
 */
function init() {
    counter = 0;
    divs= [];
    // Get all the divs in the container div.
    divs = document.getElementById('container').children;
    for (let div of divs)
        div.style.visibility = "hidden";
    divs[counter].style.visibility = "visible";
    document.getElementById('button').onclick= displayDiv;
    document.getElementById('button_add').onclick= addADiv;
}
