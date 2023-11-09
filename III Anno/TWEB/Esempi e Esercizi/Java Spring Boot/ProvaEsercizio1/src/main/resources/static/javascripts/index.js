function init(){
    try {
        const button = document.getElementById("submit");
        button.onclick = onSubmit;
    } catch (e) {}

    document.getElementById("xForm").style.display="block";
    document.getElementById("results").style.display="none";
}

function onSubmit(event) {
    const formArray = $("#xForm").serializeArray();
    const data = {};

    for (let index in formArray){
        data[formArray[index].name]= formArray[index].value;
    }

    axios.post('/calculateAge', data)
        .then((res) => {
            document.getElementById('results').innerHTML+="Il Risultato è: " + JSON.stringify(res.data);
            document.getElementById('xForm').style.display='none';
            document.getElementById('results').style.display='block';
        })
        .catch((err) => {
            alert(JSON.stringify(err));
        })

    event.preventDefault();
}