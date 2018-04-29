exports.config = {
	capabilities: {
		browserName: 'chrome',
	},
	allScriptsTimeout: 120000, 		// 120 seconds
	getPageTimeout: 10000, 			// 10 seconds
	seleniumAddress: 'http://localhost:4444/wd/hub',
	baseUrl: 'http://localhost:8090',
	specs: [
		'page.js',
		'page-top100.js',
		'page-latest.js'
	]
};