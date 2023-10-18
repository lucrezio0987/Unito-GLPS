function init(){
    document.getElementById('submit').onclick = submitForm;
    document.getElementById('back_to_login').onclick= showForm;
    showForm();
}

/**
 * it hides the results and shows the form
 */
function showForm(){
    document.getElementById("results_container").style.display='none';
    document.getElementById("form_container").style.display='block';

}

/**
 * it extracts the data from the form and returns it in the form of an object
 * @returns an object, e.g. {login: 'pippo', 'password': 'pluto'}
 */
function extractFormData() {
    // get all the form children that have an attribute name (they are just the imputs)
    let formElements = document.getElementById("my_form").children;
    let formData={};
    for (let ix = 0; ix < formElements.length; ix++) {
        if (formElements[ix].name) {
            // this assigns, e.g. {'login': 'pippo'} to the object form data
            // using the format object['login]='pippo'
            formData[formElements[ix].name] = formElements[ix].value;
        }
    }
    return formData;
}

/**
 * called when the form is submitted
 * it gets the results of the login from the server and shows the results
 * @param event the clicking event necessary to prevent default action
 */
function submitForm(event){
    let formData= extractFormData();
    axios.post('/form_submission', formData)
        .then (data=>{
            data = data.data;
            document.getElementById("form_container").style.display='none';
            document.getElementById("results_container").style.display='block';
            document.getElementById('result_div').innerHTML=data;
        })
        .catch(error => {
            alert('error!!!: '+error)
        })
    // prevent the form from reloading the page (normal behaviour for forms)
    // never forget thsi when you use axios!!
    event.preventDefault()
}