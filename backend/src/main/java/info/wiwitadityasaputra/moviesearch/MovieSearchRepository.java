package info.wiwitadityasaputra.moviesearch;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSearchRepository extends JpaRepository<MovieSearch, Integer> {

	@Query(value = "FROM MovieSearch ms WHERE ms.movie IS NULL")
	List<MovieSearch> findByEmptyMovie();
}
