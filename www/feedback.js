'use strict';

var exec = require('cordova/exec');

var feedback = {};

feedback.send = function(options, success, failure) {
    exec(success, failure, 'Feedback', 'send', [options]);
};

feedback.hasPermission = function(success, failure) {
    exec(success, failure, 'Feedback', 'has_permission', []);
};

module.exports = feedback;

cordova.addConstructor(function() {

	if (!window.plugins) {
		window.plugins = {};
	}

	if (!window.Cordova) {
		window.Cordova = cordova;
	}
	;

	window.plugins.feedback = feedback;
});
