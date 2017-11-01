package info.wiwitadityasaputra.movie;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepo;

	public List<Movie> findMovieByIds(JSONArray movieIds) throws JSONException {
		List<Movie> results = new ArrayList<Movie>();

		for (int i = 0; i < movieIds.length(); i++) {
			int movieId = (Integer) movieIds.get(i);
			results.add(movieRepo.findOne(movieId));
		}

		return results;
	}
}
