var jobs = [
    {color:'a~b'}, {color:'a~b'},
    {color:'a~c'}, {color:'a~c'}, {color:'a~c'},
    {color:'a~d'}, {color:'a~d'}, {color:'a~d'}, {color:'a~d'},
    {color:'a~e'}, {color:'a~e'}, {color:'a~e'},
    {color:'b~c'},
    {color:'b~d'}
];

var jobs2 = [
    {color:['a','b']}, {color:['a','b']},
    {color:['a','c']}, {color:['a','c']}, {color:['a','c']},
    {color:['a','d']}, {color:['a','d']}, {color:['a','d']}, {color:['a','d']},
    {color:['a','e']}, {color:['a','e']}, {color:['a','e']},
    {color:['b','c']},
    {color:['b','d']}
];

var jobs1 = [
    {color:'y~z'}, {color:'y~z'},
    {color:'x~z'}, {color:'x~z'}, {color:'x~z'},
    {color:'a~d'}, {color:'a~d'}, {color:'a~d'}, {color:'a~d'},
    {color:'x~y'}, {color:'x~y'}, {color:'x~y'},
    {color:'b~c'},
    {color:'b~d'}
];

var jobs_R = [
    {color:'a~d'}, {color:'a~d'}, {color:'a~d'}, {color:'a~d'},
    {color:'a~c'}, {color:'a~c'}, {color:'a~c'},
    {color:'a~e'}, {color:'a~e'}, {color:'a~e'},
    {color:'a~b'}, {color:'a~b'},
    {color:'b~c'},
    {color:'b~d'}
];
//a~d[4], a~d^a[8], a~d^d[1]
//a~c[3], a~c^a[9], a~c^c[1]
//a~e[3], a~e^a[9], a~e^e[0]
//a~b[2], a~b^a[10], a~b^b[2]
//b~c[1], b~c^b[3], b~c^c[3]
//b~d[1], b~d^b[3], b~d^d[4]


/*function findHits(jobs) {
 var hits = [];

 for (var i = 0; i < jobs.length; i ++) {
 var color = jobs[i].color;
 if(typeof hits[color] === 'undefined') {
 hits[color] = 1;
 } else {
 hits[color] = ++hits[color];
 }
 var colors = color.split('~');
 for(var j = 0; j < colors.length; j ++) {
 var partialMatchingColor = colors[j];

 }
 }
 return hits;
 }*/
/*var stat = [];
 findHits1(stat, jobs, '', []);
 console.log(stat);*/

console.log(findHits(jobs2));

function findHits(jobs) {
    var hits = [];
    for (var i = 0; i < jobs.length; i ++) {
        var colors = jobs[i].color;
        var concatenatedColors = concatenateColors(colors);
        if(typeof hits[concatenatedColors] === 'undefined') {
            hits = addAll(calculateHits(jobs, colors, [], [], true), hits);
        }
    }
    return hits;
}

function calculateHits(jobs, matchingColors, excludedColors, hits, recursive) {
    if(typeof hits === 'undefined') {
        hits = [];
    }
    for (var i = 0; i < jobs.length; i ++) {
        var colors = jobs[i].color;
        if(isExcludedColor(colors, excludedColors)) {
            continue;
        }
        var concatenatedColors = concatenateColors1(matchingColors, excludedColors);
        if(containsColors(colors, matchingColors)) {
            if(typeof hits[concatenatedColors] === 'undefined') {
                hits[concatenatedColors] = 1;
                if(matchingColors.length > 1 && recursive == true) {
                    var combinationMatrix = buildCombinationMatrix(matchingColors);
                    for(var j = 1; j < combinationMatrix.length; j ++) {
                        hits = addAll(calculateHits(jobs, [combinationMatrix[j][0]], combinationMatrix[j][1], hits, false), hits);
                    }
                }
            } else {
                hits[concatenatedColors] = ++hits[concatenatedColors];
            }
        }

    }
    return hits;
}

function calculateHitsCopy(jobs, matchingColors, excludedColors, hits) {
    if(typeof hits === 'undefined') {
        hits = [];
    }
    for (var i = 0; i < jobs.length; i ++) {
        var colors = jobs[i].color;
        if(isExcludedColor(colors, excludedColors)) {
            continue;
        }
        var concatenatedColors = concatenateColors1(matchingColors, excludedColors);
        if(containsColors(colors, matchingColors)) {
            if(typeof hits[concatenatedColors] === 'undefined') {
                hits[concatenatedColors] = 1;
                for(var j = 0; j < matchingColors.length && matchingColors.length > 1; j ++) {
                    hits = addAll(calculateHits(jobs, [matchingColors[j]], [matchingColors], hits), hits);
                }
            } else {
                hits[concatenatedColors] = ++hits[concatenatedColors];
            }
        }

    }
    return hits;
}

