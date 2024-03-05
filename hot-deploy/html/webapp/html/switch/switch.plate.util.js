/**
 * Created by Manu on 12/15/2015.
 */

//STRICT - Only optimized plates will be created
var OPTIMIZATION_LEVEL_STRICT = 'STRICT';

//FLEXIBLE - Try to make optimized plates first and will make non-optimized plates with the remaining jobs
var OPTIMIZATION_LEVEL_FLEXIBLE = 'FLEXIBLE';

//NOT used in current version

//SIZE - Try to find same size and same color, but will use different color with the same size, if same color for the current size not found to make the plate optimized
//var OPTIMIZATION_TYPE_SIZE = 'SIZE';

//COLOR - Try to find same size and same color, but will use different size with the same color, if same size for the current color not found to make the plate optimized
//var OPTIMIZATION_TYPE_COLOR = 'COLOR';

//BOTH - Use same size and same color only
//var OPTIMIZATION_TYPE_BOTH = 'BOTH';


var createIterations = 10;
var id = 1;
var defaultDueLeadDays = 1;
var defaultExecutionInterval = 90;
var EXECUTION_TIMES = ["00.00", "12.00", "14.00"];
var OPTIMIZATION_LEVELS = [OPTIMIZATION_LEVEL_STRICT, OPTIMIZATION_LEVEL_FLEXIBLE, OPTIMIZATION_LEVEL_FLEXIBLE];
var HOLIDAYS = ['2015-12-21','2015-12-22','2015-12-25','2016-01-01','2016-05-30','2016-07-04','2016-09-05','2016-11-24'];
var WORKING_WEEKENDS = [];
var inputLocation = [];
var outputLocation = [];


var plateHeight = 1000;
var optimalPlatePercentage = 95;
var imageGap = 100;

function triggerBySwitch() {
    createJobs(); // Create mock jobs and place them in the inputLocation
}

function jobsReady() {
    runPlatingProcess();
}

function runPlatingProcess() {
    var currentExecutionTime = new Date();
    if(isHolidayOrWeekend(currentExecutionTime)) {
        return;
    }
    // Get all the jobs from the input folder
    var jobsList = getJobs();
    print(jobsList);
    setArtHeight(jobsList);
    print(jobsList);
    var groupedJobs = groupJobs(jobsList, currentExecutionTime);
    console.log(groupedJobs);
    var plate = createPlate(groupedJobs, currentExecutionTime);
    console.log(plate);
    var nextExecutionInterval = plate.length == 0 ? defaultExecutionInterval : getNextExecutionTimeInterval(currentExecutionTime);
    console.log(nextExecutionInterval);
}

/**
 * Method used to find the current execution time falls on a holiday or a non-working weekend
 * @param currentExecutionTime - The current execution time
 * @returns {*|boolean} - 'true' is the current execution time falls on a holiday or non-working weekend
 */
function isHolidayOrWeekend(currentExecutionTime) {
    return (isWeekend(currentExecutionTime) || isHoliday(currentExecutionTime)) && !isWorkingWeekend(currentExecutionTime)
}

/**
 * Method used to find the current execution time falls on a holiday
 * @param currentExecutionTime - The current execution time
 * @returns {boolean} - 'true' if the current execution time falls on a holiday
 */
function isHoliday(currentExecutionTime) {
    var currentDay = convertDateTimeToDateOnly(currentExecutionTime).getTime();
    for(var i = 0; i < HOLIDAYS.length; i ++) {
        var holiday = convertStringDateToDate(HOLIDAYS[i], '-').getTime();
        if(currentDay == holiday) {
            return true;
        }
    }
    return false;
}
/**
 * Method used to find the current execution time falls on a weekend
 * @param currentExecutionTime - The current execution time
 * @returns {boolean} - 'true' if the current execution time falls on a weekend
 */
function isWeekend(currentExecutionTime) {
    return currentExecutionTime.getDay() == 6 || currentExecutionTime.getDay() == 0;
}

/**
 * Method used to find the current execution time falls on a working weekend
 * @param currentExecutionTime - The current execution time
 * @returns {boolean} - 'true' if the current execution time falls on a working weekend
 */
