package info.wiwitadityasaputra.movieFavorite;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.user.entity.User;

@Repository
public interface MovieFavoriteRepository extends JpaRepository<MovieFavorite, Integer> {

	public List<MovieFavorite> findByUser(User user);

	public MovieFavorite findByUserAndMovie(User user, Movie movie);
}
