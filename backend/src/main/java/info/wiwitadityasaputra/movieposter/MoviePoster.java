package info.wiwitadityasaputra.movieposter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.util.AbstractEntity;

@Entity
@Table(name = "movie_poster")
public class MoviePoster extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Lob
	@Column(name = "img_byte")
	private byte[] imgByte;

	@Column(name = "file_type")
	private String fileType;

	@Column(name = "main")
	private boolean main;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "movie_id", nullable = false)
	private Movie movie;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getImgByte() {
		return imgByte;
	}

	public void setImgByte(byte[] imgByte) {
		this.imgByte = imgByte;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}
