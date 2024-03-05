/**
 * Created by Manu on 10/1/2015.
 */
/*if(!window.CoreLibrary) {
    alert('The envelopes-library.js must be included in order to use this library');
}*/

//TODO - import jquery
//TODO - import jquery ui
var buildSampleArray = function(rows, columns) {
    var data = new Array(rows);
    for(var i = 0; i < rows; i ++) {
        data[i] = new Array(j);
        for(var j = 0; j < columns; j ++) {
            data[i][j] = String.fromCharCode(65 + j) + (i+1);
        }
    }
    return data;
};
//eval(LibraryUtil.importRelative("envelopes-object-util.js"));
//eval(LibraryUtil.importRelative("envelopes-collection-util.js"));
var EnvelopesLogger = {
    Level: {
    ERROR: 'ERROR',
    WARN: 'WARN',
    INFO: 'INFO',
    DEBUG: 'DEBUG'},
    ifLog: function(logLevel, settingsLogLevel) {
        var logFlag = false;
        if(typeof logLevel === 'undefined') {
            logLevel = '';
        }
        switch(settingsLogLevel){
            case EnvelopesLogger.Level.ERROR: {
                if(logLevel == EnvelopesLogger.Level.ERROR) {
                    logFlag = true;
                }
                break;
            }
            case EnvelopesLogger.Level.WARN: {
                if(logLevel == EnvelopesLogger.Level.ERROR || logLevel == EnvelopesLogger.Level.WARN) {
                    logFlag = true;
                }
                break;
            }
            case EnvelopesLogger.Level.INFO: {
                if(logLevel == EnvelopesLogger.Level.ERROR || logLevel == EnvelopesLogger.Level.WARN || logLevel == EnvelopesLogger.Level.INFO) {
                    logFlag = true;
                }
                break;
            }
            case EnvelopesLogger.Level.DEBUG:
                if(logLevel == EnvelopesLogger.Level.ERROR || logLevel == EnvelopesLogger.Level.WARN || logLevel == EnvelopesLogger.Level.INFO || logLevel == EnvelopesLogger.Level.DEBUG) {
                    logFlag = true;
                }
                break;
        }
        return logFlag;
    }
};

