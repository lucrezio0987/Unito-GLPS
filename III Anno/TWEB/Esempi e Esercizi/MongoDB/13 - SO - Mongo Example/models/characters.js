const mongoose = require('mongoose');

const Character = new mongoose.Schema(
    {
        first_name: {type: String, required: true, max: 100},
        family_name: {type: String, required: true, max: 100},
        dob: {type: Number, required: true, max: new Date().getFullYear()},
    }
);

// Virtual for a character's age
Character.virtual('age')
    .get(function () {
        const currentDate = new Date().getFullYear();
        return currentDate - this.dob;
    });

// setting the virtual property
Character.set('toObject', {getters: true, virtuals: true});

// exporting the model
module.exports = mongoose.model('Character', Character);
