/**
 * Texel Widget
 * Developed by Envelopes.com Dev Team Six
 * Dependency jQuery
 */

/**
 * Queue system for AJAX Calls
 * This will monitor an array of ajax calls and queue them up in sequence
 * @param {boolean} honorLast - Whether the queue should only care about the last call
 * @param {int} maxAttempts - How many attempts of the call should be made incase of error
 * @param {boolean} async - Should the queue execute commands in sync or async
 */
function AjaxQueue(honorLast, maxAttempts, async) {
	this.honorLast = honorLast;
	this.maxAttempts = maxAttempts;
	this.async = async;
	this.queue = [];

	var arrCounter = 0;
	var successIndex = -1;
	var failIndex = -1;

	this.getArrayPosition = function() {
		return arrCounter;
	};
	this.setNextArrayPosition = function() {
		arrCounter++;
	};
	this.getSuccessIndex = function() {
		return successIndex;
	};
	this.setSuccessIndex = function(val) {
		successIndex = val;
	};
	this.getFailIndex = function() {
		return failIndex;
	};
	this.setFailIndex = function(val) {
		failIndex = val;
	};
}

AjaxQueue.prototype = {
	/**
	 * Add an AjaxCall object to the array
	 * @param {Object} options - The ajax options to be used
	 * @param {Function} done - Callback to fire when ajax is successful
	 * @param {Function} fail - Callback to fire when ajax fails
	 * @param {Function} always - Callback to fire when ajax is finished
	 */
	push: function(options, done, fail, always) {
		options.async = this.async;
		var ajaxCall = new AjaxCall();
		ajaxCall.index = this.getArrayPosition();
		ajaxCall.start(options, done, fail, always, this);

		this.queue[this.getArrayPosition()] = ajaxCall;
		this.setNextArrayPosition();
	},
	/**
	 * Abort an ajax call within the AjaxCall object
	 * If no index is passed, it will abort all ajaxcalls prior to the last one that have not finished
	 * @param {int} index - Specific index to abort
	 */
	abort: function(index) {
		if(typeof index === 'undefined' || index == null) {
			for(var i = 0; i < this.getSuccessIndex(); i++) {
				var ajaxCall = this.queue[i];
				if(ajaxCall.ajax.state == 0) {
					ajaxCall.ajax.abort();
				}
			}
		} else {
			var ajaxCall = this.queue[index];
			if(ajaxCall.state == 0) {
				ajaxCall.ajax.abort();
			}
		}
	},
	/**
	 * If honorLast is set in the queue, then we should abort all prior ajax requests
	 */
	abortCheck: function() {
		if(this.honorLast) {
			this.abort(null);
		}
	}
};

/**
 * Object to hold the ajax call
 */
function AjaxCall() {
	this.index = 0;
	this.queue = 0;
	/**
	 * Different ajax states
	 * -1 = Dormant
	 * 0  = Executed
	 * 1  = Successful
	 * 2  = Failed
	 */
	this.state = -1;
	this.ajax = null;
	this.attempts = 1;
}

AjaxCall.prototype = {
	/**
	 * Create the ajax call
	 * @param {Object} options - The ajax options to be used
	 * @param {Function} done - Callback to fire when ajax is successful
	 * @param {Function} fail - Callback to fire when ajax fails
	 * @param {Function} always - Callback to fire when ajax is finished
	 * @param {Object} queue - Queue object to update once ajax is done
	 */
	start: function(options, done, fail, always, queue) {
		var self = this;
		self.state = 0;
		self.ajax = $.ajax(options)
			.done(function(data, textStatus, jqXHR) {
				self.state = 1;

				self.done(data, textStatus, jqXHR, done, queue); //on success set the last success index on the queue object
			}).fail(function(jqXHR) {
				self.state = 2;

				self.retry(jqXHR, options, done, fail, always, queue);
			}).always(function(data, textStatus, jqXHR) {
				//fire callback
				if(typeof always === 'function') { always(data, textStatus, jqXHR) }
			}); //jxXHR object
	},
	/**
	 * Retry the ajax call if it fails
	 */
	retry: function(jqXHR, options, done, fail, always, queue) {
		if(queue.maxAttempts > 1 && this.attempts < queue.maxAttempts && (!queue.honorLast || (queue.honorLast && queue.getSuccessIndex() < this.index))) {
			this.attempts++;
			this.start(options, done, fail, always, queue);
		} else {
			this.fail(queue); //on fail set the last fail index on the queue object

			//fire callback
			if(typeof fail === 'function') { fail(jqXHR) }
		}
	},
	/**
	 * Set the successIndex on the AjaxQueue object so we know which ajax call was completed successfully
	 */
	done: function(data, textStatus, jqXHR, done, queue) {
		/**
		 * Only fire below statements if we want all ajax responses or
		 * only if this ajax call is newer then the last successful response
		 */
		if(!queue.honorLast || (queue.honorLast && queue.getSuccessIndex() < this.index)) {
			queue.setSuccessIndex(this.index);
			queue.abortCheck();

			//fire callback
			if (typeof done === 'function') {
				done(data, textStatus, jqXHR);
			}
		}
	},
	/**
	 * Set the failIndex on the AjaxQueue object so we know which ajax call failed last
	 */
	fail: function(queue) {
		queue.setFailIndex(this.index);
	}
};