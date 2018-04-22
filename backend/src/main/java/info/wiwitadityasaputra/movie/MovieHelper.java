package info.wiwitadityasaputra.movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.wiwitadityasaputra.moviesearch.MovieSearch;
import info.wiwitadityasaputra.moviesearch.MovieSearchRepository;

@Component
public class MovieHelper {

	@Autowired
	private MovieSearchRepository movieSearchRepo;

	public List<MovieSearch> fetchMovieByGroupName(String groupName) throws IOException {
		// Get movies title
		String url = "http://www.boxofficemojo.com/daily/chart/?sortdate=" + groupName.split("_")[0];
		Document doc = Jsoup.connect(url).timeout(5000).get();
		Elements titles = doc.select("center table tbody tr td table tbody tr td b a");

		// Create MovieSearch
		List<MovieSearch> listMovieSearch = new ArrayList<MovieSearch>();
		for (Element e : titles) {
			String title = getMovieTitle(e);
			MovieSearch ms = movieSearchRepo.findByQuery(title);
			if (ms == null) {
				ms = new MovieSearch();
				ms.setQuery(title);
				movieSearchRepo.save(ms);
			}
			listMovieSearch.add(ms);
		}
		return listMovieSearch;
	}

	private String getMovieTitle(Element e) {
		String title = e.html();
		int ob = title.indexOf('(');
		int cb = title.indexOf(')');
		if (ob >= 0 && cb >= 0 && ob < cb) {
			title = title.split("\\(")[0].trim();
		}
		return title;
	}
}
