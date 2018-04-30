describe('dashboard page', function() {

	it('should show user information', function() {
		browser.get('/dashboard');

		expect(element(by.name('fullName')).isPresent()).toBe(true);
		expect(element(by.name('email')).isPresent()).toBe(true);
		expect(element(by.name('initial')).isPresent()).toBe(true);
	});

	it('should show favorite movies order by rating desc', function() {
		browser.get('/dashboard');

		$('app-dashboard').$$('td.row-movie-rating .movie-info').getAttribute('innerHTML').then(function(items) {
			var lastRating = Number.MAX_VALUE;
			for (var i = 0; i < items.length; i++) {
				var movieRating = parseFloat(items[i].trim());

				// order desc by rating
				expect(lastRating >= movieRating).toEqual(true);
				lastRating = movieRating;
			}
		});
	});

	it('should change initial after updated', function() {
		browser.get('/dashboard');

		var initial = element(by.name('initial'));
		var newInitialVal = randomInitial();

		element(by.css('.edit-btn')).click();
		initial.clear().sendKeys(newInitialVal);
		element(by.css('.save-btn')).click();

		browser.get('/dashboard');
		initial.getAttribute('value').then(function(initial) {
			expect(initial).toEqual(newInitialVal);
		});

		function randomInitial() {
			// https://stackoverflow.com/questions/105034/create-guid-uuid-in-javascript/2117523#2117523
			var d = new Date().getTime();
			if (typeof performance !== 'undefined' && typeof performance.now === 'function'){
				d += performance.now(); // use high-precision timer if available
			}
			return 'xxxxxxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
				var r = (d + Math.random() * 16) % 16 | 0;
				d = Math.floor(d / 16);
				return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
			});
		}
	});

	it('should cant access dahsboard page after signout', function() {
		browser.get('/dashboard');

		$('app-dashboard').$$('button').get(0).click();

		urlIsntDashboard();

		browser.get('/dashboard');
		urlIsntDashboard();

		function urlIsntDashboard() {
			browser.getCurrentUrl().then(function(url) {
				expect(url.indexOf('/dashboard') === -1).toBe(true);
			});
		}
	});
});