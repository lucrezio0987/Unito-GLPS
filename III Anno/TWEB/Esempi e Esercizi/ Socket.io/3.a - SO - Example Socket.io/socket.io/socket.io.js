module.exports = (io) => {
    io.on('connection', (socket) => {
        console.log('A user connected');

        socket.on('chat message', (room, msg, name) => {
            io.to(room).emit('chat message', msg, name); // Broadcast the message to all connected clients
        });
        socket.on('create or join conversation', (room, name) => {
            socket.join(room);
            io.to(room).emit('create or join conversation', name); // Broadcast the message to all connected clients
        });

        socket.on('leave room', (roomName, name) => {
            io.to(roomName).emit('chat message', "has left the conversation", name);
            socket.leave(roomName);
        });


        socket.on('disconnect', () => {
            console.log('A user disconnected');
        });
    });
};
