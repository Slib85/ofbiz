// Includes
var url = require('url'),
	http = require('http'),
	querystring = require('querystring'),
	express = require('express'),
	request = require("request"),
	xml2js = require('xml2js'),
	bodyparser = require('body-parser'),
	elasticsearch = require('elasticsearch'),
	exec = require('child_process').exec;

// Server
var texel = express();
texel.use(bodyparser.urlencoded({ extended: false }));

var elasticsearchclient = new elasticsearch.Client({ hosts: ['https://elastic:jdJk6CImCSHPfFEkd7aNyRCt@ebb060d251ef4837b4a2fd15fb15192e.us-east-1.aws.found.io:9243/']});

/**
 * Take the param object and convert it to a query string
 * @param {Object} paramObj - Literal object holding all params
 * @return {String} - return a string representation of all the url parameters
 */
var getQueryString = function(paramObj) {
	var result = '';
	for(param in paramObj) {
		if(param.indexOf('insertAfter') != -1) {
			if(typeof paramObj[param] === 'string') {
				result += (encodeURIComponent(param.replace(/\[\]/,'')) + '=' + (paramObj[param]) + '&');
			} else {
				for(subParam in paramObj[param]) {
					result += (encodeURIComponent(param.replace(/\[\]/,'')) + '=' + (paramObj[param][subParam]) + '&');
				}
			}
		} else {
			result += (encodeURIComponent(param) + '=' + (paramObj[param]) + '&');
		}
	}
	if(result) {
		result = result.substr(0, result.length - 1);
	}
	return result;
};

/**
 * Endpoint to get FXG given an id
 * @param {String} id - ID of the FXG to pull
 * @return {XML}
 */
