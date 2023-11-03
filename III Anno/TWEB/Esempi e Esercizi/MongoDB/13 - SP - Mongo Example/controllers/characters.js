const Model = require('../models/characters');

function insert(body) {
    return new Promise((resolve, reject) => {
        const mongoObj = new Model(body);
        mongoObj.save()
            .then(results => {
                resolve(results);
            })
            .catch(error => {
                console.log(error);
                reject(error);
            });
    });
}

module.exports.insert = insert;


function query(body) {
    //@todo implement the new controller method (using find here)
    // please note the syntax for find is Model.find
    // - no need to create an object as we did in the other method
    // Model.find(<condition>, <specific fields in case>)
    //    .then()
    //    .catch()
}

module.exports.query = query;
