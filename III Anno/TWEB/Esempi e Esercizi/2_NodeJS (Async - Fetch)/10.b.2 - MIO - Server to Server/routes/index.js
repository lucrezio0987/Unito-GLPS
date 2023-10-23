const express = require('express');
const router = express.Router();
const fetch = require('node-fetch');

/* GET home page. */
router.route('/')
    .get(function (req, res){
        res.render('index', {title: 'My Class'});
    })
    .post(function (req, res) {
//        res.render('index', {title: 'Please implement the supporting server adding '+req.body.no1+'+'+req.body.no2})
        let no1 = req.body.no1;
        let no2 = req.body.no2;

        let Headers = {
            method: 'post',
            body: JSON.stringify( {no1:no1, no2:no2} ),
            headers: { 'Content-Type': 'application/json' },
        }

        fetch('http://localhost:3002/add', Headers)
            .then(res => res.json())
            .then(json => res.render('index', {title: " results is: " + json.result}))
            .catch(err => res.render('index', {title: " ERRORE: " + err}));

    });

module.exports = router;
