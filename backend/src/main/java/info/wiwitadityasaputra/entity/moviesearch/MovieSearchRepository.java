package info.wiwitadityasaputra.entity.moviesearch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSearchRepository extends JpaRepository<MovieSearch, Integer> {

}
