/**
 * This is the plate building algorithm to make a plate as much optimal as possible. Given below are the detail steps in this process.
 *
 *
 * Structure of the 'Master Hits' Object for 2 color jobs:
 * {
 *      a~b: {
 *          //Stats for all jobs having exact matching colors (a & b)
 *          a~b: {
 *              jobs: <Array of all jobs having the colors a & b>,
 *              artHeight: <Total artHeight for all the jobs having the colors a & b>,
 *              heights: <Array of artHeights for each jobs having the colors a & b>,
 *              hits: <Number of jobs having the colors a & b>
 *          },
 *          //Stats for all jobs not having exact matching colors(both colors a & b), but only having partial matching color a (the color a and/or some other color, like c or d or e)
 *          a~b^a: {
 *              colors: <Array of all distinct partial matching 'a' job colors>,
 *              hit: <Total number of partial matching 'a' color jobs
 *          },
 *          //Stats for all jobs not having exact matching colors(both colors a & b), but only having partial matching color b (the color a and/or some other color, like c or d or e)
 *          a~b^b:{
 *              colors: <Array of all distinct partial matching 'b' job colors>,
 *              hit: <Total number of partial matching 'b' color jobs
 *          }
 *      }
 * }
 *
 *
 *
 *
 * Step 1:
 * Analyse all the available jobs and create a master object, called 'hits'. 'Hits' will contain the statistics of each job colors.
 * @type {{color: string}[]}
 */


var jobs = [
                {color:'a~b'}, {color:'a~b'},
                {color:'a~c'}, {color:'a~c'}, {color:'a~c'},
                {color:'a~d'}, {color:'a~d'}, {color:'a~d'}, {color:'a~d'},
                {color:'a~e'}, {color:'a~e'}, {color:'a~e'},
                {color:'b~c'},
                {color:'b~d'}
];

var gripper = 0.5; //2 * 0.25;
var imageGap = 0.5;
var plateLength = 25;
var optimalPlatePercentage = 100;
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
    {color:['a','b'], 'height':3, isRush: 'Y'}, {color:['a','b'], 'height':3, isRush: 'Y'},
    {color:['a','c'], 'height':3, isRush: 'Y'}, {color:['a','c'], 'height':3, isRush: 'Y'}, {color:['a','c'], 'height':2, isRush: 'Y'}, {color:['a','c'], 'height':2, isRush: 'Y'},
    {color:['a','d'], 'height':6, isRush: 'Y'}, {color:['a','d'], 'height':3, isRush: 'Y'}, {color:['a','d'], 'height':3, isRush: 'Y'}, {color:['a','d'], 'height':2, isRush: 'Y'},
    {color:['a','e'], 'height':5, isRush: 'Y'}, {color:['a','e'], 'height':3, isRush: 'Y'}, {color:['a','e'], 'height':2, isRush: 'Y'},
    {color:['b','c'], 'height':1, isRush: 'Y'},
    {color:['b','d'], 'height':1, isRush: 'Y'}
];
var jobs3 = [
    {color:['a','b'], 'artHeight':3, isRush: 'Y'}, {color:['a','b'], 'artHeight':3, isRush: 'Y'},
    {color:['a','c'], 'artHeight':3, isRush: 'Y'}, {color:['a','c'], 'artHeight':3, isRush: 'Y'}, {color:['a','c'], 'artHeight':2, isRush: 'Y'}, {color:['a','c'], 'artHeight':2, isRush: 'Y'},
    {color:['a','d'], 'artHeight':6, isRush: 'Y'}, {color:['a','d'], 'artHeight':3, isRush: 'Y'}, {color:['a','d'], 'artHeight':3, isRush: 'Y'}, {color:['a','d'], 'artHeight':2, isRush: 'Y'},
    {color:['a','e'], 'artHeight':5, isRush: 'Y'}, {color:['a','e'], 'artHeight':3, isRush: 'Y'}, {color:['a','e'], 'artHeight':2, isRush: 'Y'}, {color:['a','e'], 'artHeight':2, isRush: 'Y'},
    {color:['b','c'], 'artHeight':1, isRush: 'Y'},
    {color:['b','d'], 'artHeight':1, isRush: 'Y'}
];

