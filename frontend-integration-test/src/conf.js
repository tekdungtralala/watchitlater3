exports.config = {
	capabilities: {
		browserName: 'chrome',
		chromeOptions: {
			args: [
				"--headless",
				"--disable-gpu"
			],
		},
	},
	specs: ['todo-spec.js']
};
