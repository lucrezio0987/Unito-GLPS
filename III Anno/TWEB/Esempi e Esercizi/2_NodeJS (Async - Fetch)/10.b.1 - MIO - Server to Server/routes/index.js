var express = require('express');
var router = express.Router();

//-------------------------------------------

var bodyParser = require('body-parser');
router.use( bodyParser.json() );

//-------------------------------------------

router.post('/add', function(req, res, next) {

  let no1 = parseInt(req.body.no1);
  let no2 = parseInt(req.body.no2)

  res.json({result: no1 + no2});

});

module.exports = router;