console.log(makePlate(jobs3));

//****************************************************************************
/**
 * The 'getRequiredPlateHeight(jobsArray)' method will give us the plate height required for the array of jobs passed in as argument,
 * by adding the artHeight of all the jobs in the passed in array and also inserting 'imageGap' between each jobs.
 *
 * @param jobs - Array of jobs whose required plate height needs to be found
 * @returns {number} - The plate height required for all the passed in jobs
 */
function getRequiredPlateHeight(jobs) {
    //Variable to hold the total required plate height
    var requiredPlateHeight = 0;

    //Loop through each jobs
    for(var i = 0; i < jobs.length; i ++) {

        //Add 'imageGap' (before all jobs except the first one) and the job's 'artHeight' to the total required plate height for each job
        requiredPlateHeight += (i > 0 ? imageGap : 0) + jobs[i].artHeight;
    }

    return requiredPlateHeight;
}

//****************************************************************************



function makePlate(jobs) {
    // Array to hold the jobs for a plate
    var jobsForPlate = [];

    //Single object holding the hits, both full and partial match, for each distinct job color
    var hits = findHits(jobs);

    //Array of individual hit object for each job color sorted by total artHeight in descending order
    var sortedHits = sortHits(hits, 'artHeight');

    //Array of same color jobs (exact matching) with total required plate height closest to the plate length
    var exactMatchingJobsForPlate = makeMostOptimalPlateWithSameColorJobs(sortedHits);
    console.log(exactMatchingJobsForPlate);

    //Add the same color jobs to the jobs for the plate
    jobsForPlate = appendArray(jobsForPlate, exactMatchingJobsForPlate);

    //Find the used plate height
    var usedPlateHeight = getRequiredPlateHeight(jobsForPlate) + imageGap;
    console.log(usedPlateHeight);

    //Check to see if the used plate percentage is less than optimal place percentage
    if((usedPlateHeight/plateLength) * 100 < optimalPlatePercentage) {

        //Find the exact matching color of the jobs that we already added to the jobs for the plate array
        var exactMatchingJobColor = exactMatchingJobsForPlate[0].color;
        console.log(exactMatchingJobColor);


        //Get the hit object for the exact matching color
        var hitForExactMatchingJobColor = getHitForJobColor(sortedHits, exactMatchingJobColor);
        console.log(hitForExactMatchingJobColor);

        //Get colors that only differ by one color (level 2 match) with exact matching color
        var level2PartialMatchingColors = getPartialMatchingColors(hitForExactMatchingJobColor, 2);
        console.log(level2PartialMatchingColors);

        //Check to see if there are level2 partial matching colors
        if(level2PartialMatchingColors.length > 0) {

            //Get the suitable job combination (without breaking a color group) that can fit on the the free space on the plate, from the level2 partial match color jobs
            var level2partialMatchingJobsForPlate = findMostOptimalJobCombinationWithoutBreaking(usedPlateHeight, getJobsWithColors(sortedHits, level2PartialMatchingColors));

            //Add the level2 partial matching jobs to the jobs for the plate array
            jobsForPlate = appendArray(jobsForPlate, level2partialMatchingJobsForPlate);

            //Update the used plate height
            usedPlateHeight = getRequiredPlateHeight(jobsForPlate) + imageGap;
            console.log(usedPlateHeight);
        }

        //Check to see if the used plate percentage is less than optimal place percentage
        if((usedPlateHeight/plateLength) * 100 < optimalPlatePercentage) {

            //Get colors that only differ by two color (level 3 match) with exact matching color
            var level3PartialMatchingColors = getPartialMatchingColors(hitForExactMatchingJobColor, 3);

            //Check to see if there are level3 partial matching colors
            if(level3PartialMatchingColors.length > 0) {

                //Get the suitable job combination (without breaking a color group) that can fit on the the free space on the plate, from the level3 partial match color jobs
                var level3partialMatchingJobsForPlate = findMostOptimalJobCombinationWithoutBreaking(usedPlateHeight, getJobsWithColors(sortedHits, level3PartialMatchingColors));

                //Add the level3 partial matching jobs to the jobs for the plate array
                jobsForPlate = appendArray(jobsForPlate, level3partialMatchingJobsForPlate);

                //Update the used plate height
                usedPlateHeight = getRequiredPlateHeight(jobsForPlate) + imageGap;
                console.log(usedPlateHeight);
            }
        }

        //Check to see if the used plate percentage is less than optimal place percentage
        if((usedPlateHeight/plateLength) * 100 < optimalPlatePercentage) {

            //Get colors that only differ by three color (level 4 match) with exact matching color
            var level4PartialMatchingColors = getPartialMatchingColors(hitForExactMatchingJobColor, 4);

            //Check to see if there are level4 partial matching colors
            if(level4PartialMatchingColors.length > 0) {
                //Get the suitable job combination (without breaking a color group) that can fit on the the free space on the plate, from the level4 partial match color jobs
                var level4partialMatchingJobsForPlate = findMostOptimalJobCombinationWithoutBreaking(usedPlateHeight, getJobsWithColors(sortedHits, level4PartialMatchingColors));

                //Add the level4 partial matching jobs to the jobs for the plate array
                jobsForPlate = appendArray(jobsForPlate, level4partialMatchingJobsForPlate);

                //Update the used plate height
                usedPlateHeight = getRequiredPlateHeight(jobsForPlate) + imageGap;
                console.log(usedPlateHeight);
            }
        }

        //Check to see if the used plate percentage is less than optimal place percentage
        if((usedPlateHeight/plateLength) * 100 < optimalPlatePercentage) {

            //Get colors that do not even match a single color with exact matching color
            var nonMatchingColors = getNonMatchingColors(hits, exactMatchingJobColor);

            //Check to see if there are non matching colors
            if(nonMatchingColors.length > 0) {

                //Get the suitable job combination (without breaking a color group) that can fit on the the free space on the plate, from non matching color jobs
                var nonMatchingJobsForPlate = findMostOptimalJobCombinationWithoutBreaking(usedPlateHeight, getJobsWithColors(sortedHits, nonMatchingColors));

                //Add the non matching jobs to the jobs for the plate array
                jobsForPlate = appendArray(jobsForPlate, nonMatchingJobsForPlate);

                //Update the used plate height
                usedPlateHeight = getRequiredPlateHeight(jobsForPlate) + imageGap;
                console.log(usedPlateHeight);
            }
        }

    }
    return jobsForPlate;
}

