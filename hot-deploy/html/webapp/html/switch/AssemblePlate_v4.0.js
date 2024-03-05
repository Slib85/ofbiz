/**
 * Created by Manu on 12/22/2015.
 */
//------------------------------------------------
// Assemble Plates
/*

 First Plate is not added to the batch and it is removed from the incoming folder

 Author: Envelopes.com
 Date:   December 21, 2015
 Copyright © 2015

 Version History:
 1.0 - Plating Script to create only optimized plates during mind-night run and non-optimized plates during two additional runs after that (12 PM & 2PM).This
 version also supports skipping the jobs to run on Weekends and Holidays.

 Bascic Functional Specification
 This script will monitor one input folder and attempt to make "batches" from the incoming jobs.
 This script will have one outgoing folder.
 Build an array of jobs in the incoming folder
 Select the jobs that are due or prior due

 When the quantity of matching jobs is reached, the batches will be grouped into a folder and moved to the outgoing folder.

 */


//######################################################## CONSTANTS ######################################################//

//STRICT - Only optimized plates will be created
var OPTIMIZATION_LEVEL_STRICT = 'STRICT';

//FLEXIBLE - Try to make optimized plates first and will make non-optimized plates with the remaining jobs
var OPTIMIZATION_LEVEL_FLEXIBLE = 'FLEXIBLE';

var defaultDueLeadDays = 1;
var defaultExecutionInterval = 90;
var EXECUTION_TIMES = ["00.00", "12.00", "14.00"];
var OPTIMIZATION_LEVELS = [OPTIMIZATION_LEVEL_STRICT, OPTIMIZATION_LEVEL_FLEXIBLE, OPTIMIZATION_LEVEL_FLEXIBLE];
var HOLIDAYS = ['2015-12-25','2016-01-01','2016-05-30','2016-07-04','2016-09-05','2016-11-24'];
var WORKING_WEEKENDS = [];

//#########################################################################################################################//
var console;
const cLogDebug = 2;//default is -1
const cLogInfo = 1;
const cLogWarning = 2;
const cLogError = 3;
const cLogStart = 4;
const cLogProgress = 5;
const cLogEnd = 6;

var cDebugLevel = 0;//level 1 = "on", level 0 = "off"



// Is invoked each time a new job arrives in one of the input folders for the flow element.
// The newly arrived job is passed as the second parameter.
function jobArrived( s : Switch, job : Job )
{
    //We are following the timeFired() function, so nothing need to be done here
}

