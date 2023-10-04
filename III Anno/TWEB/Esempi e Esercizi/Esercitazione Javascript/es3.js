let color;
function myFunction_3_over(elID){
    color = document.getElementById(elID).style.backgroundColor;
    document.getElementById(elID).style.backgroundColor = 'red';

}
function myFunction_3_out(elID){
    document.getElementById(elID).style.backgroundColor = color;
}