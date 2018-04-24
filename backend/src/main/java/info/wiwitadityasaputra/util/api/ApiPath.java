package info.wiwitadityasaputra.util.api;

public class ApiPath {

	public ApiPath() {
		throw new IllegalStateException("ApiPath class");
	}

	public static final String API_GITSTATUS = "/api/git-status";
	public static final String API_MOVIEPOSTER = "/api/movie-poster";
	public static final String API_MOVIE = "/api/movie";
	public static final String API_MOVIEGROUP = "/api/movie-group";
	public static final String API_MOVIEFAVORITE = "/api/movie-favorite";
	public static final String API_MOVIEREVIEW = "/api/movie-review";
	public static final String API_USER = "/api/user";

	// /api/user/**
	public static final String API_USER_RANDOM = API_USER + "/random";
	public static final String API_USER_PROFILEPICTURE = API_USER + "/profile-picture";

	// /api/user/auth/**
	public static final String API_USER_AUTH_ME = API_USER + "/auth/me";
	public static final String API_USER_AUTH_SIGNIN = API_USER + "/auth/signin";
	public static final String API_USER_AUTH_SIGNUP = API_USER + "/auth/signup";
	public static final String API_USER_AUTH_SIGNOUT = API_USER + "/auth/signout";

	// /api/movie/**
	public static final String API_MOVIE_BYGROUPNAME = API_MOVIE + "/by-group-name";
	public static final String API_MOVIE_BYMOVIEIDS = API_MOVIE + "/by-movie-ids";
	public static final String API_MOVIE_RANDOMSIXMOVIES = "/random-nine-movies";
	public static final String API_MOVIE_TOP100MOVIES = "/top-100-movies";
}
