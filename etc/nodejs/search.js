/**
 * Created by shoabkhan on 4/30/17.
 */
var elasticsearch = require('elasticsearch');

var eclient = new elasticsearch.Client( {
    hosts: [
        'https://elastic:jw0d4buwN3P80StPDbibadWw@b235c58670b08689d4b0f3b42df95de1.us-east-1.aws.found.io:9243/'
    ]
});

client.search({
    index: 'bigname_search',
    type: 'product',
    body: {
        "suggest": {
            "namesuggest": {
                "prefix": "flat",
                "completion": {
                    "field": "autocomplete",
                    "size": 5
                }
            }
        },
    }
},function (error, response, status) {
    if (error){
        console.log("search error: "+error)
    } else {
        console.log("--- Response ---");
        console.log(response);
        console.log("--- Hits ---");
        response.suggest.namesuggest.forEach(function(hit){
            console.log(hit);
        })
    }
});