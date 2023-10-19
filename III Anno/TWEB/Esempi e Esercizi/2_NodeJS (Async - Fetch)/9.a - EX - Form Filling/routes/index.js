let express = require('express');
let router = express.Router();

/* GET home page. */
router.get('/', function (req, res, next) {
    res.render('index', {title: 'My Class', login_is_correct: true});
});


/* POST from form. */
router.post('/send_name', function (req, res, next) {
    let name = req.body.name;
    let surname = req.body.surname;
    let complete_name = name+ " " + surname;
    res.render('welcome', {title: complete_name});

});

module.exports = router;
