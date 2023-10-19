var express = require('express');
var router = express.Router();

/**
 * it generates a random colour string in hexadecimal (e.g #000000)
 * @returns {string}
 */
function getRandomColor() {
  const letters = '0123456789ABCDEF';
  let color = '#';
  for (let i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
}


/**
 * it generates a random int between 0 and max
 * @param max the max number
 * @returns {number} the random int
 */

function getRandomInt(max) {
  return Math.floor(Math.random() * max);
}

/**
 * it generates a random colours list
 * @returns *[] colours list
 */
function generateColoursList() {
  let number = getRandomInt(12);
  let colourArray= [];
  for (let ix=0; ix<number; ix++)
    colourArray.push(getRandomColor());
  return colourArray;
}

/* GET home page. */
router.get('/', (req, res) => {
  res.redirect('index.html');
});

/**
 * it creates the array of colours to display on the client
 */
router.get('/initial_elements', (req, res) => {
  res.json({initial_index: 0, colours: generateColoursList()});
});

/**
 * it returns which coloured div is to be displayed (it returns the index in the array)
 */
router.post('/increment', function (req, res){
  let index= req.body.index+1;
  index= index%req.body.max_number_of_elements;
  res.json({new_index: index})
})

module.exports = router;
