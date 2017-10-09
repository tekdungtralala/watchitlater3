package info.wiwitadityasaputra.movieposter;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.NotFoundException;

@RestController
@RequestMapping(value = AbstractCtrl.API_PATH_MOVIE_POSTER)
public class MoviePosterCtrl extends AbstractCtrl {

	private Logger logger = LogManager.getLogger(MoviePoster.class);

	@Autowired
	private MoviePosterRepository moviePosterRepo;

	@RequestMapping(method = RequestMethod.GET, value = "/{imdbId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPosterByImdbId(@PathVariable("imdbId") String imdbId) throws Exception {
		logger.info("GET " + AbstractCtrl.API_PATH_MOVIE_POSTER + "/" + imdbId);

		List<MoviePoster> list = moviePosterRepo.findByImdbId(imdbId);
		if (list == null || list.size() == 0)
			throw new NotFoundException();

		MoviePoster moviePoster = null;
		for (MoviePoster mp : list) {
			if (mp.isMain())
				moviePoster = mp;
		}

		if (moviePoster == null)
			moviePoster = list.get(0);

		return moviePoster.getImgByte();
	}
}
