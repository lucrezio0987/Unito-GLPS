var express = require('express');
var router = express.Router();
var fs = require('fs');

/**
 * it returns a promise reaading an image file and returns the image in base64 format
 * @param file path
 * @returns {Promise<unknown>}
 */
function readPromise(file) {
  return new Promise(function (resolve, reject) {
    fs.readFile(file, 'base64', (error, base64String) => {
      if (error)
        reject(error);
      else
        resolve(base64String);
    })
  });
}

/**
 * this route reads the file image1 and returns its base 64 in json (or an error)
 */
router.get('/one', function (req, res, next) {
  readPromise('./public/images/image12.png')
      .then(imagesData => {
        console.log('found')
        res.json(imagesData)
      })
      .catch( error => {
        console.log('error: ' + error)
        res.json(error)
      });
});


/**
 * an async function returning a promise all for loading a set of images
 * @param imageList a list of image paths
 * @returns {Promise<*[]>}
 */
async function getAllImages(imageList) {
  let promisesList = []
  for (let image of imageList)
    promisesList.push(readPromise(image));
  return await Promise.all(promisesList);

}
router.get('/all', function (req, res, next) {
  getAllImages(['./public/images/image1.png', './public/images/image2.png','./public/images/image3.png'])
      .then(imagesData => {
        console.log('how many images? ' + imagesData.length)
        res.json(imagesData)
      })
      .catch( error => {
        console.log('error: ' + error)
        res.json(error)
      });
});

module.exports = router;
