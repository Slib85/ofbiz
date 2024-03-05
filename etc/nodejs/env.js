// Includes
var url = require('url'),
	http = require('http'),
	querystring = require('querystring'),
	express = require('express'),
	request = require("request"),
	bodyParser = require('body-parser');

// Server
var env = express();
env.use(bodyParser.urlencoded({ extended: false }));

/**
 * Endpoint to get the geo location
 * @return {JSON}
 */
env.get('/getLocation', function(request, response) {
	response.set({
		'Content-Type'                : 'application/json',
		'Access-Control-Allow-Origin' : '*',
		'Cache-Control'               : 'public, max-age=432000'
	});

	//var ip = request.headers['X-Forwarded-For'];
	var ip = request.client.remoteAddress;

	var ipUrl = 'https://freegeoip.net/json/' + ip;
	var parsedURL = url.parse(ipUrl);

	//generate the options object for the post request
	var options = {
		hostname : parsedURL.host,
		path     : parsedURL.pathname,
		method   : 'GET',
		headers  : {
			'Content-Type'   : 'application/x-www-form-urlencoded'
		}
	};

	//create the request that will fetch xml from scene7 servers
	var resBody = '';
	var req = http.request(options, function(res) {
		res.setEncoding('utf8');
		res.on('data', function (chunk) {
			resBody += chunk;
		});
		res.on('end', function () {
			response.status(200).send(resBody);
		});
	});

	//handle any errors from the request
	req.on('error', function(e) {
		console.log('Problem with request: ' + e.message);
	});

	req.end();
});

env.listen(3002, function() {
	console.log('Listening on port 3002...');
});