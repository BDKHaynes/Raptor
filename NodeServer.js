var dispatcher = require('httpdispatcher');
var http = require('http');
const PORT=8080;

function handleRequest(request, response){
	console.log(request.url);
	dispatcher.dispatch(request,response);
}
function getRandomInt(min,max) {
	return Math.floor(Math.random() * (max-min +1))+min;
}

dispatcher.onGet("/getTemperature", function(req, res) {
	console.log("Received Get Temperature Request");

	var PythonShell = require('python-shell');
	var temp = 0;
	PythonShell.run('readtemperature.py', function (err,results) {
		console.log(results);
		temp = results;
		res.writeHead(200, {'Content-Type': 'text/plain'});
		res.end(temp);
		});
});
var server = http.createServer(handleRequest);
server.listen(PORT, function(){
	console.log("Server listening on: http://localhost:%s", PORT);
});


