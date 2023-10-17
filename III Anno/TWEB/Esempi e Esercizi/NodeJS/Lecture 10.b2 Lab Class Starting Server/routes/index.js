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
        res.render('index', {title: 'Please implement the supporting server adding '+req.body.no1+'+'+req.body.no2})
    });

module.exports = router;