describe('latest page', function() {

	it('should show some movies order desc by rating', function() {
		browser.get('/latest');

		var EC = protractor.ExpectedConditions
		browser.wait(EC.invisibilityOf($('.spinner-area')));
		$('app-latest').$$('td.row-movie-rating .movie-info').getAttribute('innerHTML').then(function(items) {

			// show some movies
			expect(items.length > 0).toEqual(true);

			var lastRating = Number.MAX_VALUE;
			for (var i = 0; i < items.length; i++) {
				var movieRating = parseFloat(items[i].trim());

				// order desc by rating
				expect(lastRating >= movieRating).toEqual(true);
				lastRating = movieRating;
			}
		});
	});
});