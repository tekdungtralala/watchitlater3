package info.wiwitadityasaputra.moviereview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.user.entity.User;

@Repository
public interface MovieReviewRepository extends JpaRepository<MovieReview, Integer> {

	public MovieReview findByUserAndMovie(User user, Movie movie);

	@Query(value = "SELECT * FROM movie_review WHERE movie_id = ?1 AND latest = true ORDER BY point DESC LIMIT ?2 OFFSET ?3", nativeQuery = true)
	public List<MovieReview> findByMovieId(int movieId, int limit, int offset);

	public MovieReview findByMovieAndUserAndLatest(User user, Movie movie, boolean latest);
}
