var express = require('express');
var router = express.Router();



/* GET home page. */
router.get('/add', function(req, res, next) {
  res.setHeader('Content-Type', 'application/json');
  let dati = JSON.parse(req.body);
  let no1 = dati.no1;
  let no2 = dati.no2;
  let somma = no1+no2;
  res.send(JSON.stringify({somma: somma}));
});





module.exports = router;
