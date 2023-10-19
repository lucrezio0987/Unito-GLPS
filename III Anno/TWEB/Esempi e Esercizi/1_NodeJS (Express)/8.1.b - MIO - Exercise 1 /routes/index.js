var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'My Class' });
});

router.get('/welcome', function(req, res, next) {
  res.render('welcome', { title: 'COM3504' });
});

module.exports = router;
