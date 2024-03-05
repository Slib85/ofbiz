/**
 * Created by Manu on 7/30/2015.
 */
if(!window.CoreLibrary) {
    alert('The envelopes-library.js must be included in order to use this library');
}

if(!window.ObjectUtilityLibrary) {
    window.ObjectUtilityLibrary = {};

    ObjectUtil = function() {
        alert(CoreLibrary.ABSTRACT_INSTANTIATION_ERROR_MESSAGE);
        throw new Error(CoreLibrary.ABSTRACT_INSTANTIATION_ERROR_MESSAGE);
    };

    ObjectUtil.isDefined = function(value) {
        return (typeof value != "undefined");
    };

    ObjectUtil.isNotDefined = function(value) {
        return !ObjectUtil.isDefined(value);
    };

    ObjectUtil.isNull = function(value) {
        return (!ObjectUtil.isDefined(value) || value === null);
    };

    ObjectUtil.isNotNull = function(value) {
        return !ObjectUtil.isNull(value);
    };

    ObjectUtil.isFunction = function(value) {
        return (typeof value == "function");
    };

    ObjectUtil.isArray = function(value) {
        return (ObjectUtil.isNotNull(value) && (typeof (value.push) == "function") && ObjectUtil.isDefined(value.length));
    };

    ObjectUtil.removeArrayElement = function(array, index) {
        var val = null;
        if(ObjectUtil.isNull(array) || index >= array.length) {
            return val;
        }

        return array.splice(index, 1);
    };

    ObjectUtil.insertArrayElement = function(array, index, value) {
        if(ObjectUtil.isNull(array)) {
            return;
        }

        array.splice(index, 0, value);
    };

    /*Mutex = function() {
        this.inUse = false;

        if (!Mutex.functionsDefined) {
            Mutex.functionsDefined = true;
        }
        Mutex.prototype.acquire = function(callback) {
            if (this.inUse) {
                var fn = function () {
                    $thisPointer.acquire.call($thisPointer, $callbackFunction);
                };

                fn = fn.wrapped();
                fn.thisPointer = this;
                fn.callbackFunction = callback;

                window.setTimeout(fn, 100);
            } else {
                this.inUse = true;
                callback();
            }
        };

        Mutex.prototype.release = function() {
            this.inUse = false;
        }
    };*/



}