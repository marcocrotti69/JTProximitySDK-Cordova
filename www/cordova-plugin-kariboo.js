var exec = require('cordova/exec');

exports.init = function(arg0, arg1, success, error) {
    exec(success, error, "KaribooPlugin", "init", [arg0, arg1]);
};

exports.setDebug = function(arg0, success, error) {
    exec(success, error, "KaribooPlugin", "setDebug", [arg0]);
};

exports.getInstallationId = function(success, error) {
    exec(success, error, "KaribooPlugin", "getInstallationId", []);
};
