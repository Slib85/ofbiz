/**
 * Created by Manu on 7/30/2015.
 */

var coreFileCachingEnabled = false;

var CoreResources = {};

var root = window;

CoreResources.keepScripts = true;

CoreResources.fileCache = {};

CoreResources.httpRequestsPool = [];

//CoreResources.nonBlockingHttpRequests = 0;

// TODO - Replace the XMLHttpRequest using nodeJS
CoreResources.loadFile = function(fullPath) {
    if(coreFileCachingEnabled) {
        var file = CoreResources.fileCache[fullPath];
        if(file) {
            return file;
        }
    }

    var httpRequest = CoreResources.httpRequestsPool.shift();
    if(!httpRequest) {
        httpRequest = window.ActiveXObject ? new ActiveXObject('Microsoft.XMLHTTP') : new XMLHttpRequest();
    }

    var response = '';
    httpRequest.open('POST', fullPath, false);
    httpRequest.send(null);
    if(httpRequest.status == 0 || httpRequest.status == 200 || httpRequest.status == 304) {
//        response = 'CoreResources.nonBlockingHttpRequests++;' + httpRequest.responseText;
        response = httpRequest.responseText;
    } else {
        CoreResources.httpRequestsPool.push(httpRequest);
        throw new Error('Importing of file: ' + fullPath + ' returned an invalid status ' + httpRequest.status);
    }
    CoreResources.httpRequestsPool.push(httpRequest);

    if(coreFileCachingEnabled) {
        CoreResources.fileCache[fullPath] = response;
    }

    return response;

};

//Initialize the Library only once
if(!window.CoreLibrary) {
    var CoreLibrary = {};

    /**
     * A message that should be shown when someone tries to instantiate a logically abstract class. The methods in the abstract class should be called in a logically 'static' fashion.
     * @private
     */
    CoreLibrary.ABSTRACT_INSTANTIATION_ERROR_MESSAGE = "This is logically a static class and cannot be instantiated.";

    CoreLibrary.importedFilePaths = {};

    CoreLibrary.importedFiles = {};

    LibraryUtil = function() {
        alert(CoreLibrary.ABSTRACT_INSTANTIATION_ERROR_MESSAGE);
        throw new Error(CoreLibrary.ABSTRACT_INSTANTIATION_ERROR_MESSAGE);
    };

    LibraryUtil.importFile = function(fullPath) {
        if(CoreLibrary.importedFilePaths[fullPath]) {
            return "";
        }

        var result = CoreResources.loadFile(fullPath);
        CoreLibrary.importedFilePaths[fullPath] = true;
        return result;
    };

    LibraryUtil.importRelative = function(fileToImport, knownFile) {
        if(CoreLibrary.importedFiles[fileToImport]) {
            return '';
        }

        knownFile = knownFile ? knownFile : 'envelopes-library.js';

        var result = '';
        var fullPath = '';
        var scripts = document.getElementsByTagName('script');
        for(var i = 0; i < scripts.length; i ++) {
            var src = scripts[i].src;
            if (src.match(fileToImport)) {
                CoreLibrary.importedFiles[fileToImport] = src;
                return '';
            } else if (knownFile) {
                fullPath = src.replace(knownFile, fileToImport);
                break;
            } else if (CoreLibrary.importedFiles[knownFile]) {
                fullPath = CoreLibrary.importedFiles[knownFile].replace(new RegExp("(/.[^/]*$)"), "/") + fileToImport;
                break;
            }
        }
        if(fullPath != '') {
            result = LibraryUtil.importFile(fullPath);
        } else {
            throw new Error('Unable to find the full path to import the file with the name \'' + fileToImport + '\' relative to the file with the name \'' + knownFile + '\'');
        }

        CoreLibrary.importedFiles[fileToImport] = fullPath;

        return result;
    };

    /**
     * Wraps a function up into a new function call that is safe to pass with embedded references.  Useful for event handlers and timer functions.  A variable with a dollar sign preceding their name (i.e. <code>$myArg</code>)
     * will be converted to <code>arguments.callee.myArg</code>.  A new function object will be returned which will represent the function that is being wrapped.  All dollar-variables can be appended to the wrapped function
     * as static parameters.  Functions to be wrapped cannot contain single-line comments (i.e. <code>// This is an invalid comment</code>) however, block-style comments are acceptable.
     * <pre>
     * function testFunc(arg1, arg2) {
	 *    if ($inputParam == arg1) {
	 *       return arg2;
	 *    } else {
	 *       return Math.pow($inputParam, $exp);
	 *    }
	 * }
     * </pre>
     * The function above would be re-written such that <code>$inputParam</code> becomes <code>arguments.callee.inputParam</code> and <code>$exp</code> becomes <code>arguments.callee.exp</code>.  After which the following
     * would be the proper way to append the parameters to the function call:
     * <pre>
     * var fn = testFunc.wrapped();
     * fn.inputParam = 13;
     * fn.exp = 5;
     * </pre>
     * @type Function
     */
    /*Function.prototype.wrapped = function()
    {
        var varRE = /(\$)(\w+)/g;                                   // Translate variables
        var argRE = /(function\s*\w*\s*\()((\w*\s*,?\s*)*)(\)\s*)/; // Extract arguments
        var fsRE = /function\s*\w*\s*\((\w*\s*,?\s*)*\)\s*\{/;      // Remove function header
        var nlRE = /\s{1,}/g;                                       // Compress whitespace (removes newlines)
        var mlRE = new RegExp("\\/\\*[^*]*\\*+([^\\/][^*]*\\*+)*\\/","g");         // Remove block comments
        var funcStr = this.toString();

        // Get arguments
        var args = funcStr.match(argRE)[2].replace(/\s/g, "");

        // Clean up function
        funcStr = funcStr.replace(fsRE, "");
        funcStr = funcStr.replace(varRE, "arguments.callee.$2");
        funcStr = funcStr.replace(nlRE, " ");
        funcStr = funcStr.replace(mlRE, "");

        // Remove the last brace
        var lastBrace = funcStr.lastIndexOf("}");
        funcStr = funcStr.substring(0, lastBrace);

        return new Function(args, funcStr);
    };*/


    /**
     * Helper method to support inheritance, to support class hierarchy
     * @param _class
     * @private
     */
    Function.prototype._extends = function(_class) {
        this.prototype = new _class();
        this.base = _class.prototype;
    };


    BaseObject = function() {
        if(BaseObject.functionsDefined) {
            return;
        }

        BaseObject.functionsDefined = true;

        BaseObject.prototype.isBaseObject = function() {
            return true;
        };

        BaseObject.prototype.getClassName = function() {
            return "BaseObject";
        };

        BaseObject.prototype.getClass = function() {
            return window[this.getClassName()];
        };

        BaseObject.prototype.newInstance = function() {
            return new (this.getClass());
        };
    };

    new BaseObject();
}
