package info.wiwitadityasaputra.movieposter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;
import info.wiwitadityasaputra.util.api.exception.NotFoundException;

@RestController
@RequestMapping(value = ApiPath.API_MOVIEPOSTER)
public class MoviePosterCtrl extends AbstractCtrl {

	@Autowired
	private MoviePosterRepository moviePosterRepo;

	@RequestMapping(method = RequestMethod.GET, value = "/{imdbId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPosterByImdbId(@PathVariable("imdbId") String imdbId) {

		List<MoviePoster> list = moviePosterRepo.findByImdbId(imdbId);
		if (list == null || list.isEmpty())
			throw new NotFoundException("Can't find image with imdbId = " + imdbId);

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
