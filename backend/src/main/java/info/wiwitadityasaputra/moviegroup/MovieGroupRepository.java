package info.wiwitadityasaputra.moviegroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieGroupRepository extends JpaRepository<MovieGroup, Integer> {

	public MovieGroup findByName(String name);
}
