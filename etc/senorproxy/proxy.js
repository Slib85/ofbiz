// Includes
var url = require('url'),
    http = require('http'),
    qs = require('querystring'),
    parser = require('libxml-to-js');
// Server
var server = http.createServer(function (request, response) {
    response.writeHead(200, {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
        "Cache-Control":"public, max-age=300"
    });
    if (request.url == "/favicon.ico" || request.method !== 'POST') {
        response.end();
        return;
    }
    var getData = function (data) {
        var URL = data.url;
        var shortURL = data.url;
        var parsedURL = url.parse(shortURL);
        console.log("Processing (" + new Date().toISOString() + "): " + parsedURL.pathname);
        delete data.url;
        URL = URL + '?' + getQueryString(data);
        if (URL.length > 2000) {
            var postBody = getQueryString(data);
            var options = {
              hostname: parsedURL.host,
              path: parsedURL.pathname,
              method: 'POST',
               headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Content-Length': postBody.length
              }
            };

            var req = http.request(options, function(res) {
                var body = "";
                res.setEncoding('binary');
                res.on('data', function (chunk) {
                    body += chunk;
                });
                res.on('end', function () {
                    var base64 = new Buffer(body, 'binary').toString('base64');
                    getOverflow(URL, base64)
                });
            });

            req.on('error', function(e) {
              console.log('problem with request: ' + e.message);
            });

            // write data to request body
            req.write(postBody);
            req.end();
        } else {
            getOverflow(URL, null)
        }
    }

    var getOverflow = function (URL, base64) {
        var postBody = URL+"&req=oversetstatus";
        var parsedURL = url.parse(URL);
        var options = {
          hostname: parsedURL.host,
          path: parsedURL.pathname,
          method: 'POST',
           headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Content-Length': postBody.length
          }
        };

        var req = http.request(options, function(res) {
            var body = "";
            res.setEncoding('binary');
            res.on('data', function (chunk) {
                body += chunk;
            });
            res.on('end', function () {
                parser(body, function (error, result) {
                    if (error) {
                        console.error(error);
                    } else {
                        var overflows = [];
                        if (typeof result.Group.RichText != "undefined"){
                            if (typeof result.Group.RichText.length == "undefined") {
                                overflows.push(result.Group.RichText['@'].elementID)
                            } else {
                                for (var key in result.Group.RichText) {
                                    overflows.push(result.Group.RichText[key]['@'].elementID)
                                }
                            }
                        }
                        if (base64 != null) {
                            response.end('{"data":"' + base64 + '", "scene7url":"' + URL + '", "type":"data", "overflow":' + JSON.stringify(overflows) + '}');
                        } else {
                            response.end('{"data":"' + URL + '", "scene7url":"' + URL + '", "type":"url", "overflow":' + JSON.stringify(overflows) + '}');
                        }

                    }
                });
            });
        });

        req.on('error', function(e) {
          console.log('problem with request: ' + e.message);
        });

        // write data to request body
        req.write(postBody);
        req.end();
    }

    var getQueryString = function (obj) {
        var result = "";
        for (param in obj) {
        	if (param.indexOf('insertAfter') != -1){
        		if (typeof obj[param] === 'string'){
        			result += (encodeURIComponent(param.replace(/\[\]/,'')) + '=' + (obj[param]) + '&');
        		}else {
	        		for (subParam in obj[param]){
        				result += (encodeURIComponent(param.replace(/\[\]/,'')) + '=' + (obj[param][subParam]) + '&');
	            	}
            	}
            } else {
            	result += (encodeURIComponent(param) + '=' + (obj[param]) + '&');
            }
        }
        if (result) {
            result = result.substr(0, result.length - 1);
        }
        return result;
    }

    if (request.method == 'POST') {
        var body = '';
        request.on('data', function (data) {
            body += data;
            if (body.length > 1e6) {
                request.connection.destroy();
            }
        });
        request.on('end', function () {
            getData(qs.parse(body));
        });
    }
});
// Alleged Catch all
server.on('uncaughtException', function(err) {
    console.log(err.stack);
});
// Listen on port 8000, IP defaults to 127.0.0.1
server.listen(80);
// Put a friendly message on the terminal
console.log("Server running at http://127.0.0.1:80/");