/**
 * Method used to find same color jobs that can most fit on a plate. This is done by creating job combinations for each available job colors and
 * picking the one combination that closely fit on an empty plate. The 'getRequiredPlateHeight()' method will give us the plate height required
 * for an array of jobs by adding the artHeight of all the jobs in the passed in array and also inserting 'imageGap' between each jobs.
 *
 * @param sortedHits - Array of individual hit objects for each job colors sorted by total artHeight in descending order
 * @returns {Array} = Array containing the most optimal job combination for the plate with the same job color
 */
function makeMostOptimalPlateWithSameColorJobs(sortedHits) {
    //Array to hold the most optimal job combination with same job colors
    var mostOptimalJobCombination = [];

    //The required plate height for mostOptimalJobCombination
    var mostOptimalJobCombinationsPlateHeight = 0;

    //Loop through each sorted job color hit object
    for(var i = 0; i < sortedHits.length; i ++) {

        //Get the exact hit values for this job color hit object
        var exactHit = getExactHit(sortedHits[i]);

        //Find the most optimal job combination from this job color jobs
        var jobCombination = getMostOptimalJobCombination(0, exactHit.jobs, true);

        //Check to see if the required plate height for this job combination is greater than the previous job combination
        if(getRequiredPlateHeight(jobCombination) > mostOptimalJobCombinationsPlateHeight) {

            //Clear the most optimal job combination
            mostOptimalJobCombination = [];

            //Make this job combination as the most optimal job combination
            mostOptimalJobCombination = appendArray(mostOptimalJobCombination, jobCombination);

            //Make this job combination's required plate height as the most optimal job combination's plate height
            mostOptimalJobCombinationsPlateHeight = getRequiredPlateHeight(jobCombination);
        }

    }
    return mostOptimalJobCombination;
}

