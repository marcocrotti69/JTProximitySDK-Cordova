module.exports = function(context) {
    console.log("Kariboo android hook: replacing KARIBOO_ID and KARIBOO_SECRET in source files...");

    var fs = require('fs');
    var path = require('path');

    var sourceFile = null;
    var KARIBOO_ID = null;
    var KARIBOO_SECRET = null;

    var walkSync = function (currentDirPath) {
        fs.readdirSync(currentDirPath).forEach(function (name) {
            var filePath = path.join(currentDirPath, name);
            var stat = fs.statSync(filePath);
            if (stat.isFile()) {
                if (name == 'KaribooPlugin.java') {
                    sourceFile = filePath;
                } else if (name == 'strings.xml') {
                    var strings = fs.readFileSync(filePath).toString();
                    var match = strings.match(/<string name="kariboo_id">([^<]+?)<\/string>/i);
                    if (match) {
                        KARIBOO_ID = match[1];
                    }
                    match = strings.match(/<string name="kariboo_secret">([^<]+?)<\/string>/i);
                    if (match) {
                        KARIBOO_SECRET = match[1];
                    }
                }
            } else if (stat.isDirectory()) {
                walkSync(filePath);
            }
        });
    }
    var basePath = path.join(context.opts.projectRoot, 'platforms', 'android');
    walkSync(basePath);
    if (sourceFile && KARIBOO_ID && KARIBOO_SECRET) {
        console.log("...found: " + sourceFile);
        fs.readFile(sourceFile, 'utf8', function (err,data) {
            if (err) {
                return console.log("Unable to read source file", err);
            }
            var result = data.replace(/@KARIBOO_ID@/g, KARIBOO_ID)
                .replace(/@KARIBOO_SECRET@/g, KARIBOO_SECRET);
            fs.writeFile(sourceFile, result, 'utf8', function (err) {
                if (err) {
                    return console.log("Unable to write source file", err);
                }
            });
        });
    }
}