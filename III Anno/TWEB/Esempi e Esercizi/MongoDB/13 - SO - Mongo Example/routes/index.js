var express = require('express');
var router = express.Router();
const controller = require("../controllers/characters")

router.post('/insert', async (req, res, next) => {
  try {
    const results = await controller.insert(req.body);
    res.json(results);
  } catch (error) {
    res.status(500).json({ error: error });
  }
});



router.post('/query', async (req, res, next) => {
  try {
    const results = await controller.query(req.body);
    res.json(results);
  } catch (error) {
    res.status(500).json({ error: error });
  }
});

module.exports = router;
