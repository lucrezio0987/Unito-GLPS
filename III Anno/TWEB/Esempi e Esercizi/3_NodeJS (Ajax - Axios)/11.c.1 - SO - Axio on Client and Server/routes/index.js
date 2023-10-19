const express = require('express');
const router = express.Router();
const axios = require('axios')

/* GET home page. */
router.route('/')
    .get (function(req, res) {
      res.render('index', {title: 'Express'});
    })

    .post(function  (req, res) {
        let firstNo = req.body.no1;
        let secondNo = req.body.no2;
        if (isNaN(firstNo) || isNaN(secondNo)) {
            res.setHeader('Content-Type', 'application/json');
            res.status(403).json({error: 403, reason: 'One of the numbers is invalid'});
        } else {
            axios.post('http://localhost:3000/add',
                {firstNumber: firstNo, secondNumber: secondNo})
                .then(json =>
                    res.json(json.data.result))
                .catch(err => {
                    res.setHeader('Content-Type', 'application/json');
                    res.status(505).json(err)
                })

        }
    });

module.exports = router;
