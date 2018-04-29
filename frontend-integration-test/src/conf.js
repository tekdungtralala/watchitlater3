exports.config = {
	capabilities: {
		browserName: 'chrome',
	},
	seleniumAddress: 'http://localhost:4444/wd/hub',
	baseUrl: 'http://localhost:8090',
	specs: ['page.js']
};