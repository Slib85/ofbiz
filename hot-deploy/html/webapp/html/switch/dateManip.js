var defaultDueLeadDays = 1;
var HOLIDAYS = ['2015-12-25','2016-01-01','2016-05-30','2016-07-04','2016-09-05','2016-11-24'];
var WORKING_WEEKENDS = [];

console.log(getLeadDueDays(new Date()));
/**
 * Method used to find the number of days in which the given dueDate is due
 * @param _dueDate - The due date of a single job
 * @param currentExecutionTime - The current execution time
 * @returns {number} - The number of days in which this dueDate is due
 */
function jobDueIn(_dueDate, currentExecutionTime) {
    var dueIn = 0;
    _dueDate = convertStringDateToDate(_dueDate, '-');
    var dueDate = new Date(_dueDate.getFullYear(), _dueDate.getMonth(), _dueDate.getDate());

    var today = new Date(currentExecutionTime.getFullYear(), currentExecutionTime.getMonth(), currentExecutionTime.getDate());

    dueIn = (dueDate.getTime() - today.getTime());
    if(dueIn < 0) {
        dueIn = 0;
    } else {
        dueIn = dueIn / (24 * 60 * 60 * 1000);
    }
    console.log('dueDate:' + convertDateToString(dueDate));
    console.log('today:' + convertDateToString(today));
    console.log('dueIn:' + dueIn);

    return dueIn;
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
 * Method used to convert dateTime to a date
 * @param dateTime
 * @returns {*}
 */
function convertDateTimeToDateOnly(dateTime) {
    return new Date(dateTime.getFullYear(), dateTime.getMonth(), dateTime.getDate());
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
    return date.getFullYear() + '-' + (date.getMonth() < 10 ? '0' : '') + date.getMonth() + '-' + (date.getDate() < 10 ? '0' : '') + date.getDate() + ' ' + (date.getHours() < 10 ? '0' : '') + date.getHours() + '_' + (date.getMinutes() < 10 ? '0' : '') + date.getMinutes() + '_' + (date.getSeconds() < 10 ? '0' : '') + date.getSeconds();
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
