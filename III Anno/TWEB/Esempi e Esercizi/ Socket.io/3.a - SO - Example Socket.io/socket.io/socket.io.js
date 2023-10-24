module.exports = (io) => {
    io.on('connection', (socket) => {
        console.log('A user connected');

        socket.on('chat message', (msg, name) => {

        });
        socket.on('create or join conversation', (name) => {
        });

        socket.on('leave conversation', (name) => {
        });

        socket.on('disconnect', () => {
            console.log('A user disconnected');
        });
    });
};
