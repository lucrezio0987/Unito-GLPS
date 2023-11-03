var express = require('express');
var router = express.Router();
const controller
    = require("../controllers/characters")

router.post('/insert', async (req, res, next) => {
  try {
    const results = await controller.insert(req.body);
    res.json(results);
  } catch (error) {
    res.status(504).json({ error: error.errors });
  }
});


router.post('/query', async (req, res, next) => {
  //@todo implement the route query here
});

module.exports = router;
