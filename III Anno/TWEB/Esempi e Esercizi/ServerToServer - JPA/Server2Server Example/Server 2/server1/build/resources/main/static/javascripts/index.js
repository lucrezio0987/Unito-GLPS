function init(){
    const button = document.getElementById('submit');
    button.onclick = onSubmit;
    document.getElementById('results').style.display='none';
    document.getElementById('xForm').style.display='block';
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
            document.getElementById('results').style.display='block';
            document.getElementById('xForm').style.display='none';
        })
        .catch( function (response) {
            alert (response.message);
        })
}

/**
 * called when the submit button is pressed
 * @param event the submission event
 */
function onSubmit(event) {
    let data  = {
        number1: Number(document.getElementById('number1').value),
        number2: Number(document.getElementById('number2').value)
    }
    // const data = JSON.stringify($(this).serializeArray());
    sendAxiosQuery('/add', data);
    // prevent the form from reloading the page (normal behaviour for forms)
    event.preventDefault()
}

