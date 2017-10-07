package info.wiwitadityasaputra.movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	public Movie findByImdbId(String imdbId);

	@Query(value = "SELECT * FROM movie ORDER BY imdb_rating DESC limit 100" + "", nativeQuery = true)
	public List<Movie> findTop100Movies();
}