$.widget("envelopes.BaseWidget", {
    options: {
        indenting: true,
        logLevel: EnvelopesLogger.Level.WARN
    },
    _indent:1,
    ERROR: EnvelopesLogger.Level.ERROR,
    WARN: EnvelopesLogger.Level.WARN,
    INFO: EnvelopesLogger.Level.INFO,
    DEBUG: EnvelopesLogger.Level.DEBUG,

    /**
     * Clone utility method for quick cloning of objects as value and not reference
     * WARNING: THIS WILL NOT WORK WHEN YOU HAVE DATE VALUES
     * @param {Object} obj - object to clone
     * @return {Object} - New object
     */
    _clone: function(obj) {
        return JSON.parse(JSON.stringify(obj));
    },

    _getStringArray: function(parameter) {
        var array = [];
        if(typeof parameter !== 'undefined') {
            if(Object.prototype.toString.call(parameter) === '[object Array]') {
                array = parameter;
            } else if(Object.prototype.toString.call(parameter) === '[object String]') {
                array[0] = parameter;
            }
        }
        return array;
    },

    _log: function(level, message, indent, objName) {
        if(this.options.debug) {

            if(!EnvelopesLogger.ifLog(level, this.options.logLevel)) {
                return;
            }
            if (typeof indent !== 'undefined' && indent < 0) {
                this._indent += indent;
            }

            if (typeof console == 'object') {
                if(typeof message === 'object') {
                    this._logObject(message, objName);
                } else {
                    console.log(/*this._getWidgetName() + '-' + new Date().toLocaleString() + ':' + */this._getIndent() + message);
                }

            }

            if (typeof indent !== 'undefined' && indent > 0) {
                this._indent += indent;
            }
            if (this._indent == 0) {
                if (typeof console == 'object') {
                    console.log('___________________________________________________________________________________________________________________________________');
                    console.log('');
                }
            }
        }
    },

    _logObject: function(object, objName) {
        console.log(this._getIndentBaseOffset() + this._getIndent() + (typeof objName !== 'undefined' ? objName : 'object') + ' {');
        for(var prop in object) {
            console.log(this._getIndentBaseOffset() + '\t' + this._getIndent() + prop + ' :: ' + object[prop]);
        }
        console.log(this._getIndentBaseOffset() + this._getIndent() + '}');
    },

    _getIndent: function() {
        if(this.options.indenting != true) {
            return '';
        }
        var indent = '';
        for(var i = 0; i < this._indent; i++) {
            indent += '\t';
        }
        return indent;
    },

    _getWidgetName: function() {
        return 'envelopes.BaseWidget';
    },

    _getIndentBaseOffset: function() {
        return '\t\t\t\t\t';
    },

    _inverseArray: function(data, defaultDimension) {
        data = this._fixArrayDimension(data, defaultDimension);
        var invertedArray = [[]];
        if(data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                for (var j = 0; j < data[i].length; j++) {
                    if (i == 0) {
                        invertedArray[j] = [];
                    }
                    invertedArray[j][i] = data[i][j];
                }
            }
        }
        return invertedArray;
    },

    _fixArrayDimension: function(data, defaultDimension) {
        var arrayDimension = this._get2DArrayDimension(data, defaultDimension);
        var rows = arrayDimension[0];
        var columns = arrayDimension[1];
        if(Object.prototype.toString.call(data) === '[object Array]') {
            for(var i = 0; i < rows; i ++) {
                if(Object.prototype.toString.call(data[i]) === '[object Array]') {
                    for(var j = data[i].length; j < columns; j ++) {
                        data[i][j] = undefined;
                    }
                } else {
                    data[i] = new Array(columns);
                }
            }
            return data;
        } else {
            data = new Array(rows);
            for(var i = 0; i < rows; i ++) {
                data[i] = new Array(columns);
            }
            return data;
        }
    },

    _get2DArrayDimension: function(data, defaultDimension) {
        var rows = 0;
        var columns = 0;
        if(Object.prototype.toString.call(data) === '[object Array]') {
            rows = data.length;
            for(var i = 0; i < rows; i ++) {
                if(Object.prototype.toString.call(data[i]) === '[object Array]') {
                    columns = data[i].length > columns ? data[i].length : columns;
                }
            }

            if(rows == 0) {
                rows = defaultDimension[0];
            }

            if(columns == 0) {
                columns = defaultDimension[1];
            }

            return [rows, columns];
        }
        return defaultDimension;
    },
    _removeEmptyRows: function(data, replaceNulls, removeCellId) {
        var filteredData = [];
        var filteredIdx = 0;
        for(var i = 0; i < data.length; i ++) {
            var nonEmptyRow = false;
            for(var j = 0; j < data[i].length; j ++) {
                if(typeof data[i][j] !== 'undefined' && data[i][j] != null && data[i][j].trim() != '') {
                    nonEmptyRow = true;
                    break;
                }
            }
            if(nonEmptyRow) {
                if (typeof replaceNulls !== 'undefined' && replaceNulls) {
                    for (var j = 0; j < data[i].length; j++) {
                        if (typeof data[i][j] === 'undefined' || data[i][j] == null) {
                            data[i][j] = '';
                        }
                    }
                }
                if(typeof removeCellId !== 'undefined' && removeCellId == true) {
                    data[i][0] = this._getIdCellValue(data[i][0]);
                }
                filteredData[filteredIdx++] = data[i];

            }
        }
        return filteredData;
    },

    _shiftTab: function(element, callback1, callback2, args) {
        $(element).keydown(function(e) {
            if(!args) args=[]; // IE barks when args is null
            if(e.keyCode == 9 && e.shiftKey) {
                callback1.apply(this, args);
                return false;
            } else if(e.keyCode == 9) {
                callback2.apply(this, args);
                return false;
            }
        });
    },

    _ctrl: function(element, key, callback, args) {
        $(element).keydown(function(e) {
            if(!args) args=[]; // IE barks when args is null
            if(e.keyCode == key.charCodeAt(0) && e.ctrlKey) {
                callback.apply(this, args);
                return false;
            }
        });
    },

    _compareArrays: function(array1, array2) {

        if(array1.length != array2.length) {
            return 1;
        }

        for(var i = 0; i < array1.length; i ++) {
            var val1 = array1[i];
            var val2 = array2[i];
            if(typeof val1 === 'undefined' || val1 == null) {
                val1 = '';
            }
            if(typeof val2 === 'undefined' || val2 == null) {
                val2 = '';
            }
            if(val1.trim() != val2.trim()) {
                return 1;
            }
        }

        return 0;
    },

    _firstIndexInArray: function(array, value) {
        for(var i = 0; i < array.length; i ++) {
            if(array[i] == value) {
                return i;
            }
        }
        return -1;
    },

    _removeNullsFrom1DArray:function(arr) {
        for(var i = 0; i < arr.length; i ++) {
            if(arr[i] == null || typeof arr[i] === 'undefined') {
                arr[i] = '';
            }
        }
        return arr;
    },

    _isArrayEmpty: function(arr) {
        for(var i = 0; i < arr.length; i ++) {
            if(arr[i] != null && typeof arr[i] !== 'undefined' && arr[i].trim() != '') {
               return false;
            }
        }
        return true;
    },

    _getRowDataWithoutIdValue: function(rowData) {
        var result =[];
        for(var i = 0; i < rowData.length; i ++) {
            if(i == 0) {
                result[i] = this._getIdCellValue(rowData[0]);
            } else {
                result[i] = rowData[i];
            }
        }
        return result;
    },

    _setIdCellValue: function(idCellValue, id) {
        if(idCellValue != null && typeof idCellValue !== 'undefined' && typeof id !== 'undefined' && id != '' && idCellValue.indexOf('::rowid::') == -1) {
            return id + '::rowid::' + idCellValue;
        }
        return idCellValue;
    },

    _getIdCellValue: function(idCellValue) {
        if(idCellValue != null && typeof idCellValue !== 'undefined' && idCellValue.indexOf('::rowid::') != -1) {
            return idCellValue.substring(idCellValue.indexOf('::rowid::') + '::rowid::'.length);
        }
        return idCellValue;
    },

    _getRowIdValue: function(idCellValue) {
        if(idCellValue != null && typeof idCellValue !== 'undefined' && idCellValue.indexOf('::rowid::') != -1) {
            return idCellValue.substring(0, idCellValue.indexOf('::rowid::'));
        }
        return '';
    },

    _mergeIdCellValues: function(oldValue, newValue) {
        var rowIdValue = this._getRowIdValue(oldValue);
        newValue = this._setIdCellValue(newValue, rowIdValue);
        return newValue;
    }

    /*_copyTextToClipboard: function(text) {
        var textArea = document.createElement("textarea");

        //
        // *** This styling is an extra step which is likely not required. ***
        //
        // Why is it here? To ensure:
        // 1. the element is able to have focus and selection.
        // 2. if element was to flash render it has minimal visual impact.
        // 3. less flakyness with selection and copying which **might** occur if
        //    the textarea element is not visible.
        //
        // The likelihood is the element won't even render, not even a flash,
        // so some of these are just precautions. However in IE the element
        // is visible whilst the popup box asking the user for permission for
        // the web page to copy to the clipboard.
        //

        // Place in top-left corner of screen regardless of scroll position.
        textArea.style.position = 'fixed';
        textArea.style.top = 0;
        textArea.style.left = 0;

        // Ensure it has a small width and height. Setting to 1px / 1em
        // doesn't work as this gives a negative w/h on some browsers.
        textArea.style.width = '2em';
        textArea.style.height = '2em';

        // We don't need padding, reducing the size if it does flash render.
        textArea.style.padding = 0;

        // Clean up any borders.
        textArea.style.border = 'none';
        textArea.style.outline = 'none';
        textArea.style.boxShadow = 'none';

        // Avoid flash of white box if rendered for any reason.
        textArea.style.background = 'transparent';


        textArea.value = text;

        document.body.appendChild(textArea);

        textArea.select();

        try {
            var successful = document.execCommand('copy');
            var msg = successful ? 'successful' : 'unsuccessful';
            console.log('Copying text command was ' + msg);
        } catch (err) {
            console.log('Oops, unable to copy');
        }

        document.body.removeChild(textArea);
    }*/


});
