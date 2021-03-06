exports.config = {
	chromeDriver: 'node_modules/protractor/node_modules/webdriver-manager/selenium/chromedriver_2.38',
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
		'page-latest.js',
		'page-register.js',
		'page-login.js',
		'page-dashboard.js'
	]
};