package info.wiwitadityasaputra.moviereview;

import org.springframework.util.StringUtils;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.user.entity.User;

public class MovieReviewResp {

	private int id;
	private String review;
	private int point;

	private Integer movieId;
	private String title;

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