function isWorkingWeekend(currentExecutionTime) {
    var currentDay = convertDateTimeToDateOnly(currentExecutionTime).getTime();
    for(var i = 0; i < WORKING_WEEKENDS.length; i ++) {
        var workingWeekend = convertStringDateToDate(WORKING_WEEKENDS[i], '-').getTime();
        if(currentDay == workingWeekend) {
            return true;
        }
    }
    return false;
}


/**
 * Method used to find the dueLeadDays, based on weekends and holidays
 * @param currentExecutionTime - The current execution time
 */
function getLeadDueDays(currentExecutionTime) {
    var dueLeadDays = defaultDueLeadDays;
    var currentDay = convertDateTimeToDateOnly(currentExecutionTime);
    while(isHolidayOrWeekend(new Date(currentDay.getTime() + (dueLeadDays * 24 * 60 * 60 * 1000)))) {
        dueLeadDays ++;
    }
    return dueLeadDays;
}

/**
 * Method used to pick jobs that are due with in the given dueLeadDays and then group them by their due in days.
 * Prior due orders from all prior due dates are grouped together in a single bucket that has a 0 dueIn days value
 * @param jobs - All the jobs at he input location
 * @param currentExecutionTime - The current execution time
 * @returns {{}} - A map of jobs are due with in the given dueLeadDays grouped by dueInDays and then by size. Jobs with in a give grouped size are sorted by their color and then by RUSH and then by STANDARD production
 */
function groupJobs(jobs, currentExecutionTime) {
    var groupedJobs = {};
    for(var i = 0; i < jobs.length; i ++) {
        var dueIn = jobDueIn(jobs[i].dueDate, currentExecutionTime);
        // Pick only jobs that are due in dueLeadDays
        if(dueIn <= getLeadDueDays(currentExecutionTime)) {
            if (groupedJobs.hasOwnProperty(dueIn)) {
                groupedJobs[dueIn][groupedJobs[dueIn].length] = jobs[i];
            } else {
                groupedJobs[dueIn] = [jobs[i]];
            }
        }
    }
    for(prop in groupedJobs) {
        if(groupedJobs.hasOwnProperty(prop)) {
            groupedJobs[prop] = groupJobsBySize(groupedJobs[prop]);
        }
    }
    return groupedJobs;
}

/**
 * Method used to group jobs that are due in a given number of days by their size
 * @param jobs - Jobs that are due in a given number of due in days
 * @returns {{}} - The jobs that are due in a given number of days grouped by size
 */
function groupJobsBySize(jobs) {
    var groupedJobs = {};
    for(var i = 0; i < jobs.length; i ++) {
        var currentSize = jobs[i].size1;
        if(groupedJobs.hasOwnProperty(currentSize)) {
            groupedJobs[currentSize][groupedJobs[currentSize].length] = jobs[i];
        } else {
            groupedJobs[currentSize] = [jobs[i]];
        }
    }
    for(prop in groupedJobs) {
        groupedJobs[prop] = groupedJobs[prop].sort(function(a, b) {return a.color > b.color ? 1 : a.color < b.color ? -1 : a.rush == 'N' && a.rush != b.rush ? 1 : a.rush == 'Y' && a.rush != b.rush ? -1 : parseFloat(a.artHeight) > parseFloat(b.artHeight) ? -1 : parseFloat(a.artHeight) < parseFloat(b.artHeight) ? 1 : 0;});
    }

    return groupedJobs;
}

/**
 * Method used to find the number of days in which the given dueDate is due
 * @param _dueDate - The due date of a single job
 * @param currentExecutionTime - The current execution time
 * @returns {number} - The number of days in which this dueDate is due
 */
function jobDueIn(_dueDate, currentExecutionTime) {
    var dueIn = 0;
    var dueDate = new Date(_dueDate.getFullYear(), _dueDate.getMonth(), _dueDate.getDate());

    var today = new Date(currentExecutionTime.getFullYear(), currentExecutionTime.getMonth(), currentExecutionTime.getDate());

    dueIn = (dueDate.getTime() - today.getTime());
    if(dueIn < 0) {
        dueIn = 0;
    } else {
        dueIn = dueIn / (24 * 60 * 60 * 1000);
    }
    return dueIn;
}

/**
 * The method used to get the keys of a map as an ascending sorted array
 * @param obj - The map
 * @returns {Array} - The array of keys of the map that are sorted ascending
 */
