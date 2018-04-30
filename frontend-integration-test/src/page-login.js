describe('login page', function() {

	it('should submit form', function() {
		browser.get('/login');

		$('app-login').$('form').getAttribute('class').then(function(classes) {
			expect(classes.split(' ').indexOf('form-submited') === -1).toBe(true);
		});

		clickRegisterUser();

		$('app-login').$('form').getAttribute('class').then(function(classes) {
			expect(classes.split(' ').indexOf('form-submited') >= 0).toBe(true);
		});
	});

	it('should redirect to dashboard after success signin', function() {
		browser.get('/login');

		var email = element(by.name('email'));
		var password = element(by.name('password'));
		email.clear().sendKeys(process.env.WATCHITLATER3_FE_TEST_EMAIL);
		password.clear().sendKeys(process.env.WATCHITLATER3_FE_TEST_PASSWORD);

		clickRegisterUser();

		browser.getCurrentUrl().then(function(url) {
			expect(url.indexOf('/dashboard') >= 0).toBe(true);
		});
	});

	function clickRegisterUser() {
		$('app-login').$$('button').get(0).click();	
	}
});