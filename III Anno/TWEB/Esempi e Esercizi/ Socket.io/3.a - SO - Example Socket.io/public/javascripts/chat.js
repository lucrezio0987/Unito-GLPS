const socket = io();
let myName = "";
let mySurname ="";
let currentRoom ="";

/**
 * called by body onload, it sets up the buttons actions and sets the display to form visibile
 * and conversion invisible
 */
function init() {
    const messages = document.getElementById('messages');
    const messageInput = document.getElementById('messageInput');
    const messageButton = document.getElementById('messageButton');
    const formButton = document.getElementById("form-btn");

    messageButton.addEventListener('click', () => {
        //@todo here we should extract the message from the form
        //@todo here we should send the message via socket
        messageInput.value = '';
    });


    formButton.addEventListener('click', (event) => {
        // extract the information from the form
        getDataFromForm();
        document.getElementById("form_container").style.display = 'none';
        document.getElementById("message_container").style.display = 'block';
        document.getElementById('logout').style.display='block';
        event.preventDefault()
    });

    let logoutButton = document.getElementById('logout');
    logoutButton.addEventListener('click', (event) => {
        // @todo here we should send a leave message through the socket
    })

    // this makes sure that the message is accepted if the user presses enter while typing
    let field = document.getElementById('messageInput');
    field.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            // @todo here we should share the message through the socket
            messageInput.value = '';
            return false;
        }
    });

    // here we receive the message through the socket
    socket.on('chat message', (msg, name) => {
        insertMessage(messages, name+ ": "+msg);
    });

    // here we receive the join message via the socket
    socket.on('create or join conversation', (name) => {
        insertMessage(name+ " has joined the conversation")
    });

    myName = localStorage.getItem('my_name');
    mySurname = localStorage.getItem('my_surname');
    currentRoom= localStorage.getItem('room');
    if (myName) {
        document.getElementById('name').value= myName;
        document.getElementById('surname').value= mySurname;
        document.getElementById('room').value= currentRoom;
    }
    document.getElementById("form_container").style.display = 'block';
    document.getElementById("message_container").style.display = 'none';
    document.getElementById('logout').style.display='none';
}

function getMyFullName(){
    return myName+" "+mySurname
}

/**
 * it extracts the information about name, surname and room from the form
 */
function getDataFromForm(){
    myName= document.getElementById('name').value;
    mySurname= document.getElementById('surname').value;
    currentRoom= document.getElementById('room').value;
    localStorage.setItem('my_name', myName);
    localStorage.setItem('my_surname', mySurname);
    localStorage.setItem('room', currentRoom);
}

/**
 * it inserts a message as a list item in the conversation div
 * @param conversation_div
 * @param message
 */
function insertMessage(conversation_div, message){
    const li = document.createElement('li');
    li.textContent = message;
    conversation_div.appendChild(li);
}