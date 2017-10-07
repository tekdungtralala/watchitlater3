package info.wiwitadityasaputra.movie;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.util.api.AbstractCtrl;

@RestController
@RequestMapping(value = AbstractCtrl.API_PATH_MOVIE)
public class MovieCtrl extends AbstractCtrl {

	private final static String PATH_RANDOM_SIX_MOVIE = "/random-nine-movie";

	private Logger logger = LogManager.getLogger(MovieCtrl.class);

	@Autowired
	private MovieRepository movieRepo;

	@RequestMapping(method = RequestMethod.GET, value = PATH_RANDOM_SIX_MOVIE)
	public List<Movie> getRandom6Movie() {
		logger.info("GET " + AbstractCtrl.API_PATH_MOVIE + PATH_RANDOM_SIX_MOVIE);
		List<Movie> list = movieRepo.findAll();
		int max = list.size();
		int min = 0;

		List<Movie> results = new ArrayList<Movie>();
		for (int i = 0; i < 9; i++) {
			int r = min + (int) (Math.random() * ((max - min) + 1));
			results.add(list.get(r));
			list.remove(r);
			max--;
		}

		return results;
	}
}
