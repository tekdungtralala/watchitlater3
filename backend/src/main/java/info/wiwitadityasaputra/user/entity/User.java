package info.wiwitadityasaputra.user.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import info.wiwitadityasaputra.moviefavorite.MovieFavorite;
import info.wiwitadityasaputra.moviereview.MovieReview;
import info.wiwitadityasaputra.util.AbstractEntity;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "email")
	private String email;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "fullName")
	private String fullName;

	@Column(name = "initial")
	private String initial;

	@JsonIgnore
	@Lob
	@Column(name = "profile_picture")
	private byte[] profilePicture;

	@JsonIgnore
	@Column(name = "file_type")
	private String fileType;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
	private Set<MovieFavorite> listMovieFavorite;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
	private Set<MovieReview> listMovieReview;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@JsonIgnore
	public Set<MovieFavorite> getListMovieFavorite() {
		return listMovieFavorite;
	}

	public void setListMovieFavorite(Set<MovieFavorite> listMovieFavorite) {
		this.listMovieFavorite = listMovieFavorite;
	}

	@JsonIgnore
	public Set<MovieReview> getListMovieReview() {
		return listMovieReview;
	}

	public void setListMovieReview(Set<MovieReview> listMovieReview) {
		this.listMovieReview = listMovieReview;
	}
}
