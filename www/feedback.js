function Feedback() {
	this.resultCallback = null;
}

Feedback.prototype.send = function(options, success, failure) {
	alert(options);
  cordova.exec(success, failure, 'Feedback', 'send', [options]);
};

Feedback.prototype.hasPermission = function(success, failure) {
  cordova.exec(success, failure, 'Feedback', 'has_permission', []);
};

cordova.addConstructor(function() {

	if (!window.plugins) {
		window.plugins = {};
	}

	if (!window.Cordova) {
		window.Cordova = cordova;
	}
	;

	window.plugins.feedback = new Feedback();
});
