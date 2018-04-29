describe('register page', function() {

	var buttonIndex = {
		randomUser: 1,
		registerUser: 2,
		signinPage: 3
	};

	var infoIndex = {
		defaultPassword: 0,
		successRegister: 1,
		duplicateEmail: 2
	}

	it('should fill with random data', function() {
		browser.get('/register');

		var inputEmail = $('input[name="email"]');
		var inputFullName = $('input[name="fullName"]');
		var passwordName = $('input[name="password"]');
		var rePasswordName = $('input[name="rePassword"]');

		// empty value
		inputEmail.getAttribute('value').then(emptyValue);
		inputFullName.getAttribute('value').then(emptyValue);
		passwordName.getAttribute('value').then(emptyValue);
		rePasswordName.getAttribute('value').then(emptyValue);

		clickRandomUser();

		// not empty value
		inputEmail.getAttribute('value').then(notEmptyValue);
		inputFullName.getAttribute('value').then(notEmptyValue);
		passwordName.getAttribute('value').then(notEmptyValue);
		rePasswordName.getAttribute('value').then(notEmptyValue);

		function emptyValue(value) {
			expect(value === '').toEqual(true);
		}
		function notEmptyValue(value) {
			expect(value !== '').toEqual(true);
		}
	});

	it('should register user hide/show necessary info', function() {
		browser.get('/register');

		var info = $$('.random-user-info');
		var infoDP = info.get(infoIndex.defaultPassword);
		var infoSR = info.get(infoIndex.successRegister);
		var infoDE = info.get(infoIndex.duplicateEmail);

		// after poage load, all info are hidden
		expect(infoDP.isDisplayed()).toBe(false);
		expect(infoSR.isDisplayed()).toBe(false);
		expect(infoDE.isDisplayed()).toBe(false);

		clickRandomUser();

		// after click random user, `default password info` visible
		expect(infoDP.isDisplayed()).toBe(true);
		expect(infoSR.isDisplayed()).toBe(false);
		expect(infoDE.isDisplayed()).toBe(false);

		clickRegisterUser();

		// after click register, `default password info` hidden
		expect(infoDP.isDisplayed()).toBe(false);
		$('app-register').$$('button').get(buttonIndex.randomUser).isDisplayed().then(function(isShow) {
			if (isShow) {
				//  failed register
				expect(infoSR.isDisplayed()).toBe(false);
				expect(infoDE.isDisplayed()).toBe(true);
			} else {
				//  success register
				expect(infoSR.isDisplayed()).toBe(true);
				expect(infoDE.isDisplayed()).toBe(false);
			}
		})

	});

	function clickRandomUser() {
		$('app-register').$$('button').get(buttonIndex.randomUser).click();
	}

	function clickRegisterUser() {
		$('app-register').$$('button').get(buttonIndex.registerUser).click();	
	}

});