var dispatcher = require('httpdispatcher');
var http = require('http');
var url = require('url');
var gpio = require("rpi-gpio");
const PORT=8080;

function ledOn(pin){
	gpio.setup(pin, gpio.DIR_OUT, function(){
		gpio.write(pin, 1, function(err){
			if (err) throw err;
			console.log('Turned pin on: ' + pin);
		});
	});
}

function ledOff(pin){
	gpio.setup(pin, gpio.DIR_OUT, function(){
		gpio.write(pin, 0, function(err){
			if (err) throw err;
			console.log('Turned pin off: ' + pin);
		});
	});
}

function handleRequest(request, response){
	console.log(request.url);
	dispatcher.dispatch(request,response);
}

function getRandomInt(min,max) {
	return Math.floor(Math.random() * (max-min +1))+min;
}

temperatureNameSpace = function() {
	var desiredTemperature = 0;
	var currentTemperature = 0;
	function setDesired(desired) {desiredTemperature = desired;}
	function setCurrent(current) {currentTemperature = current;}
	function getDesired() {return desiredTemperature;}
	function getCurrent() {return currentTemperature;}
	return{
		setDesired:setDesired,
		setCurrent:setCurrent,
		getDesired:getDesired,
		getCurrent:getCurrent
	}
}();

var intervalVar = setInterval(periodicFunction, 5000);

function periodicFunction() {
	var desiredTemperature = temperatureNameSpace.getDesired();
	var currentTemperature = temperatureNameSpace.getCurrent();
	
	if (!desiredTemperature || !currentTemperature) {
		console.log("Temperature not set");
		ledOff(33);
		ledOff(35);
		return;
	}

	console.log("Desired temp: " + desiredTemperature);
	console.log("Current temp: " + currentTemperature);
	
	if (parseFloat(desiredTemperature) < parseFloat(currentTemperature)) {
		console.log("Lowering temperature to the desired set point");
		ledOff(33);
		ledOn(35);
	} else if (parseFloat(desiredTemperature) > parseFloat(currentTemperature)) {
		console.log("Raising temperature to the desired set point");
		ledOff(35);
		ledOn(33);
	} else {
		ledOff(33);
		ledOff(35);
	}
}

dispatcher.onGet("/getTemperature", function(req, res) {
	console.log("Received Get Temperature Request");

	var PythonShell = require('python-shell');
	PythonShell.run('readtemperature.py', function (err,results) {
		if (typeof(results) != 'undefined' && results != null) {
			console.log(results[0]);
			temperatureNameSpace.setCurrent(results[0]);
		} else {
			console.log("Error Reading Temperature Value: " + err);
		}
		res.writeHead(200, {'Content-Type': 'text/plain'});
		res.end(temperatureNameSpace.getCurrent());
	});
});

dispatcher.onGet("/setTemperature", function(req, res) {
        console.log("Received Set Temperature Request");
	var queryData = url.parse(req.url, true).query;
	temperatureNameSpace.setDesired(queryData.param);
	console.log("Setting temperature to " + queryData.param);
	res.writeHead(200, {'Content-Type': 'text/plain'});
	res.end();
});

var server = http.createServer(handleRequest);
server.listen(PORT, function(){
	console.log("Server listening on: http://localhost:%s", PORT);
});


