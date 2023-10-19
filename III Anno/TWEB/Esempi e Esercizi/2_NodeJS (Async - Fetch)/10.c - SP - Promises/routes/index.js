var express = require('express');
var router = express.Router();
const fs = require('fs')

/* GET home page. */
router.get('/', function (req, res, next) {
    res.render('index', {title: 'Express'});
});
router.get('/get_photos', function (req, res, next) {
    const path = './public/images/image1.png'
    // fs.access it checks if a file exists or not
    fs.access(path, fs.F_OK, (err) => {
        if (err) {
            console.error(err)
            res.writeHead(500, {'Content-Type': 'text/plain'});
            res.end('image does not exist');
            return;
        }
        const path = './public/images/image2.png'
        fs.access(path, fs.F_OK, (err) => {
            if (err) {
                console.error(err)
                res.writeHead(500, {'Content-Type': 'text/plain'});
                res.end('image does not exist');
                return;
            }
            const path = './public/images/image3.png'
            fs.access(path, fs.F_OK, (err) => {
                if (err) {
                    console.error(err)
                    res.writeHead(500, {'Content-Type': 'text/plain'});
                    res.end('image does not exist');
                    return;
                }
              res.writeHead(200, {'Content-Type': 'text/plain'});
                              res.end('all images exist');
            })
        })
    })
});

module.exports = router;