function getSortedProperties(obj) {
    var props = [];
    for(prop in obj) {
        if(obj.hasOwnProperty(prop)) {
            props[props.length] = prop;
        }
    }
    props.sort();
    return props;
}

/**
 * Method used to set the current optimization level and then go through jobs that are due starting from the one that is due in least days
 * and try to create a plate. This method will try to create optimal plate only if the current optimization level is STRICT. Event if the
 * current optimization level is STRICT and if there are prior due orders, this method will override the optimization level to FLEXIBLE,
 * so that the prior due orders can be plated on a un-optimal plate, if an optimal plate of due orders can't be created.
 *
 * @param groupedJobs - The jobs that are due in the given due lead days grouped by due in days and then by size.
 * @param currentExecutionTime - The current execution time
 * @returns {Array} - The array of jobs that can be plated on a single plate.
 */
function createPlate(groupedJobs, currentExecutionTime) {
    var plate = [];
    var optimizationLevel = getOptimizationLevel(currentExecutionTime);
    var dueInDays = getSortedProperties(groupedJobs);
    var optimalPlateOnly = optimizationLevel == OPTIMIZATION_LEVEL_STRICT;

    for(var i = 0; i < dueInDays.length; i ++) {
        if(dueInDays[i] == 0) {
            optimalPlateOnly = false;
        } else {
            optimalPlateOnly = optimizationLevel == OPTIMIZATION_LEVEL_STRICT;
        }
        plate = buildPlate(groupedJobs[dueInDays[i]], optimalPlateOnly);
        if(plate.length > 0) {
            break;
        }
    }

    return plate;
}

/**
 * Method used to create an array of jobs that can be plated on a single plate. Given below are the business rules:
 * 1 - This method will create an optimal plate(if optimalPlateOnly flag is true or false) and if not possible create a non-optimal plate with highest plateUsedHeight, if the optimalPlateOnly flag is 'false'
 * 2 - While creating a non-optimal plate this method will got through each sizes and will try to build a non-optimal plate with highest plateUsedHeight
 * @param jobsForTheDay - Jobs having the same dueDate grouped by sizes or all jobs having prior dueDates (one or more) grouped by sizes.
 * @param optimalPlateOnly - Boolean flag to specify if this method should create optimal plates only or not
 * @returns {Array} - Array of jobs that can be plated on a single plate
 */
function buildPlate(jobsForTheDay, optimalPlateOnly) {
    var previousPlate = [];
    var previousPlateUsedHeight = 0;
    var currentPlate = [];
    var optimalPlate = false;
    for(prop in jobsForTheDay) {
        if(jobsForTheDay.hasOwnProperty(prop)) {
            var sameSizeJobs = jobsForTheDay[prop];
            var plateUsedHeight = 0;
            currentPlate = [];
            for(var i = 0; i < sameSizeJobs.length; i ++) {
                var job = sameSizeJobs[i];
                var gap = i == 0 ? 0 : imageGap;
                if(plateUsedHeight + gap + job.artHeight <= plateHeight) {
                    currentPlate[currentPlate.length] = job;
                    plateUsedHeight += gap + job.artHeight;
                    if((plateUsedHeight/plateHeight) * 100 >= optimalPlatePercentage) {
                        optimalPlate = true;
                        break;
                    }
                }
            }
            if(optimalPlate) { //if this is an optimal plate, no need to look for another size
                break;
            } else if(optimalPlateOnly) { //If the current plate is not optimal and we require only optimal plate, clear the jobs that's already added to the plate.
                currentPlate = [];
            } else if(!optimalPlateOnly) { //If the current plate is not optimal and we can has non-optimal plates, store the current plate jobs in an array and look again to create a plate with greater plateUsedHeight than this one
                if(plateUsedHeight > previousPlateUsedHeight) {
                    previousPlate = currentPlate;
                } else {
                    currentPlate = previousPlate;
                }
            }
        }
    }
    return currentPlate;
}

/**
 * Method used to find the current execution slot index or the next execution interval, if executionTimeIntervalFlag is true
 * @param currentExecutionTime - The current execution time
 * @param executionTimeIntervalFlag - If next execution time interval is returned instead of execution slot index
 * @returns {*} - The execution slot index or the next execution time interval depending on executionTimeIntervalFlag
 */
