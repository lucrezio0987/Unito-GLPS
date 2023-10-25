module.exports = (io) => {
    io.on('connection', (socket) => {
        console.log('A user connected');

        socket.on('chat message', (room, msg, name) => {
            io.to(room).emit('chat message',msg, name)
        });
        socket.on('create or join conversation', (room, name) => {
            socket.join(room);
            io.to(room).emit('create or join conversation', name);
        });

        socket.on('leave conversation', (room, name) => {
            io.to(room).emit('leave conversation', name);
            socket.leave(room);
        });

        socket.on('disconnect', () => {
            console.log('A user disconnected');
        });
    });
};
