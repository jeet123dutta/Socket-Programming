var app = require("express")();
var http = require("http").Server(app);
var io = require("socket.io")(http);
var fs = require('fs');

app.get('/', function (req, res) {
	res.sendFile(__dirname + '/index.html');
});

var usernames = {};

var rooms = ['group1', 'group2', 'group3','group4'];

function check_key(value){	
	for(var key in usernames){
		if(usernames[key] == value){
			return key;
		}
	}
	return '';
}

io.sockets.on('connection', function (socket) {
	socket.on('sendchat', function (data) {
		io.sockets.in(socket.room).emit('updatechat', socket.username, data);
	});

	socket.on('sendBroadCast',function(data){
		io.sockets.emit('updatechat', socket.username, data);
	});

	socket.on('adduser', function(username){
		socket.username = username;
		usernames[username] = socket.id;
		socket.emit('store_username', username);
		socket.broadcast.emit('updatechat', 'SERVER', username + ' has connected '/* + socket.id*/);
		io.sockets.emit('updateusers', usernames);
	});

	socket.on('addgroup', function(group){
		socket.room = group;
		socket.join(group);
		socket.broadcast.to(group).emit('updatechat', 'SERVER', socket.username + ' has connected to this room');
		socket.emit('updaterooms', rooms, group);
	});

	socket.on('switchRoom', function(newroom){
		socket.leave(socket.room);
		socket.join(newroom);
		socket.emit('updatechat', 'SERVER', 'you have connected to '+ newroom);
		socket.broadcast.to(socket.room).emit('updatechat', 'SERVER', socket.username+' has left this room');
		socket.room = newroom;
		socket.broadcast.to(newroom).emit('updatechat', 'SERVER', socket.username+' has joined this room');
		socket.emit('updaterooms', rooms, newroom);
	});

	socket.on('disconnect', function(){
		delete usernames[socket.username];
		io.sockets.emit('updateusers', usernames);
		socket.broadcast.emit('updatechat', 'SERVER', socket.username + ' has disconnected');
		socket.leave(socket.room);
		
	});
	

	socket.on('check_user', function(asker, id){
		io.to(usernames[asker]).emit('msg_user_found', check_key(id));
	});

	socket.on('msg_user', function(usr, username, msg) {
		io.to(usernames[usr]).emit('msg_user_handle', username, msg);

		fs.writeFile("chat_data.txt", msg, function(err) {
			if(err) {
				console.log(err);
			}
		});
	});


});

http.listen(3000, function(){
	console.log("Server opened on port 3000");
});