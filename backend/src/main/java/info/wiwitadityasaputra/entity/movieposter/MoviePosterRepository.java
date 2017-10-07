package info.wiwitadityasaputra.entity.movieposter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.wiwitadityasaputra.entity.movie.Movie;

@Repository
public interface MoviePosterRepository extends JpaRepository<MoviePoster, Integer> {

	public List<MoviePoster> findByMovie(Movie movie);
}
