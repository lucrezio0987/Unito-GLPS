const express = require('express');
const router = express.Router();
const fetch = require('node-fetch');

/* GET home page. */
router.route('/')
    .get (function(req, res) {
      res.render('index', {title: 'Express'});
    })

    .post(function  (req, res) {
        let firstNo = req.body.no1;
        let secondNo = req.body.no2;
        fetch('http://localhost:3000/add', {
            method: 'post',
            body: JSON.stringify({firstNumber: firstNo, secondNumber: secondNo}),
            headers: {'Content-Type': 'application/json'},
        })
            .then (res => res.json())
            .then(json =>
                res.render('index', {title: " results is: "+json.result}))
            .catch(err =>
                res.render('index', {title: err}))
    });

module.exports = router;