texel.get('/getFXG', function(request, response) {
	//console.log('Fetching XML For Scene7 Template ID: ' + request.query.id);

	response.set({
		'Content-Type'                : 'application/xml',
		'Access-Control-Allow-Origin' : '*',
		'Cache-Control'               : 'public, max-age=432000'
	});

	var s7Url = null;
	if(request.query.type == 'ugc') {
		s7Url = 'http://actionenvelopew2p.scene7.com/is/agm/ActionEnvelope/ugc/' + request.query.id + '.fxg';
	} else {
		s7Url = 'http://actionenvelopew2p.scene7.com/is/agm/ActionEnvelope/' + request.query.id;
	}

	var parsedURL = url.parse(s7Url);

	var params = querystring.stringify({
		fmt: 'fxgraw'
	});

	//generate the options object for the post request
	var options = {
		hostname : parsedURL.host,
		path     : parsedURL.pathname,
		method   : 'POST',
		headers  : {
			'Content-Type'   : 'application/x-www-form-urlencoded',
			'Content-Length' : params.length
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

	/*
	 * write data to the request body
	 * in this case, its the params because we are passing data as a post body
	 */
	req.write(params);
	req.end();
});

/**
 * Endpoint to get FXG given an id
 * @param {String} id - ID of the FXG to pull
 * @return {IMAGE}
 */
texel.get('/getBasicImage', function(request, response) {
	//console.log('Fetching Image for: ' + request.query.id);

	response.set({
		'Content-Type'                : 'image/png',
		'Access-Control-Allow-Origin' : '*',
		'Cache-Control'               : 'public, max-age=432000'
	});

	//check if this is a shortcut
	var parsedURL = url.parse('http://actionenvelopew2p.scene7.com/is/agm/ActionEnvelope/' + request.query.id);
	delete request.query.id;

	//for(var propName in request.query) {
	//	if(request.query.hasOwnProperty(propName)) {
	//		console.log(propName, request.query[propName]);
	//	}
	//}

	var params = null;

	if(typeof request.query.templateType !== 'undefined' && request.query.templateType != null && request.query.templateType != '') {
		if(typeof request.query.fmt !== 'undefined' && request.query.fmt != null && request.query.fmt != '') {
			params = 'fmt=' + request.query.fmt;
		} else {
			params = 'fmt=png-alpha';
		}

		if(request.query.templateType == 'BLANK') {
			params = params + '&setElement.COLOR_bgcolor_Path=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor1=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setAttr.IMAGE_return_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_name_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_address_logo_left=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_return_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_name_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_address_logo_top=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_reply_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_name_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_address_logo_top=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_reply_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_name_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_address_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_name_no_logo=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_address_no_logo=%7Bvisible%3Dfalse%7D&setAttr.COLOR_reply_border=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_stamp_mark=%7Bvisible%3Dfalse%7D&setAttr.TEXT_universal_bottom_text=%7Bvisible%3Dfalse%7D&setAttr.TEXT_universal_bottom_text1=%7Bvisible%3Dfalse%7D&setAttr.TEXT_universal_bottom_text2=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_name_no_logo=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_address_no_logo=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_logo_back=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_flap_logo_left=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_logo_front=%7Bvisible%3Dfalse%7D';
		} else if(request.query.templateType == 'RETURN') {
			params = params + '&setElement.COLOR_bgcolor_Path=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor1=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setAttr.IMAGE_return_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_name_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_address_logo_left=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_return_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_name_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_address_logo_top=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_reply_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_name_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_address_logo_top=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_reply_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_name_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_address_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_name_no_logo=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_address_no_logo=%7Bvisible%3Dfalse%7D&setAttr.COLOR_reply_border=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_stamp_mark=%7Bvisible%3Dfalse%7D&setAttr.TEXT_universal_bottom_text=%7Bvisible%3Dfalse%7D&setAttr.TEXT_universal_bottom_text1=%7Bvisible%3Dfalse%7D&setAttr.TEXT_universal_bottom_text2=%7Bvisible%3Dfalse%7D';
		} else if(request.query.templateType == 'REPLY') {
			params = params + '&setElement.COLOR_bgcolor_Path=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor1=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setAttr.IMAGE_reply_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_name_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_address_logo_left=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_reply_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_name_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_address_logo_top=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_return_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_name_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_address_logo_top=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_return_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_name_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_address_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_name_no_logo=%7Bvisible%3Dfalse%7D&setAttr.TEXT_return_address_no_logo=%7Bvisible%3Dfalse%7D&setAttr.COLOR_reply_border=%7Bvisible%3Dfalse%7D&setAttr.TEXT_reply_stamp_mark=%7Bvisible%3Dfalse%7D&setAttr.TEXT_universal_bottom_text=%7Bvisible%3Dfalse%7D&setAttr.TEXT_universal_bottom_text1=%7Bvisible%3Dfalse%7D&setAttr.TEXT_universal_bottom_text2=%7Bvisible%3Dfalse%7D';
		} else if(request.query.templateType == 'FLAP') {
			params = params + '&setElement.COLOR_bgcolor_Path=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor1=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setAttr.IMAGE_flap_logo_top_FilledPath=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_flap_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_flap_name_logo_left=%7Bvisible%3Dfalse%7D&setAttr.TEXT_flap_return_address_logo_left=%7Bvisible%3Dfalse%7D&setAttr.IMAGE_flap_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_flap_name_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_flap_return_address_logo_top=%7Bvisible%3Dfalse%7D&setAttr.TEXT_universal_bottom_text=%7Bvisible%3Dfalse%7D&setAttr.TEXT_universal_bottom_text1=%7Bvisible%3Dfalse%7D&setAttr.TEXT_universal_bottom_text2=%7Bvisible%3Dfalse%7D';
		}
		params = params.replace(/ENV_HEX_PLACE_HOLDER/g, (typeof request.query.hex !== 'undefined' && request.query.hex != null && request.query.hex != '') ? request.query.hex : 'ffffff');

		if(typeof request.query.wid !== 'undefined' && request.query.wid != null && request.query.wid != '') {
			params = params + '&wid=' + request.query.wid;
		}
		if(typeof request.query.hei !== 'undefined' && request.query.hei != null && request.query.hei != '') {
			params = params + '&hei=' + request.query.hei;
		}
	} else {
		params = querystring.stringify(request.query);
	}

	//generate the options object for the post request
	var options = {
		hostname : parsedURL.host,
		path     : parsedURL.pathname,
		method   : 'POST',
		headers  : {
			'Content-Type'   : 'application/x-www-form-urlencoded',
			'Content-Length' : params.length
		}
	};

	//create the request that will fetch image from scene7 servers
	var resBody = '';
	var req = http.request(options, function(res) {
		res.setEncoding('binary');
		res.on('data', function (chunk) {
			resBody += chunk;
		});
		res.on('end', function () {
			var buff = new Buffer(resBody, 'binary');
			response.status(200).send(buff);
		});
	});

	//handle any errors from the request
	req.on('error', function(e) {
		console.log('Problem with request: ' + e.message);
	});

	/*
	 * write data to the request body
	 * in this case, its the params because we are passing data as a post body
	 */
	req.write(params);
	req.end();
});

/**
 * Endpoint to get JSON response with base64 image and overflow data
 */
texel.post('/getImage', function(request, response) {
	//console.log('Fetching Base64 Image for: ' + request.body.url);

	response.set({
		'Content-Type'                : 'application/json',
		'Access-Control-Allow-Origin' : '*',
		'Cache-Control'               : 'public, max-age=432000'
	});

	var s7Url = request.body.url;
	var parsedURL = url.parse(s7Url);
	delete request.body.url;

	var parameters = getQueryString(request.body);

	var jsonResponse = {
		'data': null,
		'overflow': null
	};

	//###################################################
	//###################################################
	//create the request that will get the scene7 base64
	//generate the options object for the post request
	var options = {
		hostname : parsedURL.host,
		path     : parsedURL.pathname,
		method   : 'POST',
		headers  : {
			'Content-Type'   : 'application/x-www-form-urlencoded',
			'Content-Length' : parameters.length
		}
	};

	var resBody = '';
	var req = http.request(options, function(res) {
		res.setEncoding('binary');
		res.on('data', function (chunk) {
			resBody += chunk;
		});
		res.on('end', function () {
			jsonResponse.data = new Buffer(resBody, 'binary').toString('base64');

			//###################################################
			//###################################################
			//create the request that will get the scene7 overflow
			parameters = parameters + '&req=oversetstatus';
			options = {
				hostname : parsedURL.host,
				path     : parsedURL.pathname,
				method   : 'POST',
				headers  : {
					'Content-Type'   : 'application/x-www-form-urlencoded',
					'Content-Length' : parameters.length
				}
			};

			var reqOverFlow = http.request(options, function(res2) {
				resBody = '';
				res2.setEncoding('binary');
				res2.on('data', function (chunk) {
					resBody += chunk;
				});
				res2.on('end', function () {
					xml2js.parseString(resBody, function(error, result) {
						if(error) {
							console.log('Problem with parsing xml: ' + error);
						} else {
							var overflows = [];
							if(result != null && typeof result['Graphic'] != 'undefined' && typeof result['Graphic']['Group'] != 'undefined' && typeof result['Graphic']['Group'].length != 'undefined') {
								for(var i = 0; i < result['Graphic']['Group'].length; i++) {
									if(typeof result['Graphic']['Group'][i]['RichText'] != 'undefined') {
										for(var j = 0; j < result['Graphic']['Group'][i]['RichText'].length; j++) {
											overflows.push(result['Graphic']['Group'][i]['RichText'][j]['$']['s7:elementID'])
										}
									}
								}
							}
							if(overflows.length > 0) {
								jsonResponse.overflow = overflows;
							}

							//output response back
							response.status(200).send(JSON.stringify(jsonResponse));
						}
					});
				});
			});

			//handle any errors from the request
			reqOverFlow.on('error', function(e) {
				console.log('Problem with overflow request: ' + e.message);
			});

			/*
			 * write data to the request body
			 * in this case, its the params because we are passing data as a post body
			 */
			reqOverFlow.write(parameters);
			reqOverFlow.end();
		});
	});

	//handle any errors from the request
	req.on('error', function(e1) {
		console.log('Problem with image request: ' + e1.message);
	});

	/*
	 * write data to the request body
	 * in this case, its the params because we are passing data as a post body
	 */
	req.write(parameters);
	req.end();
});

/**
 * Endpoint to get image attributes
 * @param {String} url - url of image to fetch
 * @return {JSON}
 */
texel.get('/getImageAttr', function(request, response) {
	//console.log('Fetching Image Attributes: ' + request.query.url);

	response.set({
		'Content-Type'                : 'application/json',
		'Access-Control-Allow-Origin' : '*',
		'Cache-Control'               : 'public, max-age=1'
	});

	var s7Url = request.query.url;
	var parsedURL = url.parse(s7Url);
	var params = querystring.stringify({
		req: 'imageprops,xml'
	});

	//generate the options object for the post request
	var options = {
		hostname : parsedURL.host,
		path     : parsedURL.pathname,
		method   : 'POST',
		headers  : {
			'Content-Type'   : 'application/xml',
			'Content-Length' : params.length
		}
	};

	var jsonResponse = {
		'width': null,
		'height': null
	};

	//create the request that will fetch xml from scene7 servers
	var resBody = '';
	var req = http.request(options, function(res) {
		res.setEncoding('utf8');
		res.on('data', function (chunk) {
			resBody += chunk;
		});
		res.on('end', function () {
			xml2js.parseString(resBody, function(error, result) {
				if(error) {
					console.log('Problem with parsing xml: ' + error);
				} else {
					if(typeof result['prop-group'] !== 'undefined' && result['prop-group']['prop-group'][0] !== 'undefined') {
						for(var i = 0; i < result['prop-group']['prop-group'].length; i++) {
							for(var j = 0; j < result['prop-group']['prop-group'][i]['property'].length; j++) {
								if(result['prop-group']['prop-group'][i]['property'][j]['$']['name'] == 'width') {
									jsonResponse.width = result['prop-group']['prop-group'][i]['property'][j]['$']['value'];
								} else if(result['prop-group']['prop-group'][i]['property'][j]['$']['name'] == 'height') {
									jsonResponse.height = result['prop-group']['prop-group'][i]['property'][j]['$']['value'];
								}
							}
						}
					}
				}
			});

			//output response back
			response.status(200).send(JSON.stringify(jsonResponse));
		});
	});

	//handle any errors from the request
	req.on('error', function(e) {
		console.log('Problem with request: ' + e.message);
	});

	/*
	 * write data to the request body
	 * in this case, its the params because we are passing data as a post body
	 */
	req.write(params);
	req.end();
});

/**
 * Endpoint to get the geo location
 * @return {JSON}
 */
texel.get('/getLocation', function(request, response) {
	response.set({
		'Content-Type'                : 'application/json',
		'Access-Control-Allow-Origin' : '*',
		'Cache-Control'               : 'public, max-age=432000'
	});

	var ip;
	if(request.headers['X-Forwarded-For']) {
		ip = request.headers['X-Forwarded-For'].split(",")[0];
	} else if(request.headers['x-forwarded-for']) {
		ip = request.headers['x-forwarded-for'].split(",")[0];
	} else if(request.headers['X-Real-IP']) {
		ip = request.headers['X-Real-IP'].split(",")[0];
	} else if(request.headers['x-real-ip']) {
		ip = request.headers['x-real-ip'].split(",")[0];
	} else if(request.connection && request.connection.remoteAddress) {
		ip = request.connection.remoteAddress;
	} else if(request.socket && request.socket.remoteAddress) {
		ip = request.socket.remoteAddress;
	} else if(request.connection && request.connection.socket && request.connection.socket.remoteAddress) {
		ip = request.connection.socket.remoteAddress;
	} else {
		ip = request.ip;
	}

	//console.log('IP: ' + ip);
	//var ip = request.client.remoteAddress;

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

/**
 * Endpoint to get the geo location country via i-parcel
 * @return {JSON}
 */
texel.get('/getCountry', function(request, response) {
	response.set({
		'Content-Type'                : 'application/json',
		'Access-Control-Allow-Origin' : '*',
		'Cache-Control'               : 'public, max-age=432000'
	});

	var ip;
	if(request.headers['X-Forwarded-For']) {
		ip = request.headers['X-Forwarded-For'].split(",")[0];
	} else if(request.headers['x-forwarded-for']) {
		ip = request.headers['x-forwarded-for'].split(",")[0];
	} else if(request.headers['X-Real-IP']) {
		ip = request.headers['X-Real-IP'].split(",")[0];
	} else if(request.headers['x-real-ip']) {
		ip = request.headers['x-real-ip'].split(",")[0];
	} else if(request.connection && request.connection.remoteAddress) {
		ip = request.connection.remoteAddress;
	} else if(request.socket && request.socket.remoteAddress) {
		ip = request.socket.remoteAddress;
	} else if(request.connection && request.connection.socket && request.connection.socket.remoteAddress) {
		ip = request.connection.socket.remoteAddress;
	} else {
		ip = request.ip;
	}

	//console.log('IP: ' + ip);
	//var ip = request.client.remoteAddress;

	var ipUrl = 'http://session.i-parcel.com/GetCountry';
	var parsedURL = url.parse(ipUrl);

	var params = {
		key: '935f5a96-e12a-4580-98f2-cdc73a30a5ae',
		IPAddress: ip
	};

	//generate the options object for the post request
	var options = {
		hostname : parsedURL.host,
		path     : parsedURL.pathname,
		method   : 'POST',
		headers  : {
			'Content-Type'   : 'application/json'
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

	req.write(JSON.stringify(params));
	req.end();
});

/**
 * Endpoint to convert image
 * @return {JSON}
 */
texel.get('/convertImage', function(request, response) {
	response.set({
		'Content-Type'                : 'application/json',
		'Access-Control-Allow-Origin' : '*',
		'Cache-Control'               : 'no-cache'
	});

	var originalImagePath = request.query.oIP;
	var newImagePath = request.query.nIP;

	//console.log(originalImagePath);
	//console.log(newImagePath);

	var execCommand = "gs -dBATCH -dSAFER -dNOPAUSE -dNOPROMPT -r300 -dMaxBitmap=500000000 -dEPSCrop -dAlignToPixels=0 -dGridFitTT=2 -sDEVICE=pngalpha -dTextAlphaBits=4 -dGraphicsAlphaBits=4 -sOutputFile=" + newImagePath + " " + originalImagePath;

	exec(execCommand, function (error, stdout, stderr) {
		if(error !== null) {
			newImagePath = '';
			console.log(error);
		}
	});

	var jsonResponse = {
		'originalImagePath': originalImagePath,
		'newImagePath': newImagePath
	};
	response.status(200).send(JSON.stringify(jsonResponse));
});

/**
 * Endpoint to get the elasticsearch autocomplete
 * @return {JSON}
 */
texel.get('/searchAutoComplete', function(request, response) {
	response.set({
		'Content-Type'                : 'application/json',
		'Access-Control-Allow-Origin' : '*',
		'Cache-Control'               : 'no-cache'
	});

	var searchQuery = request.query.w;
	var webSiteId = request.query.wsi;

	//console.log("query: " + searchQuery);
	var hitResults = [];
	elasticsearchclient.search({
		index: 'bigname_search_alias',
		type: 'product',
		body: {
			"from" : 0,
			"size" : 5,
			"query" : {
				"function_score" : {
					"query" : {
						"bool" : {
							"must" : [
								{
									"multi_match" : {
										"query" : searchQuery,
										"fields" : [
											"autocomplete^1.0"
										],
										"type" : "best_fields",
										"operator" : "AND",
										"slop" : 0,
										"prefix_length" : 0,
										"max_expansions" : 50,
										"lenient" : false,
										"zero_terms_query" : "NONE",
										"boost" : 1.0
									}
								}
							],
							"filter" : [
								{
									"terms" : {
										"websiteid.keyword" : [
											webSiteId
										],
										"boost" : 1.0
									}
								}
							],
							"disable_coord" : false,
							"adjust_pure_negative" : true,
							"boost" : 1.0
						}
					},
					"functions" : [
						{
							"filter" : {
								"match_all" : {
									"boost" : 1.0
								}
							},
							"field_value_factor" : {
								"field" : "salesrank",
								"factor" : 1.0,
								"modifier" : "none"
							}
						}
					],
					"score_mode" : "multiply",
					"max_boost" : 3.4028235E38,
					"boost" : 1.0
				}
			},
			"sort" : [
				{
					"_score" : {
						"order" : "desc"
					}
				}
			]
		}
	}).then(function(resp) {
		if(typeof resp.hits != 'undefined' && typeof resp.hits.hits != 'undefined') {
            hitResults = resp.hits.hits;
		}
		var jsonResponse = {
			'searchQuery': searchQuery,
			'results': hitResults
		};
		response.status(200).send(JSON.stringify(jsonResponse));
	}, function (error) {
		response.status(200).send(JSON.stringify(error));
	});
});

texel.listen(3001, function() {
	console.log('Listening on port 3001...');
});