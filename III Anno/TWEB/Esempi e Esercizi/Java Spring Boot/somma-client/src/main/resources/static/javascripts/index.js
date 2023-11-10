function init(){
    try {
        const button = document.getElementById("submit");
        button.onclick = onSubmit;
    } catch (e) {}
}

function onSubmit(event) {
    const formArray = $('#xForm').serializeArray();
    const data = {};

    for (let index in formArray)
        data[formArray[index].name] = formArray[index].value;

    axios.post('/somma', data)
        .then((res) => {
            document.getElementById("results").innerHTML="La somma è: " + JSON.stringify(res.data);
        })
        .catch((err) => {
            alert(JSON.stringify(err));
        })

    event.preventDefault();
}