// Is invoked at regular intervals regardless of whether a new job arrived or not.
// The interval can be modified with s.setTimerInterval().
function timerFired( s : Switch )
{
    console = new Console(s);
    var currentExecutionTime = new Date();	//the current execution time
    console.log(cLogStart,"Start 1 Color Press - Assemble Plates " + currentExecutionTime);
    s.setTimerInterval(defaultExecutionInterval);


    if(isHolidayOrWeekend(currentExecutionTime)) {
        s.setTimerInterval(defaultExecutionInterval);
        console.log(cLogDebug,'Today is a HolidayOrWeekend, so nothing to plate');
    } else {
        //Get the property Values
        var plateWidth = s.getPropertyValue("plateWidth") * 72;							//convert from inches to points
        var plateLength = parseFloat(s.getPropertyValue("plateLength")) * 72;			//convert from inches to points
        var gripper = parseFloat(s.getPropertyValue("gripper")) * 72;					//convert from inches to points
        var tailEdge = parseFloat(s.getPropertyValue("tailEdge")) * 72;					//convert from inches to points
        var imageGap = s.getPropertyValue("imageGap") * 72;								//convert from inches to points
        var originanImageGap = imageGap;
        var optimalPlatePercentage = s.getPropertyValue("minUsagePct");					//the height used percentage of a plate, when it can be considered as as optimal plate

        plateLength = plateLength - gripper - tailEdge;									//correct the actual platable length by subtracting the gripper and tailEdge

        var plateProperties = {};
        plateProperties['plateWidth'] = plateWidth;
        plateProperties['plateLength'] = plateLength;
        plateProperties['imageGap'] = imageGap;
        plateProperties['optimalPlatePercentage'] = optimalPlatePercentage;

        console.log(cLogDebug, 'Executing script with Plate Properties', plateProperties);

        var dueJobs = [];																		//array to hold all jobs that are due

        //Get the jobs that are due
        console.log(cLogStart,"Start getDueJobList");
        dueJobs = getDueJobList(s, currentExecutionTime);
        console.log(cLogDebug, dueJobs.length + " items returned");
        console.log(cLogEnd,"End getDueJobList");

        //Group the due jobs by their dueInDays and then by size
        console.log(cLogStart,"Start groupJobs");
        var groupedJobs = groupJobs(dueJobs, currentExecutionTime);
        console.log(cLogEnd,"End groupJobs");

        var plateJobs = createPlate(groupedJobs, plateProperties, currentExecutionTime);

        if(plateJobs.length > 0) {
            batchJobs(s, plateJobs);
        } else {
            console.log(cLogDebug,'Nothing to plate');
        }

        var nextExecutionInterval = plateJobs.length != 0 ? defaultExecutionInterval : getNextExecutionTimeInterval(currentExecutionTime);
        //console.log(cLogDebug, 'Next Execution Interval - ' + nextExecutionInterval + ' sec');
        //s.setTimerInterval(nextExecutionInterval);
        s.setTimerInterval(defaultExecutionInterval);
        console.log(cLogEnd,"End 1 Color Press - Assemble Plates " + new Date());
    }
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
    return currentExecutionTime.getDay() == 6 || currentExecutionTime.getDay() == 7;
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

function printDueJobSummary(incomingJobs) {
    var numberOfJobs = incomingJobs.getCount();
    var jobSummary = {};
    for(var i = 0; i < numberOfJobs; i ++) {
        try {
            var thisJob = incomingJobs.getItem(i);
            var dueDate = thisJob.getPrivateData("dueDate");
            if(typeof jobSummary[dueDate] === 'undefined') {
                jobSummary[dueDate] = 1;
            } else {
                jobSummary[dueDate] = ++jobSummary[dueDate];
            }
        }catch(err){
            console.log(cLogError,"Problem adding info to jobSummary: " + err);
            console.log(cLogError,"thisJob: " + thisJob);
            console.log(cLogError,"thisJobPath: " + thisJobPath);
            console.log(cLogError,"thisJobName: " + thisJobName);
            console.log(cLogError,"thisPlateDueDate: " + thisPlateDueDate);
        }
    }
    console.log(cLogDebug, '---------------------------------Jobs Summary-------------------------------------');
    console.log(cLogDebug, 'Total Jobs : ' + numberOfJobs);
    for(prop in jobSummary) {
        console.log(cLogDebug, prop + ' - ' + jobSummary[prop] + ' Jobs');
    }
    console.log(cLogDebug, '-----------------------------------------------------------------------------------');
}

/**
 * Method used to get all the jobs that are due from the input folder
 *
 * @param s
 * @param currentExecutionTime
 * @returns {Array}
 */
function getDueJobList(s, currentExecutionTime){
    var incomingJobs = s.getJobs();
    var numberOfJobs = incomingJobs.getCount();
    var dueLeadDays = getLeadDueDays(currentExecutionTime);
    var jobList = [];

    for (var i=0; i<numberOfJobs; i++) {
        try{
            var thisJob = incomingJobs.getItem(i);
            var thisJobPath = thisJob.getPath();
            var thisJobName = thisJob.getName();
            var thisJobEnvelopeWidth = thisJob.getPrivateData("envelopeWidth");
            var thisJobArtHeight = thisJob.getPrivateData("artHeight");
            var thisPlateOrderIndex = thisJob.getPrivateData("theFullIndex");
            var thisPlateDueDate = thisJob.getPrivateData("dueDate");
            var isRush = thisJob.getPrivateData('productionTime') == 'RUSH' ? 'Y' : 'N';
            var dueInDays = jobDueIn(thisPlateDueDate, currentExecutionTime);
            var color = getColor(thisJob);
            //console.log(cLogDebug, thisJobName + ' is dueIn: ' + dueInDays + ' day(s)');
            //console.log(cLogDebug, 'isRush:' + isRush + ' [ ' + thisJob.getPrivateData('productionTime') + ' ] ');
            //console.log(cLogDebug, 'color:' + color);
            if(dueInDays <= dueLeadDays) {
                var jobObject = {};
                jobObject["jobPath"] = thisJobPath;
                jobObject["plateIndex"] = thisPlateOrderIndex;
                jobObject["envelopeWidth"] = thisJobEnvelopeWidth;
                jobObject["artHeight"] = thisJobArtHeight;
                jobObject["dueDate"] = thisPlateDueDate;
                jobObject["dueIn"] = dueInDays;
                jobObject["color"] = color;
                jobObject["rush"] = isRush;
                jobList.push(jobObject);
            }
        } catch(err){
            console.log(cLogError,"Problem adding info to jobList: " + err);
            console.log(cLogError,"thisJob: " + thisJob);
            console.log(cLogError,"thisJobPath: " + thisJobPath);
            console.log(cLogError,"thisJobName: " + thisJobName);
            console.log(cLogError,"thisPlateDueDate: " + thisPlateDueDate);
        }
    }
    console.log(cLogDebug, 'Found ' + jobList.length + ' jobs that are due');
    console.log(cLogDebug, 'Due Jobs -', jobList);
    return jobList;
}
function getColor(job) {
    /*var inkColor1 = job.getPrivateData("inkColorBack1") != '' ? job.getPrivateData("inkColorBack1") : job.getPrivateData("inkColorFront1");
     var inkColor2 = job.getPrivateData("inkColorBack2") != '' ? job.getPrivateData("inkColorBack2") : job.getPrivateData("inkColorFront2");
     var inkColor3 = job.getPrivateData("inkColorBack3") != '' ? job.getPrivateData("inkColorBack3") : job.getPrivateData("inkColorFront3");
     var inkColor4 = job.getPrivateData("inkColorBack4") != '' ? job.getPrivateData("inkColorBack4") : job.getPrivateData("inkColorFront4");*/

    var inkColor1 = job.getPrivateData("inkColor1").replace(' ', '').toLowerCase();
    var inkColor2 = job.getPrivateData("inkColor2").replace(' ', '').toLowerCase();
    var inkColor3 = job.getPrivateData("inkColor3").replace(' ', '').toLowerCase();
    var inkColor4 = job.getPrivateData("inkColor4").replace(' ', '').toLowerCase();
    var colors = [];
    colors[0] = inkColor1;
    if(inkColor2 != '') {
        colors[1] = inkColor2;
        if(inkColor3 != '') {
            colors[2] = inkColor3;
            if(inkColor4 != '') {
                colors[3] = inkColor4;
            }
        }
    }
    colors = colors.sort();
    var color = '';
    for(var i = 0; i < colors.length; i ++) {
        color  = color + (i > 0 ? '~' : '') + colors[i];
    }

    return color;
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
 * Method used to find the number of days in which the given dueDate is due
 * @param _dueDate - The due date of a single job
 * @param currentExecutionTime - The current execution time
 * @returns {number} - The number of days in which this dueDate is due
 */
function jobDueIn(_dueDate, currentExecutionTime) {
    var dueIn = 0;
    _dueDate = convertStringDateToDate(_dueDate, '-');
    var dueDate = new Date(_dueDate.getYear(), _dueDate.getMonth(), _dueDate.getDate());

    var today = new Date(currentExecutionTime.getYear(), currentExecutionTime.getMonth(), currentExecutionTime.getDate());

    dueIn = (dueDate.getTime() - today.getTime());
    if(dueIn < 0) {
        dueIn = 0;
    } else {
        dueIn = dueIn / (24 * 60 * 60 * 1000);
    }
    //console.log(cLogDebug, 'dueDate:' , convertDateToString(dueDate));
    //console.log(cLogDebug, 'today:' , convertDateToString(today));
    //console.log(cLogDebug, 'dueIn:' , dueIn);

    return dueIn;
}

/**
 * Method used to group jobs that are due by their dueInDays.
 * Prior due orders from all prior due dates are grouped together in a single group that has a 0 dueInDays value
 * @param jobs - All the due jobs
 * @param currentExecutionTime - The current execution time
 * @returns {{}} - A map of jobs are due with in the given dueLeadDays grouped by dueInDays and then by size. Jobs with in a give grouped size are sorted by their color and then by RUSH and then by STANDARD production
 */
function groupJobs(jobs, currentExecutionTime) {
    var groupedJobs = {};
    for(var i = 0; i < jobs.length; i ++) {
        var dueIn = jobDueIn(jobs[i]['dueDate'], currentExecutionTime);
        if (typeof groupedJobs[dueIn] !== 'undefined') {
            groupedJobs[dueIn][groupedJobs[dueIn].length] = jobs[i];
        } else {
            groupedJobs[dueIn] = [jobs[i]];
        }
    }
    for(prop in groupedJobs) {
        if(typeof groupedJobs[prop] !== 'undefined') {
            var dueIn = prop;
            var sameSizeJobs = groupJobsBySize(groupedJobs[dueIn]);
            groupedJobs[dueIn] = sameSizeJobs;
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
        var currentSize = jobs[i].envelopeWidth;
        if(typeof groupedJobs[currentSize] !== 'undefined') {
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
 * Method used to set the current optimization level and then go through jobs that are due starting from the one that is due in least days
 * and try to create a plate. This method will try to create optimal plate only if the current optimization level is STRICT. Event if the
 * current optimization level is STRICT and if there are prior due orders, this method will override the optimization level to FLEXIBLE,
 * so that the prior due orders can be plated on a un-optimal plate, if an optimal plate of due orders can't be created.
 *
 * @param groupedJobs - The jobs that are due in the given due lead days grouped by due in days and then by size.
 * @param plateProperties - The properties of the plate like plateLength, plateWidth, imageGap, optimalPlatePercentage, etc
 * @param currentExecutionTime - The current execution time
 * @returns {Array} - The array of jobs that can be plated on a single plate.
 */
function createPlate(groupedJobs, plateProperties, currentExecutionTime) {
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
        plate = buildPlate(groupedJobs[dueInDays[i]], plateProperties, optimalPlateOnly);
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
 * @param plateProperties - The properties of the plate like plateLength, plateWidth, imageGap, optimalPlatePercentage, etc
 * @param optimalPlateOnly - Boolean flag to specify if this method should create optimal plates only or not
 * @returns {Array} - Array of jobs that can be plated on a single plate
 */
function buildPlate(jobsForTheDay, plateProperties, optimalPlateOnly) {
    var plateLength = plateProperties['plateLength'];
    var plateWidth = plateProperties['plateWidth'];
    var imageGap = plateProperties['imageGap'];
    var optimalPlatePercentage = plateProperties['optimalPlatePercentage'];
    var previousPlate = [];
    var previousPlateUsedLength = 0;
    var currentPlate = [];
    var optimalPlate = false;
    for(prop in jobsForTheDay) {
        if(typeof jobsForTheDay[prop] !== 'undefined') {
            var sameSizeJobs = jobsForTheDay[prop];
            var plateUsedLength = 0;
            currentPlate = [];
            for(var i = 0; i < sameSizeJobs.length; i ++) {
                var job = sameSizeJobs[i];
                var gap = i == 0 ? 0 : imageGap;
                if(plateUsedLength + gap + parseFloat(job.artHeight) <= plateLength) {
                    currentPlate[currentPlate.length] = job;
                    plateUsedLength += gap + parseFloat(job.artHeight);
                    if((plateUsedLength/plateLength) * 100 >= optimalPlatePercentage) {
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
                if(plateUsedLength > previousPlateUsedLength) {
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
            var date = new Date(2015, 12, 1, hour, minute, 0).getTime();
            var date1 = new Date(2015, 12, 1, hour1, minute1, 0).getTime();
            var date2 = new Date(2015, 12, lastSlot ? 2 : 1, hour2, minute2, 0).getTime();
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

/**
 * Method used to convert dateTime to a date
 * @param dateTime
 * @returns {*}
 */
function convertDateTimeToDateOnly(dateTime) {
    return new Date(dateTime.getYear(), dateTime.getMonth(), dateTime.getDate());
}

/**
 * Method used to convert a string type date to a date of Date type
 * @param dateTime
 * @returns {*}
 */
function convertStringDateToDate(stringDate, delimiter) {
    var date = stringDate.split(delimiter);
    return new Date(parseInt(date[0], 10), parseInt(date[1], 10), parseInt((date[2].indexOf(' ') > -1 ? date[2].substring(0,date[2].indexOf(' ')) : date[2]), 10));
}

function convertDateToString(date) {
    return date.getYear() + '-' + (date.getMonth() < 10 ? '0' : '') + date.getMonth() + '-' + (date.getDate() < 10 ? '0' : '') + date.getDate() + ' ' + (date.getHours() < 10 ? '0' : '') + date.getHours() + '_' + (date.getMinutes() < 10 ? '0' : '') + date.getMinutes() + '_' + (date.getSeconds() < 10 ? '0' : '') + date.getSeconds();
}

/**
 * The method used to get the keys of a map as an ascending sorted array
 * @param obj - The map
 * @returns {Array} - The array of keys of the map that are sorted ascending
 */
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

function batchJobs(s, plateJobs){

    console.log(cLogDebug, "Creating the batch to plate.");

    for(var i = 0; i < plateJobs.length; i ++){
        console.log(cLogDebug,"PlateJobs job " + i + " is ", plateJobs[i]['jobPath']);
    }

    //STEP 1
    //create the batch folder name
    //creat a batch ID based on the names of each of the jobs in the batch
    var d = new Date();
    var batchID = "Batch_" + d.getTime();

    //STEP 1
    //make the batch folder
    var tempBatchFolder = s.createPathWithName(batchID);

    var batchFolder = new Dir(tempBatchFolder);
    try{
        batchFolder.mkdir(tempBatchFolder);
        console.log(cLogDebug," Created tempBatchFolder: " + tempBatchFolder);

    }
    catch(err){
        console.log(cLogDebug,"Problem creating tempBatchFolder: " + err);
        console.log(cLogDebug,"tempBatchFolder: " + tempBatchFolder);
        thisJob.fail("Failed to create the orphan batch folder.");
    }
    s.sleep(2); //make sure the folder is created before we write to it

    //STEP 2 copy the matching files to the batch folder
    //       and send the original to NULL (delete)

    var thisJob;
    var thePlateLengthUsed = 0;

    for(var i = 0; i < plateJobs.length; i ++){
        var plateJobPath = plateJobs[i]['jobPath'];
        //get the file name
        var outputFileName = getJobName(s, plateJobPath);//strip the path off
        var outputFileNameLength = outputFileName.length;
        outputFileName = outputFileName.substring(0,outputFileNameLength-4) + "_X_" + i + outputFileName.substring(outputFileNameLength-4); //- add a counter for duplicate named jobs

        thePlateLengthUsed = parseFloat(thePlateLengthUsed) + parseFloat(getJobHeight(s, outputFileName));

        console.log(cLogDebug, "The job height is -" + getJobHeight(s,outputFileName) + "-");
        console.log(cLogDebug, "The job folder name is -" + outputFileName + "-");
        console.log(cLogDebug, "The thePlateLengthUsed is -" + thePlateLengthUsed + "-");
        console.log(cLogDebug, "Sending " + (i + 1) + " of " + plateJobs.length + "-" + plateJobPath + " to " + tempBatchFolder + "/" + outputFileName);

        try{
            s.copy(plateJobPath,tempBatchFolder + "/" + outputFileName);
        }
        catch(err){
            console.log(cLogError, "Problem sending " + (i +1) + " of " + plateJobs.length + "-" + plateJobPath + " to " + tempBatchFolder + "/" + i + "_" + outputFileName);
            console.log(cLogError,"Problem with copy to batch folder: " + err);
            console.log(cLogError,"Source File: " + plateJobPath);
            console.log(cLogError,"Destiation : " + tempBatchFolder + "/" + i + "_" + outputFileName);

        }

        thisJob = s.createNewJob(plateJobPath);

        try{
            thisJob.sendToNull(plateJobPath);
            console.log(cLogDebug, "Sent " + plateJobPath + " to " + "NULL");
        }
        catch(err){
            console.log(cLogError, "Problem removing  "  + plateJobPath );
        }

    }//end copy loop

    //add the plate used pct to the private data
    thisJob.setPrivateData("thePlateLengthUsed",thePlateLengthUsed);

    //STEP 3 move the batch folder to the output
    thisJob.sendToSingle(tempBatchFolder);
    s.sleep(3);//make sure the jobs that have been batched are no longer in input before looping again

}

function getJobName(s, theJobPath){
    jobPath = String(theJobPath);
    var jobPathArray = jobPath.split("/");
    var jobPathArrayLength = jobPathArray.length;
    jobName = jobPathArray[jobPathArrayLength-1];//strip off the path
    jobName = jobName.substring(7);//strip off the uniuq prefix
    return jobName;
}


function getJobHeight(s,theJobName){

    var theJobNameArray = theJobName.split("_");
    var theJobHeight = theJobNameArray[1];

    console.log(cLogDebug, "--The job height is -" + theJobHeight + "-");
    console.log(cLogDebug, "--The theJobNameArray is -" + theJobNameArray + "-");

    return theJobHeight;
}

function stringify(obj) {
    var t = typeof (obj);
    if (t != "object" || obj === null) {

        // simple data type
        if (t == "string") obj = '"'+obj+'"';
        return String(obj);

    }
    else {

        // recurse array or object
        var n;
        var v;
        var json = [];
        var arr = (obj && obj.toString() !== '[object Object]');

        for (n in obj) {
            v = obj[n]; t = typeof(v);

            if (t == "string") v = '"'+v+'"';
            else if (t == "object" && v !== null) v = stringify(v);

            json.push((arr ? "" : '"' + n + '":') + String(v));
        }

        return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
    }
}

class Console {
    var _s;
    function Console(s) {
        _s  = s;
    }

    function log(level, message, extra) {
        _s.log(level, message + ' ' + (typeof extra !== 'undefined' ? stringify(extra) : ''));
    }

}


