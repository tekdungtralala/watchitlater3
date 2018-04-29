describe('top100 page', function() {

	it('should show 100 movies order desc by rating', function() {
		browser.get('/top100');

		$('app-top100').$$('td.row-movie-rating .movie-info').getAttribute('innerHTML').then(function(items) {
			// must has 100 movies
			expect(items.length).toEqual(100);

			var lastRating = Number.MAX_VALUE;
			for (var i = 0; i < 100; i++) {
				var movieRating = parseFloat(items[i].trim());

				// order desc by rating
				expect(lastRating >= movieRating).toEqual(true);
				lastRating = movieRating;
			}
		});
	});

	it('should show 100 unique movies', function() {
		browser.get('/top100');
		$('app-top100').$$('td.row-movie-name .movie-info').getAttribute('innerHTML').then(function(items) {
			// must has 100 movies
			expect(items.length).toEqual(100);

			var lastTitle = '';
			for (var i = 0; i < 100; i++) {
				var movieTitle = items[i].trim();

				// movie title must be different
				expect(lastTitle === movieTitle).toEqual(false);
				lastTitle = movieTitle;
			}
		});
	});
});