let currentIndex=0;
let colouredSquares= [];

/**
 * given a colour, it creates a div under the element with id=row
 * @param row the parent element to insert the newly created div
 * @param colour the colour of the div
 */
function createColouredDiv(row, colour) {
    let cellDiv= document.createElement('div');
    cellDiv.classList.add("col");
    row.appendChild(cellDiv);
    let newDiv = document.createElement('div')
    newDiv.classList.add('coloured-square');
    newDiv.style.backgroundColor=colour;
    cellDiv.appendChild(newDiv);
}

/**
 * calloed in onload, it initialises the button's onclick and then connects to the
 * server to receive the colours to display
 */
function init(){
    document.getElementById( "next_button"  ).onclick = getNext;
    document.getElementById( "reset_button" ).onclick = resetDivs;
    resetDivs();
}


/**
 * it connects to the server to get which coloured div to show
 * the server will return an index which is to be found in  colouredSquares
 * as a side effect all the coloured elements are hidden bar the one indicated by the server
 */
function getNext(){
    url='/increment'
    axios.post(url , {index: currentIndex, max_number_of_elements: colouredSquares.length})
        .then (function (dataR) {
            // the result is is dataR.data;
            let newIndex= dataR.data.new_index;
            displaySquare(newIndex);
            currentIndex= newIndex;
        })
        .catch( function (response) {
            alert (response.toJSON());
            for (let index =0; index< colouredSquares.length; index++) {
                colouredSquares[index].style.display = 'block';
            }
        })
}

/**
 * it asks the server to regenerate the coloured divs instructions that are
 * then implemented by creating the coloured divs here on the client
 */
function resetDivs(){
    axios.get('/initial_elements', {})
        .then (data => {
            data = data.data;
            //data now contains {initial_index: 0, colours: ['red', 'yellow', 'blue', 'green']}
            currentIndex=data.initial_index;
            let row= document.getElementById("squares_row")
            // remove all children (if any)
            row.replaceChildren();
            // add the requested coloured divs, e.g. ['red', 'yellow', 'blue', 'green']
            for (let colour of data.colours){
                createColouredDiv(row, colour)
            }
            colouredSquares= row.children;
        })
}

function displaySquare(currentIndex){
    for (let index =0; index< colouredSquares.length; index++) {
        if (index==currentIndex)
            colouredSquares[index].style.display='block';
        else
            colouredSquares[index].style.display='none';
    }
}
