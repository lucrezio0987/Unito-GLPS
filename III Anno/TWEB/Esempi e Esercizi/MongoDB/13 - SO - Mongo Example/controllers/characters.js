const Model = require('../models/characters');

function insert(body) {
    return new Promise((resolve, reject) => {
        const mongoObj = new Model(body);
        mongoObj.save()
            .then(results => {
                resolve(results);
            })
            .catch(error => {
                reject(error);
            });
    });
}

module.exports.insert = insert;


function query(body) {
    return new Promise((resolve, reject) => {
        Model.find(body)
            .then(results => {
                results.forEach((character) => {
                    character._id = null;
                });
                resolve(results);
            })
            .catch(error => {
                reject(error);
            });
    });
}

module.exports.query = query;
