var express = require('express');
var router = express.Router();
const fs = require('fs')

/* GET home page. */
router.get('/', function (req, res, next) {
    res.render('index', {title: 'Express'});
});


function error_function(err, res){
  if (err) {
    console.error(err)
    res.writeHead(500, {'Content-Type': 'text/plain'});
    res.end('image does not exist');
    return;
  }
}

router.get('/get_photos', function (req, res, next) {
    // fs.access it checks if a file exists or not
    fs.access('./public/images/image1.png', fs.F_OK, (err) => { error_function(err, res);
        fs.access('./public/images/image2.png', fs.F_OK, (err) => { error_function(err, res);
            fs.access('./public/images/image3.png', fs.F_OK, (err) => { error_function(err, res);
                res.writeHead(200, {'Content-Type': 'text/plain'});
                res.end('all images exist');
            })
        })
    })
});

module.exports = router;
