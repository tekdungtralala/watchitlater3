package info.wiwitadityasaputra.moviegroup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import info.wiwitadityasaputra.util.AbstractEntity;

@Entity
@Table(name = "movie_group")
public class MovieGroup extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "movie_ids")
	private String movieIds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMovieIds() {
		return movieIds;
	}

	public void setMovieIds(String movieIds) {
		this.movieIds = movieIds;
	}
}