/**
 * Method used to find most optimal job combination for the available plate height from the passed in array of jobs. The array of jobs is a 3D array,
 * where the inner most array holds the jobs belonging to the same job color and the array outside that holds different job color jobs that can be
 * combined together while generating the optimal combination. There is a third dimension outside that, will hold different partially matching job
 * color jobs. Job group jobs at this dimensions cannot be combined while generating the optimal combination.
 *
 * Below are the 3 different cases of this 3D job array representation:
 * <pre>
 * STATE 1 : EXACT MATCH
 *
 * [[Jobs of color a~b],[Jobs of color a~c],[Jobs of color a~d]]
 *
 * When an array of this state is passed in, the most optimal job combination will contain jobs from any one of the 3 colors - a~b, a~c or a~d
 *
 * STATE 2 : PARTIAL MATCH (for a~b)
 *
 * [[Jobs of color a~c, Jobs of color a~d],[Jobs of color b~c, Jobs of color b~d]]
 *
 * When an array of this state is passed in, the most optimal job combination will contain jobs from any one of the 2 groups - a~c and/or a~d  OR  b~c and/or b~d
 *
 * STATE 3 : NO MATCH (a~b)
 *
 * [[Jobs of color c, Jobs of color d]]
 *
 * When an array of this state is passed in, the most optimal job combination will contain jobs from any of the 2 colors - c or d or c and d
 * </pre>
 * @param usedPlateHeight - The used plate height
 * @param jobsByJobColorGroups
 * @returns {Array}
 */
function findMostOptimalJobCombinationWithoutBreaking(usedPlateHeight, jobsByJobColorGroups) {

    var mostOptimalJobCombination = [];
    var mostOptimalJobCombinationsRequiredPlateHeight = 0;
    for(var i = 0; i < jobsByJobColorGroups.length; i ++) {
        var mostOptimalColorGroupJobCombination = [];
        for(var j = 0; j < jobsByJobColorGroups[i].length; j ++) {
            var thisCombination = getMostOptimalJobCombination(usedPlateHeight + getRequiredPlateHeight(mostOptimalColorGroupJobCombination) + imageGap, jobsByJobColorGroups[i][j], false);
            var thisCombinationsRequiredPlateHeight = getRequiredPlateHeight(thisCombination);
            mostOptimalColorGroupJobCombination = appendArray(mostOptimalColorGroupJobCombination, thisCombination);
            if(((usedPlateHeight + thisCombinationsRequiredPlateHeight + imageGap) / plateLength) * 100 >= optimalPlatePercentage) {
                break;
            }
        }
        var mostOptimalColorGroupJobCombinationsRequiredPlateHeight = getRequiredPlateHeight(mostOptimalColorGroupJobCombination);
        if(mostOptimalColorGroupJobCombinationsRequiredPlateHeight > mostOptimalJobCombinationsRequiredPlateHeight) {
            mostOptimalJobCombination = [];
            mostOptimalJobCombination = appendArray(mostOptimalJobCombination, mostOptimalColorGroupJobCombination);
            mostOptimalJobCombinationsRequiredPlateHeight = mostOptimalColorGroupJobCombinationsRequiredPlateHeight;
        }

    }

    return mostOptimalJobCombination;
}

