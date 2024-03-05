var jobs = [
    {color:'a~b'}, {color:'a~b'},
    {color:'a~c'}, {color:'a~c'}, {color:'a~c'},
    {color:'a~d'}, {color:'a~d'}, {color:'a~d'}, {color:'a~d'},
    {color:'a~e'}, {color:'a~e'}, {color:'a~e'},
    {color:'b~c'},
    {color:'b~d'}
];

var gripper = 0.25;
var imageGap = 0.5;
var plateLength = 12;
var optimalPlatePercentage = 90;
var secondaryOptimalPlatePercentage = 50;



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



var jobs2 = [
    {color:['a','b'], 'height':1, isRush: 'Y'}, {color:['a','b'], 'height':3, isRush: 'Y'},
    {color:['a','c'], 'height':2, isRush: 'Y'}, {color:['a','c'], 'height':3, isRush: 'Y'}, {color:['a','c'], 'height':5, isRush: 'Y'},
    {color:['a','d'], 'height':1, isRush: 'Y'}, {color:['a','d'], 'height':2, isRush: 'Y'}, {color:['a','d'], 'height':3, isRush: 'Y'}, {color:['a','d'], 'height':4, isRush: 'Y'},
    {color:['a','e'], 'height':4, isRush: 'Y'}, {color:['a','e'], 'height':4, isRush: 'Y'}, {color:['a','e'], 'height':4, isRush: 'Y'},
    {color:['b','c'], 'height':2, isRush: 'Y'},
    {color:['b','d'], 'height':3, isRush: 'Y'}
];

//console.log(findHits(jobs2));
//console.log(sortHits(findHits(jobs2), 'hit'));
console.log(sortHits(findHits(jobs2), 'height'));
//console.log(sortJobs(jobs2));

//console.log(findMatchingCombination(10, [6,5,3,2]));

console.log(findHighestMatchingCombinationWithoutBreaking(10, [[6,3,3,2], [3,3,2,2], [5,3,2], [1]]));

function findMatchingCombination(height, heights) {//0, 10, [6,5,3,2]
    var combination = [];
    for(var i = 0; i < heights.length; i ++) {
        if(heights[i] > height) {
            continue;
        }
        if(heights[i] == height) {
            combination = [heights[i]];
            break;
        } else if(i < heights.length - 1) {
            var addOnCombinations = findMatchingCombination(height - heights[i], removeFirstElement(heights, i));
            if(findTotalHeight(addOnCombinations) == height - heights[i]) {
                combination = [heights[i]];
                combination = appendArray(combination, addOnCombinations);
                break;
            }
        }
    }
    return combination;

}

function findHighestMatchingCombination(height, heights) {//0, 10, [6,3,3,2]
    var combination = [];
    var highestCombination = 0;
    for(var i = 0; i < heights.length; i ++) {
        if(heights[i] > height) {
            continue;
        }
        if(heights[i] == height) {
            combination = [heights[i]];
            break;
        } else if(i < heights.length - 1) {
            var addOnCombinations = findHighestMatchingCombination(height - heights[i], removeFirstElement(heights, i));
            if(findTotalHeight(addOnCombinations) == height - heights[i]) {
                combination = [heights[i]];
                combination = appendArray(combination, addOnCombinations);
                break;
            } else if(findTotalHeight(addOnCombinations) + heights[i] > highestCombination) {
                combination = [heights[i]];
                combination = appendArray(combination, addOnCombinations);
                highestCombination = findTotalHeight(combination);
            }
        } else if(i == heights.length - 1) {
            if(heights[i] > highestCombination) {
                combination = [heights[i]];
                highestCombination = findTotalHeight(combination);
            }
        }
    }
    return combination;

}

function findHighestMatchingCombinationWithoutBreaking2(height, heights, topLevelMatchFlag) {//0, 10, [6,3,3,2]
    var combination = [];
    var highestCombination = 0;
    for(var i = 0; i < heights.length; i ++) {
        if(heights[i] > height) {
            continue;
        }
        if(heights[i] == height) {
            combination = [heights[i]];
            break;
        } else if(i < heights.length - 1) {
            var addOnCombinations = findHighestMatchingCombination(height - heights[i], removeFirstElement(heights, i));
            if(findTotalHeight(addOnCombinations) == height - heights[i]) {
                combination = [heights[i]];
                combination = appendArray(combination, addOnCombinations);
                break;
            } else if(findTotalHeight(addOnCombinations) + heights[i] > highestCombination) {
                combination = [heights[i]];
                combination = appendArray(combination, addOnCombinations);
                highestCombination = findTotalHeight(combination);
            }
        } else if(i == heights.length - 1) {
            if(heights[i] > highestCombination) {
                combination = [heights[i]];
                highestCombination = findTotalHeight(combination);
            }
        }
    }
    if(heights.length != combination.length && !topLevelMatchFlag) {
        combination = [];
    }
    return combination;

}

function findHighestMatchingCombinationWithoutBreaking1(height, sameColoredJobs) {//0, 10, [6,3,3,2]
    var combination = [];
    for(var x = 0; x < sameColoredJobs.length; x ++) {
        var heights = sameColoredJobs[x];
        combination = appendArray(combination, findHighestMatchingCombinationWithoutBreaking2(height - findTotalHeight(combination), heights, combination.length == 0));
        if(findTotalHeight(combination) == 10) {
            break;
        }
    }
    return combination;
}

