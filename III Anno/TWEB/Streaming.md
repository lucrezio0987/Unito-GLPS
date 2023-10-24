## Streaming API
...

## Localstorage
...

## WebSocket
  - usa il protocollo TCP
  - nelllo streaming il server continua a mandare fino a quando la destinazione non choude la connessione, nel socket entrambi possono scambiare dati.
  - event based

  [blobs]: è un tipo di dato di cui il protocollo HTTP non ne conosce i lconetnunto (es: immaigni)

  - si possono creare cominicazioni client-client

### Struttura
 - l'express-route contiene il codice della sockete apre una connessione IO
 - a un certo punto la io fa una socket.emit(messaggio, param)
 ```Javascript
  router.get('/', function(req, res, next) {
    res.render('index', { title: 'My Chat' });

    io.on('connection', function(socket){
      socket.on (‘message’,function (param){
        //...
        socket.emit ("message" param);
      });
    });

    //...

    socket.on('disconnect', function(socket){
      //...
    });

  });

 ```


 - il server invece di avere una root ha de codice di questo tipo:
 ```HTML
 <script src=“/socket.io/socket.io.js"> </script>
 ```
 ```Javascript
    var socket = io();
    socket.emit (‘message’, param);
    socket.on (‘message’, function (param){...});
 ```

### Brodcast
  in questo modo il client è sicurao che il server riceva il messaggio.

 ```Javascript
  io.on("connection", (socket) => {
    socket.brodcast.emit("messaggio");
  });
 ```
 - l'idea è di definire delle "rooms", che limita il raggio delle brodcast.

  ```Javascript
    io.on("connection", (socket) => {
      socket.join("room");
      //...
      socket.leave();
    });

    io.to("room").emit("messaggio");
  ```


