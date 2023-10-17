var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'My class', login_is_correct: true});
});

router.post('/submit', function(req, res) {
  let login = req.body.login;
  let password = req.body.password;

  if (login === 'paula')
    res.render('welcome', {title: login});
  else
    res.render('index', {title: 'My class', login_is_correct: false})
})

module.exports = router;
