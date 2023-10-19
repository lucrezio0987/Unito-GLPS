let express = require('express');
let router = express.Router();

/* GET home page. */
router.get('/', function (req, res, next) {
    res.render('index', {title: 'My Class', login_is_correct: true});
});


/* POST from form. */
router.post('/welcome', function (req, res, next) {
    let login = req.body.login;
    let password = req.body.password;

    if (login == 'paula') {
        res.render('welcome', {title: login, login_is_correct: true});
    } else {
        res.render('index', {title: 'My Class', login_is_correct: false});
    }


});

module.exports = router;