/**
 * Method used to find most optimal job combination for the available plate height from the passed in array of jobs. This method is used
 * recursively to find the most optimal job combination, starting with the first job in the passed in array of jobs.
 *
 * @param usedPlateHeight - The used plate height
 * @param jobs - The array of jobs from which the most optimal job combination needs to be created
 * @param splitGroupFlag - If the most optimal job combination can take a sub set of the jobs from the passed in jobs array
 *
 * @returns {Array}
 */
function getMostOptimalJobCombination(usedPlateHeight, jobs, splitGroupFlag) {

    //Find the available plate height
    var availablePlateHeight = plateLength - usedPlateHeight;

    //Array to hold the most optimal job combination
    var mostOptimalJobCombination = [];

    //The required plate height for mostOptimalJobCombination
    var mostOptimalJobCombinationPlateHeight = 0;

    //Loop through each jobs in the given jobs array
    for(var i = 0; i < jobs.length; i ++) {

        //The current job
        var job = jobs[i];

        //If the current job's artHeight is greater than the available plate height, skip this job
        if(job.artHeight > availablePlateHeight) {
            continue;
        }

        //If adding this job to the most optimal job combination creates an optimal plate, pick this job as the most optimal job and break the iteration.
        if(((usedPlateHeight + job.artHeight + imageGap) / plateLength) * 100 >= optimalPlatePercentage) {
            mostOptimalJobCombination = [job];
            break;
        }
        //Check to see if this is not the last job in the passed in array of jobs
        else if(i < jobs.length - 1) {

            //Find the most optimal add on job combination that can fit the remaining available plate height after adding the current job's artHeight and image gap to the initial usedPlateHeight
            var addOnJobCombinations = getMostOptimalJobCombination(usedPlateHeight + job.artHeight + imageGap, removeFirstElement(jobs, i), splitGroupFlag);

            //Check to see if adding the current job's artHeight & imageGap and the required plateHeight for the addOnCombination to the initial usedPlateHeight will make an optimal plate
            if(((usedPlateHeight + getRequiredPlateHeight(addOnJobCombinations) + job.artHeight + imageGap) / plateLength) * 100 >= optimalPlatePercentage) {

                //Clear the most optimal job combination and add the current job to the most optimal job combination
                mostOptimalJobCombination = [job];

                //Append the add on job combination to the most optimal job combination
                mostOptimalJobCombination = appendArray(mostOptimalJobCombination, addOnJobCombinations);

                //The plate is now optimal, so break the iteration
                break;

            }
            //Check to see if the sum of current job's artHeight & imageGap and the required plateHeight for the addOnCombination is greater than the most optimal job combination plate height
            else if(getRequiredPlateHeight(addOnJobCombinations) + job.artHeight + imageGap > mostOptimalJobCombinationPlateHeight) {

                //Clear the most optimal job combination and add the current job to the most optimal job combination
                mostOptimalJobCombination = [job];

                //Append the add on job combination to the most optimal job combination
                mostOptimalJobCombination = appendArray(mostOptimalJobCombination, addOnJobCombinations);

                //Update the most optimal job combination's plate height
                mostOptimalJobCombinationPlateHeight = getRequiredPlateHeight(mostOptimalJobCombination);
            }
        }
        //Check to see if this is the last job in the passed in array of jobs
        else if(i == jobs.length - 1) {

            //Check to see if the current job's artHeight is greater than the most optimal job combination's plate height
            if(job.artHeight > mostOptimalJobCombinationPlateHeight) {

                //Clear the most optimal job combination and add the current job to the most optimal job combination
                mostOptimalJobCombination = [job];
            }
        }
    }
    if(typeof splitGroupFlag === 'undefined' || !splitGroupFlag) {
        if(mostOptimalJobCombination.length != jobs.length) {
            mostOptimalJobCombination = [];
        }
    }
    return mostOptimalJobCombination;

}

