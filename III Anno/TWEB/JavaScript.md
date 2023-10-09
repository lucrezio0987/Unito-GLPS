## JavaScript

 - Inserimento del file JS alla pagina HTML
    <script src="myScript.js"></script>


 - Variabili e Funzioni in js

    ```JavaScript

        // PARE non bisogni fare le variabili globali

        function myFunction() {
            let x = 5;          // dichiarazione variabile per il blocco
            let y = x * 3;

            const pi = 3.14;    // Variabili costanti

            res = x + y         // Può essere usata prima di essere definita
            var res;            // Variabile globale (anche se dichairata nel blocco)
        }

    ```

    <button type="button" onclick="myFunction()">Try it</button>

 [Datatype]:
    • String
    • Number
    • Bigint
    • Boolean
    • Undefined
    • Null
    • Symbol
    • Object
        • An object
        • An array
        • A date


- Definizione array (2 modi):
    1. ``` const car = {type:"Fiat", model:"500", color:"white"};                   ```
    2. ``` const cars = new Array("Saab", "Volvo", "BMW");                          ```
    3. ``` const cars = []; cars[0]= "Saab"; cars[1]= "Volvo"; cars[2]= "BMW";      ```

 - Accesso agli oggetti (2 modi):
    1. ``` objectName.propertyName      ```
    2. ``` objectName["propertyName"]   ```

 - Definizione oggetto

    ```JavaScript
    const person = {
        firstName: "John",
        lastName : "Doe",
        id: 5566,
        fullName : function() {
            return this.firstName + " " + this.lastName;
        }
    };
    ```

    [.......]

    >  [https://www.w3schools.com/js/js_array_iteration.asp]

#### Array forEach()

```JavaScript
    const numbers = [45, 4, 9, 16, 25];
    let txt = "";
    numbers.forEach(myFunction);

    function myFunction(value, index, array) {
        txt += value + "<br>";
    }
```

#### Array Map()

```JavaScript
    const numbers1 = [45, 4, 9, 16, 25];
    const numbers2 = numbers1.map(myFunction);

    function myFunction(value, index, array) {
        return value * 2;
    }
```

#### Array flatMap()

```JavaScript
    const myArr = [1, 2, 3, 4, 5, 6];
    const newArr = myArr.flatMap((x) => x * 2);
```

#### Array filter()

```JavaScript
    const numbers = [45, 4, 9, 16, 25];
    const over18 = numbers.filter(myFunction);

    function myFunction(value, index, array) {
        return value > 18;
    }
```

#### Array reduce()  / reduceRight()

```JavaScript
    const numbers = [45, 4, 9, 16, 25];
    let sum = numbers.reduce(myFunction);

    function myFunction(total, value, index, array) {
        return total + value;
    }
```

#### Array every()

```JavaScript
    const numbers = [45, 4, 9, 16, 25];
    let allOver18 = numbers.every(myFunction);

    function myFunction(value, index, array) {
        return value > 18;
    }
```

#### Array some()

```JavaScript
    const numbers = [45, 4, 9, 16, 25];
    let someOver18 = numbers.some(myFunction);

    function myFunction(value, index, array) {
        return value > 18;
    }
```

#### Array indexOf() / lastIndexOf()
#### Array find() / findIndex()


----

[Booleans]:
 - solo 0 è falso (tutto il resto è vero, comrpeso la stringa vuota o -0)


[Loops]:
#### For
 - for
 - for/in
 - for/of
 - while
 - do/while

 [......]

----

### DOM (Document Object Module)
  > [https://www.w3schools.com/js/js_htmldom.asp]

 - Oggetto in HTML che permette di interagiere tra gli oggetti e il js
 - ...

