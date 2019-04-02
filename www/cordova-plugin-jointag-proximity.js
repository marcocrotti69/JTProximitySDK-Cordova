var exec = require('cordova/exec');

exports.getInstallationId = function(success, error) {
    exec(success, error, "KaribooPlugin", "getInstallationId", []);
};

exports.init = function(success, error) {
    exec(success, error, "KaribooPlugin", "init", []);
};
