var express = require('express');
var router = express.Router();

/**
 * it generates a table of elements called "elem.row.column", e.g. "elem.1.1"
 * @param numberOfRows number of rows in the table
 * @param numberOfColumns number of columns in the table
 * @returns {*[]} e.g. #0000000
 */
function generateNewTable(numberOfRows, numberOfColumns) {
  let prefix = "elem.";
  let table = [];
  for (let row =0; row< numberOfRows; row++) {
    let rowElem = [];
    for (let col = 0; col < numberOfColumns; col++)
      rowElem[col]= prefix + row + "." + col;
    table[row] = rowElem;
  }
  return table;
}


/**
 * it generates a random int between min and max
 * @param min the min number
 * @param max the max number
 * @returns {number} the random int
 */

function getRandomInt(min, max) {
  let val = Math.floor(Math.random() * max);
  if (val < min) val = min;
  return val;
}

/**
 * it generates a random colours list
 * @returns *[] colours list
 */


/* GET home page. */
router.get('/', (req, res) => {
  res.redirect('index.html');
});

/**
 * it creates the array of colours to display on the client
 */
router.get('/initial_elements', (req, res) => {
  let min = getRandomInt(1, 10);
  let max = min+getRandomInt(11, 20);
  let table = generateNewTable(getRandomInt(20, 30), max+getRandomInt(1, 3));
  res.json({min: min, max: max, table: table});
});

module.exports = router;
