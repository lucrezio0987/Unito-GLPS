/**
 * async await makes an asyc function synchronous
 */

async function fetchImage() {
    let response = await fetch('http://localhost:3000/get_photos');
    if (response.ok) { // if HTTP-status is 200-299
        // get the response body (the method explained below)
        document.getElementById('title').innerHTML= "OK!!";
    } else {
        document.getElementById('title').innerHTML= "Images not found!!";
    }
}