function buildCombinationMatrix(colors) {
    var combinationMatrix = [];
    var combinations = buildCombinations(colors);
    var previousMatchingColorsLength = combinations[0].length;
    var previousMatchingColors = [];
    var excludedColors = [];
    for(var i = 0; i < combinations.length; i ++) {
        var matchingColors = combinations[i];
        if(matchingColors.length < previousMatchingColorsLength) {
            excludedColors[excludedColors.length] =  previousMatchingColors;
        } else if(matchingColors.length > previousMatchingColorsLength) {
            excludedColors = removeLastElement(excludedColors);
        }
        combinationMatrix[combinationMatrix.length] = [matchingColors, cloneArray(excludedColors)];
        previousMatchingColors = matchingColors;
        previousMatchingColorsLength = matchingColors.length;
    }
    return combinationMatrix;
}

function buildCombinations(colors, combinations) {
    if(typeof combinations === 'undefined') {
        combinations = [];
    }
    for(var i = colors.length; i >= 0; i --) {
        var matchingColors = [];
        var excludedColors = [];
        for(var j = 0, _j = 0; j < colors.length; j ++) {
            if(j != i) {
                matchingColors[_j++] = colors[j];
            }
        }
        if(matchingColors.length > 1 && matchingColors.length < colors.length) {
            buildCombinations(matchingColors, combinations);
        } else if(matchingColors.length > 0) {
            combinations[combinations.length] = matchingColors;
        }
    }
    return combinations;
}

function concatenateColors(colors) {
    var concatenatedColors = '';
    for(var i = 0; i < colors.length; i ++) {
        concatenatedColors += (i > 0 ? '~' : '') + colors[i];
    }
    return concatenatedColors;
}

function concatenateColors1(colors, excludedColors) {
    var concatenatedColors = '';
    for(var i = 0; i < excludedColors.length; i ++) {
        concatenatedColors += concatenateColors(excludedColors[i]) + '^';
    }
    concatenatedColors += concatenateColors(colors);
    return concatenatedColors;
}

function addAll(source, destination) {
    for(prop in source) {
        if(typeof source[prop] !== 'undefined') {
            destination[prop] = source[prop];
        }
    }
    return destination;
}

function containsColors(colors, matchingColors) {
    var matches = false;
    for(var i = 0; i < matchingColors.length; i ++) {
        matches = false;
        for(var j = 0; j < colors.length; j ++) {
            if(matchingColors[i] == colors[j]) {
                matches = true;
                break;
            }
        }
        if(matches == false) {
            break;
        }
    }
    return matches;
}

function isExcludedColor(colors, excludedColors) {
    for(var i = 0; i < excludedColors.length; i ++) {
        if(containsColors(colors, excludedColors[i])) {
            return true;
        }
    }
    return false;
}

function cloneArray(array) {
    var clonedArray = [];
    for(var i = 0; i < array.length; i ++) {
        clonedArray[i] = array[i];
    }
    return clonedArray;
}

function removeLastElement(array) {
    var trimmedArray = [];
    for(var i = 0; i < array.length - 1; i ++) {
        trimmedArray[i] = array[i];
    }
    return trimmedArray;
}


/*function findHits1(stat, jobs, matcher, exclude) {
 if(matcher == '') {
 matcher = jobs[0].color;
 }
 for(var i = 0; i < jobs.length; i ++) {
 var color = jobs[i].color;
 if(typeof stat[color] === 'undefined') {
 stat[color] = 1;
 } else {
 stat[color] = ++stat[color];
 }
 }
 }*/



function groupByColors(jobs) {
    var hits = findHits(jobs);
//    console.log(hits);
    return jobs.sort(function(a, b) {return hits[b.color] - hits[a.color]});
}
//console.log(findHits());
//console.log(groupByColors(jobs1));
/*var stat = {};
 for(var i = 0; i < jobs.length - 1; i ++) {
 var job = jobs[i];
 var colors = job.color.split('~');
 stat['color'] = job.color;
 stat['numberOfColors'] = colors.length;
 for(var j = colors.length; j > 0; j --) {
 for (var k = 0; k < jobs.length; k++) {
 var thisJob = jobs[k];
 var thisColors = thisJob.color.split('~');
 for (var x = 0; x < colors.length; x++) {

 for (var y = 0; y < thisColors.length; y++) {

 }
 }
 }
 var comparingColors = [];
 }
 }*/
/*var combinations = getComparingColors(['a', 'b'*//*, 'c', 'd'*//*], []);
 //console.log(combinations);
 function getComparingColors(colors, combinations) {
 for(var i = colors.length; i >= 0; i --) {
 var comparingColors = [];
 for(var j = 0, k = 0; j < colors.length; j ++) {
 if(j != i) {
 comparingColors[k++] = colors[j];
 }
 }

 if(comparingColors.length > 1 && comparingColors.length < colors.length) {
 combinations[combinations.length] = getComparingColors(comparingColors, [])
 } else if(comparingColors.length > 0){
 combinations[combinations.length] = comparingColors;
 }
 }
 return combinations;

 }*/

