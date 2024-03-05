/**
 * Create a new session
 * @constructor
 */
function EnvSession() {
	this.id = null;
}
EnvSession.prototype = {
	getId: function() {
		return this.id;
	},
	createId: function() {
		var sessionId = '';
		var cList = document.cookie.split(';');
		for(var i = 0; i < cList.length; i++) {
			var c = cList[i];
			while(c.charAt(0) == ' ') {
				c = c.substring(1);
			}
			if(c.indexOf('__SS_Data') != -1) {
				sessionId = c.substring(c.indexOf('=')+1);
			}
		}

		if(sessionId == '') {
			this.id = this.uuid() + '-' + this.uuid();
			document.cookie = '__SS_Data=' + this.id + ';path=/';
		} else {
			this.id = sessionId;
		}
	},
	uuid: function(a) {
		return a?(a^Math.random()*16>>a/4).toString(16):([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g,this.uuid)
	}
};

var envSession = new EnvSession(); envSession.createId(); envSession = envSession.getId();