package info.wiwitadityasaputra.config;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import info.wiwitadityasaputra.entity.movie.MovieRepository;
import info.wiwitadityasaputra.entity.moviesearch.MovieSearch;
import info.wiwitadityasaputra.entity.moviesearch.MovieSearchRepository;

@Component
public class SearchMovieSchedule {

	private Logger logger = LogManager.getLogger(SearchMovieSchedule.class);

	private boolean stillRunnig = false;

	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private MovieSearchRepository movieSearchRepo;

	@Scheduled(fixedRateString = "10000", initialDelay = 1000)
	public void start() throws Exception {
		if (!stillRunnig) {
			stillRunnig = true;
			logger.info("start(), stillRunnig: " + stillRunnig);
			logger.info(movieRepo.count());
			logger.info(movieSearchRepo.count());

			List<MovieSearch> list = movieSearchRepo.findAll();
			for (MovieSearch ms : list) {
				if (ms.getMovie() != null) {
					logger.info(ms.getId() + " - " + ms.getMovie().getReleased());
				}
			}

			stillRunnig = false;
		}
	}
}
