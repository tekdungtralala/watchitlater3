package info.wiwitadityasaputra.moviefavorite;

public class MovieFavoriteReq {

	private int movieId;
	private boolean favorite;

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
}