function findHighestMatchingCombinationWithoutBreaking(height, sameColoredJobs) {
    var combination = [];
    var highestCombination = 0;
    for(var x = 0; x < sameColoredJobs.length; x ++) {
        var thisCombination = findHighestMatchingCombinationWithoutBreaking1(height, x == 0 ? sameColoredJobs : removeFirstElement(sameColoredJobs, x - 1));
        var thisCombinationHeight = findTotalHeight(thisCombination);
        if(thisCombinationHeight > highestCombination) {
            combination = [];
            combination = appendArray(combination,thisCombination);
            highestCombination = thisCombinationHeight;
        }

        if(findTotalHeight(combination) == 10) {
            break;
        }

    }

    return combination;

}

function appendArray(destination, source) {
    for(var i = 0, j = destination.length; i < source.length; i ++, j++) {
        destination[j] = source[i];
    }
    return destination;
}

function removeFirstElement(array, offSet) {
    var trimmedArray = [];
    for(var i = 1 + offSet, j = 0; i < array.length; i ++) {
        trimmedArray[j++] = array[i];
    }
    return trimmedArray;
}

function findTotalHeight(heights) {
    var height = 0;
    for(var i = 0; i < heights.length; i ++) {
        height += heights[i];
    }
    return height;
}


function matchDepth(matchKey) {
    return matchKey.split('^').length;
}

function sortJobs(jobs) {
    var hits = findHits(jobs);
    return jobs.sort(function(a, b) {
        var aColors = concatenateColors(a.color);
        var bColors = concatenateColors(b.color);
        return hits[bColors] - hits[aColors];
    });
}

function getSortedProperties(obj) {
    var props = [];
    for(prop in obj) {
        if(typeof obj[prop] !== 'undefined') {
            props[props.length] = prop;
        }
    }
    props.sort();
    return props;
}

function sortHits(hits, sortBy) {
    var hitsArray = [];
    for(prop in hits) {
        hitsArray[hitsArray.length] = hits[prop];
    }
    hitsArray.sort(function(a, b) {
        var hit1 = getExactHit(a);
        var hit2 = getExactHit(b);
        return hit2[sortBy] - hit1[sortBy];
    });
    return hitsArray;
}

function getExactHit(hit) {
    var key = '';
    for(prop in hit) {
        if(key == '' || key.length > prop.length ) {
            key = prop;
        }
    }
    return hit[key];
}


/*function convertHits(_hits) {
 var hits = [];
 for(prop in _hits) {
 if(_hits.hasOwnProperty(prop)) {
 var obj = {};
 obj['key'] = prop;
 obj['hit'] = _hits[prop];
 obj['depth'] = matchDepth(prop);
 hits[hits.length] = obj;
 }
 }
 hits = hits.sort(function(a, b) {
 return a.key > b.key ? 1 : a.key < b.key ? -1 : 0;
 });

 *//*hits = hits.sort(function(a, b) {
 return a.depth == b.depth ? b.hit - a.hit : a.depth - b.depth;
 });*//*
 return hits;
 }*/
function findHits(jobs) {
    var hits = {};
    for (var i = 0; i < jobs.length; i ++) {
        var colors = jobs[i].color;
        var concatenatedColors = concatenateColors(colors);
        if(typeof hits[concatenatedColors] === 'undefined') {
            var _hits = calculateHits(jobs, colors, [], {}, true);
            console.log(_hits);
            hits[concatenatedColors] = _hits;
        }
    }
    return hits;
}

function calculateHits(jobs, matchingColors, excludedColors, hits, recursive) {
    if(typeof hits === 'undefined') {
        hits = {};
    }
    for (var i = 0; i < jobs.length; i ++) {
        var colors = jobs[i].color;
        if(isExcludedColor(colors, excludedColors)) {
            continue;
        }
        var concatenatedColors = concatenateColors1(matchingColors, excludedColors);
        if(containsColors(colors, matchingColors)) {
            if(typeof hits[concatenatedColors] === 'undefined') {
                if(matchDepth(concatenatedColors) == 1) {
                    hits[concatenatedColors] = {'hit': 1, 'height': jobs[i].height, 'heights': [jobs[i].height], 'jobs': [jobs[i]]};
                } else {
                    hits[concatenatedColors] = {'hit' : 1, 'colors': [concatenateColors(jobs[i].color)]};
                }

                if(matchingColors.length > 1 && recursive == true) {
                    var combinationMatrix = buildCombinationMatrix(matchingColors);
                    for(var j = 1; j < combinationMatrix.length; j ++) {
                        hits = addAll(calculateHits(jobs, [combinationMatrix[j][0]], combinationMatrix[j][1], hits, false), hits);
                    }
                }
            } else {
                if(matchDepth(concatenatedColors) == 1) {
                    hits[concatenatedColors]['hit'] = ++ hits[concatenatedColors]['hit'];
                    hits[concatenatedColors]['height'] = hits[concatenatedColors]['height'] + jobs[i].height;
                    hits[concatenatedColors]['heights'][hits[concatenatedColors]['heights'].length] = jobs[i].height;
                    hits[concatenatedColors]['jobs'][hits[concatenatedColors]['jobs'].length] = jobs[i];
                } else {
                    hits[concatenatedColors]['hit'] = ++ hits[concatenatedColors]['hit'];
                    if(!containsColors(hits[concatenatedColors]['colors'], [concatenateColors(jobs[i].color)])) {
                        hits[concatenatedColors]['colors'][hits[concatenatedColors]['colors'].length] = concatenateColors(jobs[i].color);
                    }
                }

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