function getExecutionSlotIndexOrNextExecutionTimeOut(currentExecutionTime, executionTimeIntervalFlag) {
    if(typeof currentExecutionTime === 'undefined') {
        currentExecutionTime = new Date();
    }
    var nextExecutionTimeIntervalMode = typeof executionTimeIntervalFlag === 'undefined' ? false : executionTimeIntervalFlag;
    var nextExecutionInterval = -1;
    var slotIndex = -1;

    if(EXECUTION_TIMES.length == 1) {
        return 0;
    } else {
        var hour = currentExecutionTime.getHours();
        var minute = currentExecutionTime.getMinutes();

        for(var i = 0; i < EXECUTION_TIMES.length && slotIndex < 0; i ++) {
            var time1 = EXECUTION_TIMES[i].split('.');
            var hour1 = parseInt(time1[0]);
            var minute1 = parseInt(time1[1]);
            var time2 = ['-1', '-1'];
            var hour2 = -1;
            var minute2 = -1;
            var lastSlot = false;
            if(i + 1 < EXECUTION_TIMES.length) {
                time2 = EXECUTION_TIMES[i + 1].split('.');
                hour2 = parseInt(time2[0]);
                minute2 = parseInt(time2[1]);
            } else {
                lastSlot = true;
                time2 = EXECUTION_TIMES[0].split('.');
                hour2 = parseInt(time2[0]);
                minute2 = parseInt(time2[1]);
            }
            var date = new Date(2015, 11, 1, hour, minute, 0).getTime();
            var date1 = new Date(2015, 11, 1, hour1, minute1, 0).getTime();
            var date2 = new Date(2015, 11, lastSlot ? 2 : 1, hour2, minute2, 0).getTime();
            if(date >= date1 && date < date2) {
                slotIndex = i;
                nextExecutionInterval = date2 - date;
            }
        }
    }

    return nextExecutionTimeIntervalMode ? parseInt(nextExecutionInterval / 1000) : slotIndex;
}

/**
 * Method used to find the optimization level for the current execution
 * @param currentExecutionTime - The current execution time
 * @returns {string} - The optimization level for the current execution
 */
function getOptimizationLevel(currentExecutionTime) {
    var executionSlotIndex = getExecutionSlotIndexOrNextExecutionTimeOut(currentExecutionTime);
    var optimizationLevel = OPTIMIZATION_LEVEL_FLEXIBLE;
    if(executionSlotIndex > -1) {
        optimizationLevel = OPTIMIZATION_LEVELS[executionSlotIndex];
    }
    return optimizationLevel;
}

/**
 * Method used to find the next execution time interval
 * @param currentExecutionTime - The current execution time
 * @returns {*} - The next execution time interval
 */
function getNextExecutionTimeInterval(currentExecutionTime) {
    return getExecutionSlotIndexOrNextExecutionTimeOut(currentExecutionTime, true);
}

function convertDateTimeToDateOnly(dateTime) {
    return new Date(dateTime.getFullYear(), dateTime.getMonth(), dateTime.getDate());
}

function convertStringDateToDate(stringDate, delimiter) {
    var date = stringDate.split(delimiter);
    return new Date(parseInt(date[0]), parseInt(date[1]) - 1, parseInt(date[2]));
}

/**
 * Mock method to get the jobs
 * @returns {Array}
 */
function getJobs() {
    return inputLocation;
}

/**
 * Mock method to create a job instance
 *
 * @param id
 * @param size
 * @param color
 * @param dueDate
 * @param isRush
 * @param height
 * @returns {{}}
 */
function createJob(id, size, color, dueDate, isRush, height) {
    var job = {};
    job.id = 'JOB-' + id;
    job.size1 = size;
    job.color = color;
    job.dueDate = dueDate;
    job.rush = isRush;
//    job.height = height;
    return job;
}

/**
 * Mock method used to set the art height
 * @param jobs
 */
function setArtHeight(jobs) {
    jobs.sort(function(a, b) {
        return parseInt(a.size1) > parseInt(b.size1) ? 1 : parseInt(a.size1) < parseInt(b.size1) ? -1 : 0;
    });
    for(var i = 0; i < jobs.length; i ++) {
        jobs[i].artHeight = getHeight(i + 1, [5]);
    }
    console.log(jobs);
}

/**
 * Mock method to create dynamic mod
 * @param mod
 * @returns {*[]}
 */
