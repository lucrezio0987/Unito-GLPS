function myFunction1_1(array) {
    array.forEach((el) => document.getElementById("results_1_1").innerHTML += (el + " "));
}

function myFunction1_2(array) {
    let sum = 0;
    array.forEach((el) => sum += el*el);
    document.getElementById("results_1_2").innerHTML = sum;

    // il prof usa reduce
}

function myFunction1_3() {
    const obj = [
        {name:"gym_1", surname:"bro", age:1},
        {name:"gym_2", surname:"bro", age:2},
        {name:"gym_3", surname:"bro", age:3},
        {name:"gym_4", surname:"bro", age:4}
    ];

    obj.forEach((o) => {
        document.getElementById("results_1_3").innerHTML += o.name + " ";
        document.getElementById("results_1_3").innerHTML += o.surname + " ";
        document.getElementById("results_1_3").innerHTML += o.age + " | ";
    })

}

function allFunction() {
    let array = [1, 2, 3, 4];
    myFunction1_1(array);
    myFunction1_2(array);
    myFunction1_3();
}