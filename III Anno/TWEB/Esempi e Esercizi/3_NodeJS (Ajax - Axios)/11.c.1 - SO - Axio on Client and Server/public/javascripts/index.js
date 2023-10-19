function init(){
    const form = document.getElementById('xForm');
    form.onsubmit = onSubmit;
}
/**
 * it sends an Ajax query using axios
 * @param url the url to send to
 * @param data the data to send (e.g. a Javascript structure)
 */
function sendAxiosQuery(url, data) {
    axios.post(url , data)
        .then (function (dataR) {
            document.getElementById('results').innerHTML= "The result is: "+dataR.data;
        })
        .catch( function (response) {
            alert (response.toJSON());
        })
}

/**
 * called when the submit button is pressed
 * @param event the submission event
 */
function onSubmit(event) {
    // The .serializeArray() method creates a JavaScript array of objects
    // https://api.jquery.com/serializearray/
    const formArray= $("form").serializeArray();
    const data={};
    for (let index in formArray){
        data[formArray[index].name]= formArray[index].value;
    }
    // const data = JSON.stringify($(this).serializeArray());
    sendAxiosQuery('/', data);
    // prevent the form from reloading the page (normal behaviour for forms)
    event.preventDefault()
}

