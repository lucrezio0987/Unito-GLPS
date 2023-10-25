
exports.init = function(io) {

  // the chat namespace
  const chat= io
      .of('/chat')
      .on('connection', function (socket) {
    try {
      /**
       * it creates or joins a room
       */
      socket.on('create or join', function (room, userId) {
        socket.join(room);
        chat.to(room).emit('joined_Chat', room, userId);
      });

      socket.on('chat', function (room, userId, chatText) {
        chat.to(room).emit('chat_Chat', room, userId, chatText);
      });

      socket.on('disconnect', function(){
        console.log('someone disconnected');
      });
    } catch (e) {
    }
  });

  // @todo insert here the /news namespace
  // it will have something like
  // const news= io
  //       .of('/news')
  // and then it will have the declarations of socket.on...
  // if you want to  broadcast to the other participants only,
  // instead of
  //     chat.to(room).emit
  // use
  //     socket.broadcast.to(room).emit

  const news= io
      .of('/news')
      .on('connection', function (socket) {
        try {

          socket.on('create or join', function (room, userId) {
            socket.join(room);
            socket.broadcast.to(room).emit('joined_News', room, userId);
          });

          socket.on('chat', function (room, userId, chatText) {
            socket.broadcast.to(room).emit('chat_News', room, userId, chatText);
          });

          socket.on('disconnect', function(){
            console.log('someone disconnected');
          });
        } catch (e) {
        }
      });
}
