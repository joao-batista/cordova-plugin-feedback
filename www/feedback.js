var feedback = {};

feedback.send = function(options, success, failure) {
    cordova.exec(success, failure, "Feedback", "send", [options]);
};

module.exports = feedback;