function init(){
    try {
        const button = document.getElementById('submit');
        button.onclick = onSubmit;
    } catch (e){};
    try {
        const buttonQuery = document.getElementById('submit_q');
        buttonQuery.onclick = onSubmitQuery;
    } catch (e){};

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
            document.getElementById('results').innerHTML= "The result is: "+JSON.stringify(dataR.data);
            document.getElementById('results').style.display='block';
            document.getElementById('xForm').style.display='none';
        })
        .catch( function (response) {
            alert (JSON.stringify(response));
        })
}

/**
 * called when the submit button is pressed
 * @param event the submission event
 */
function onSubmit(event) {
    onSubmitAux(event, '/calculateAge')
}

function onSubmitQuery(event) {
    onSubmitAux(event, '/query')
}

function onSubmitAux(event, url){
    // The .serializeArray() method creates a JavaScript array of objects
    // https://api.jquery.com/serializearray/
    const formArray= $("form").serializeArray();
    const data={};
    for (let index in formArray){
        data[formArray[index].name]= formArray[index].value;
    }
    // const data = JSON.stringify($(this).serializeArray());
    sendAxiosQuery(url, data);
    // prevent the form from reloading the page (normal behaviour for forms)
    event.preventDefault()
}