function getNextMod(mod) {
    var sizeMod = ++ mod[0];
    var colorMod = ++ mod[1];
    var dueDateMod = ++ mod[2];
    if(sizeMod > 5) {
        sizeMod = 0;
    }
    if(colorMod > 7) {
        colorMod = 0;
    }
    if(dueDateMod > 5) {
        dueDateMod = 0;
    }
    return [sizeMod, colorMod, dueDateMod];
}

var mod = [3, 4, 4];
/**
 * Mock method to create mock jobs
 */
function createJobs() {
    if(createIterations > 0) {
        setTimeout(function() {
            for (var i = 0, j = 1; i < 10; i++) {
                inputLocation[inputLocation.length] = createJob(id, getSize(id, mod), getColor(id, mod), getDueDate(id, mod), i % 2 == 0 || i % 4 == 1 ? 'N' : 'Y', getHeight(id, mod));
                mod = getNextMod(mod);
                id++;
            }
            createIterations --;
            createJobs();
        }, 1000);
    } else {
        jobsReady();
    }

}

/**
 * Mock method to create dynamic sizes
 * @param id
 * @param mod
 * @returns {string}
 */
function getSize(id, mod) {
    switch (id % mod[0]) {
        case 1:
            return '10';
        case 2:
            return '7';
        case 3:
            return '9';
        case 0:
            return '5';
        default:
            return '12';
    }
}

/**
 * Mock method to create dynamic art height
 * @param id
 * @param mod
 * @returns {number}
 */
function getHeight(id, mod) {
    switch (id % mod[0] % 2) {
        case 1:
            return 180;
        case 2:
            return 230;
        case 3:
            return 310;
        case 0:
            return 290;
        default:
            return 420;
    }
}

/**
 * Mock method to create dynamic color
 * @param id
 * @param mod
 * @returns {string}
 */
function getColor(id, mod) {
    switch (id % mod[1]) {
        case 1:
            return 'green';
        case 2:
            return 'red';
        case 3:
            return 'blue';
        case 0:
            return 'black';
        default:
            return 'yellow';
    }
}

/**
 * Mock method used to create dynamic due dates
 * @param id
 * @param mod
 * @returns {Date}
 */
function getDueDate(id, mod) {
//    return id % 3 == 0 ? new Date(2015,11,17) : id % 3 == 1 ? new Date(2015,11,18) : new Date(2015,11,19);
    switch (id % mod[2]) {
        case 1:
            return new Date(2015,11,18);
        case 2:
            return new Date(2015,11,21);
        case 3:
            return new Date(2015,11,22);
        case 0:
            return new Date(2015,11,16);
        default:
            return new Date(2015,11,17);
    }
}

/**
 * Mock method use to format the data object
 * @param date
 * @returns {string}
 */
function formatDate(date) {
    return typeof date === 'undefined' ? '' : (date.getMonth() + 1) + '/' + date.getDate() + '/' +  date.getFullYear();
}

/**
 * Mock method used to print the given array
 * @param arr
 */
function print(arr) {
    for(var i =0; i < arr.length; i ++) {
        var job = arr[i];
        console.log(job.id + ' -- ' + formatDate(job.dueDate) + ' -- ' + job.size1 + ' -- ' + job.color + ' -- ' + job.rush + ' -- ' + job.artHeight)
    }
}
var msg1 = 'Manu Prasad';
var msg2 = ['Manu', 'Dhanya', 'Rishi', 'Arjun'];
var msg3 = {1:'Manu', 2: 'Dhanya', 3: 'Rishi', 4: 'Arjun'};
function log(msg) {
    var type = typeof msg;
    if(type == 'string') {
       console.log(msg);
    } else if(type == 'object') {
        var props = getSortedProperties(msg);
        var str = '';
        if(typeof props !== 'undefined' && props.length > 0) {
            str += '{';
            for(var i = 0; i < props.length; i ++) {
                str += (i > 0 ? ',' : '') + props[i] + ':' + msg[props[i]];
            }
            str += '}';
            console.log(str);
        } else if(msg.length !== 'undefined' && msg.length > 0) {
            str = '[';
            for (var i = 0; i < msg.length; i ++) {
                str += (i > 0 ? ',' : '') + msg[i];
            }
            str += ']';
            console.log(str);
            JSON.stringify(obj);
        }
    }
}