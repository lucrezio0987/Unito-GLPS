function init() {
    document.getElementById('btn_1').onclick = close;
    document.getElementById('btn_2').onclick = close;
    document.getElementById('btn_3').onclick = close;

    document.getElementById('p_1').onmouseover = color_in;
    document.getElementById('p_2').onmouseover = color_in;
    document.getElementById('p_3').onmouseover = color_in;

    document.getElementById('p_1').onmouseout = color_out;
    document.getElementById('p_2').onmouseout = color_out;
    document.getElementById('p_3').onmouseout = color_out;

    document.getElementById('img_1').onclick = image;
    document.getElementById('img_2').onclick = image;
    document.getElementById('img_3').onclick = image;
}
function close() {
    document.getElementById(this.parentNode.id).style.display='none';
}

function color_in() {
    document.getElementById(this.id).style.backgroundColor = 'grey';
}
function color_out() {
    document.getElementById(this.id).style.backgroundColor = 'white';
}
function image() {
    if(document.getElementById(this.id).style.height.localeCompare('150px')) {
        document.getElementById(this.id).style.width = "60%"
        document.getElementById(this.id).style.height = "150px";
        document.getElementById(this.id).style.alignSelf = "center";
    } else {
        document.getElementById(this.id).style.width = "100%";
        document.getElementById(this.id).style.height = "250px";
    }
}