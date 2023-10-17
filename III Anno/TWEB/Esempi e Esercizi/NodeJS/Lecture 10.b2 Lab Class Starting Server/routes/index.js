const express = require('express');
const router = express.Router();
const fetch = require('node-fetch');

/* GET home page. */
router.route('/')
    .get(function (req, res){
        res.render('index', {title: 'My Class'});
    });

router.route('/add')
    .post(function (req, res) {

        let no1 = req.body.no1;
        let no2 = req.body.no2;

        let headers = {
            method: 'post',
            body: JSON.stringify(no1, no2),
            headers: {'Content-Type': 'application/json'},
            user-agent: 'localhost:3000'
        }
        fetch('http://localhost:3001/do_something', headers)
        .then(res => res.json()) // expecting a json response e.g. {field1: 'xxx', field 2: 'yyy'}
        .then(json =>
            res.render('index', {title: " results is: “+json.field2}))
        .catch(err =>
            res.render('index', {title: err}))


        //res.render('index', {title: 'Please implement the supporting server adding '+req.body.no1+'+'+req.body.no2})
    });

module.exports = router;