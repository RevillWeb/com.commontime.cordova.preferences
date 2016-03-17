# Preferences plugin

### Calls

**_plugins.provisioning.getAllPreferences( success, fail );_**
```
plugins.preferences.getAllPreferences(function(result) { 
	console.log(JSON.stringify(result));
}, function(error) { 
	console.error(result); 
});

{"test2":"false","foo":"bar","loglevel":"DEBUG"}
```