module.exports = {
	getAllPreferences: function(success, fail) {
		try {
			cordova.exec(success, fail, "Preferences", "getAllPreferences", []);
		} catch (e) {}
	},
	enableWebDebugging: function(enable) {
		try {
			cordova.exec(null, null, "Preferences", "enableWebDebugging", [enable]);
		} catch (e) {}
	}
};