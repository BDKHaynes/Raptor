var dispatcher = require('httpdispatcher');
var http = require('http');
const PORT=8080;

function handleRequest(request, response){
	console.log("Handling request");
	dispatcher.dispatch(request,response);
}
function getRandomInt(min,max) {
	return Math.floor(Math.random() * (max-min +1))+min;
}

dispatcher.onGet("/getTemperature", function(req, res) {
	console.log("Received Get Temperature Request");
	res.writeHead(200, {'Content-Type': 'text/plain'});
	var random = getRandomInt(70,80);
	res.end(random.toString());
});
var server = http.createServer(handleRequest);
server.listen(PORT, function(){
	console.log("Server listening on: http://localhost:%s", PORT);
});


