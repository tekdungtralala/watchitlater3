describe('access home page', function() {
	it('should get correct title', function() {
		browser.get('/');
		browser.getTitle().then(function(webpagetitle){
			expect(webpagetitle).toEqual('Watch it later');
		});
	});

	it('should show random 9 movies', function() {
		browser.get('/');

		$('#portfolio').$$('.movie-area .project-name').getAttribute('innerHTML').then(function(items1) {
			// must has 9 movies
			expect(items1.length).toEqual(9);

			browser.get('/');
			$('#portfolio').$$('.movie-area .project-name').getAttribute('innerHTML').then(function(items2) {
				// must has 9 movies
				expect(items2.length).toEqual(9);

				// all 9 movies must be different
				var sameMovies = true;
				for (var i = 0; i < 9; i++) {
					sameMovies = sameMovies && items1[i].trim() === items2[i].trim();
				}
				expect(sameMovies).toEqual(false);
			});
		});
	});
});