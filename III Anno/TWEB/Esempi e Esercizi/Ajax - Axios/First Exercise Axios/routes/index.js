var express = require('express');
var router = express.Router();

/* GET home page. */
// router.get('/', function(req, res, next) {
//   res.render('index', { title: 'Express' });
// });

router.post('/form_submission', (req, res)=>{
  let login = req.body.login;
  let password = req.body.password;
  if (login==='pippo' && password==='pluto'){
    res.end("Correct!!!");
  } else {
    res.end("Incorrect!!!");
  }
})
module.exports = router;
