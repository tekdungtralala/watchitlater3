package info.wiwitadityasaputra.moviereview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieRepository;
import info.wiwitadityasaputra.user.entity.User;

@Repository
public interface MovieReviewRepository extends JpaRepository<MovieReview, Integer> {

	public MovieReview findByUserAndMovie(User user, Movie movie);
}
