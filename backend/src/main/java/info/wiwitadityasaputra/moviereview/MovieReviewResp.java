package info.wiwitadityasaputra.moviereview;

import java.util.Date;
import org.springframework.util.StringUtils;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.user.entity.User;

public class MovieReviewResp {

	private int id;
	private String review;
	private int point;

	private Integer movieId;
	private String title;
	private String imdbId;
	private Double imdbRating;
	private Date released;
	private String genre;
	private int year;
	private String plot;
	private String json;

	private String userId;
	private String initial;

	public static MovieReviewResp toOutput(MovieReview mr) {
		MovieReviewResp o = new MovieReviewResp();
		if (mr == null)
			return o;

		o.setId(mr.getId());
		o.setReview(mr.getReview());
		o.setPoint(mr.getPoint());

		Movie m = mr.getMovie();
		o.setMovieId(m.getId());
		o.setTitle(m.getTitle());
		o.setImdbId(m.getImdbId());
		o.setImdbRating(m.getImdbRating());
		o.setReleased(m.getReleased());
		o.setGenre(m.getGenre());
		o.setYear(m.getYear());
		o.setPlot(m.getPlot());
		o.setJson(m.getJson());

		User u = mr.getUser();
		o.setUserId(u.getUserId());
		o.setInitial(StringUtils.isEmpty(u.getInitial()) ? "Anonymous" : u.getInitial());
		return o;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public Double getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(Double imdbRating) {
		this.imdbRating = imdbRating;
	}

	public Date getReleased() {
		return released;
	}

	public void setReleased(Date released) {
		this.released = released;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

}