function filterHitsByJobColors(sortedHits, jobColors) {
    var filteredHits = [];
    for(var i = 0; i < jobColors.length; i ++) {
        var hitForThisColor = getHitForJobColor(sortedHits, jobColors[i]);
        if(typeof hitForThisColor !== 'undefined') {
            filteredHits[filteredHits.length] = hitForThisColor;
        }
    }
    return filteredHits;
}

function getHitForJobColor(sortedHits, jobColor) {
    var hit;
    var colorKey = concatenateColors(jobColor);
    for(var i = 0; i < sortedHits.length; i ++) {
        if (typeof sortedHits[i][colorKey] !== 'undefined') {
            hit = sortedHits[i];
            break;
        }
    }
    return hit;
}

function getJobsWithColors(sortedHits, colors) {
    var jobsWithGivenColors = [];
    for(var i = 0; i < colors.length; i ++) {
        var jobsWithGivenColors1 = [];
        var hitsForColors = filterHitsByJobColors(sortedHits, colors[i]);
        for(var j = 0; j < colors[i].length; j ++) {
            var exactHit = getExactHit(hitsForColors[j]);
            jobsWithGivenColors1[jobsWithGivenColors1.length] = exactHit.jobs;
        }
        jobsWithGivenColors[jobsWithGivenColors.length] = jobsWithGivenColors1;
    }
    return jobsWithGivenColors;
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

function getMatchingColors(hits) {
    var matchingColors = [];
    for(prop in hits) {
        var match = [];
        match[match.length] = [prop.split('~')];
        matchingColors[matchingColors.length] = match;
    }

    return matchingColors;
}

function getPartialMatchingColors(hit, level) {
    var partialMatchingColors = [];
    for(prop in hit) {
        if(prop.split('^').length == level) {
            var partialMatch = [];
            var partialMatchingJobColors = hit[prop].colors;
            for(var i = 0; i < partialMatchingJobColors.length; i ++) {
                partialMatch[partialMatch.length] = partialMatchingJobColors[i].split('~');
            }
            partialMatchingColors[partialMatchingColors.length] = partialMatch;
        }
    }
    return partialMatchingColors;
}

function getNonMatchingColors(hits, matchingColors) {
    var nonMatchingColors = [];
    var nonMatch = [];
    for(prop in hits) {
        var colors = prop.split('~');
        if(!containsAnyColors(colors, matchingColors)) {
            nonMatch[nonMatch.length] = colors;
        }
    }
    nonMatchingColors[nonMatchingColors.length] = nonMatch;
    return nonMatchingColors;
}

function getColors(hit) {
    var key = '';
    for(prop in hit) {
        if(key == '' || key.length > prop.length ) {
            key = prop;
        }
    }
    return key.split('~');
}

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
                    hits[concatenatedColors] = {'hit': 1, 'artHeight': jobs[i].artHeight, 'heights': [jobs[i].artHeight], 'jobs': [jobs[i]]};
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
                    hits[concatenatedColors]['artHeight'] = hits[concatenatedColors]['artHeight'] + jobs[i].artHeight;
                    hits[concatenatedColors]['heights'][hits[concatenatedColors]['heights'].length] = jobs[i].artHeight;
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

function containsAnyColors(colors, matchingColors) {
    var contains = false;
    for(var i = 0; i < matchingColors.length; i ++) {
        contains = false;
        for(var j = 0; j < colors.length; j ++) {
            if(matchingColors[i] == colors[j]) {
                contains = true;
                break;
            }
        }
        if(contains == true) {
            break;
        }
    }
    return contains;
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

function groupByColors(jobs) {
    var hits = findHits(jobs);
//    console.log(hits);
    return jobs.sort(function(a, b) {return hits[b.color] - hits[a.color]});
}

