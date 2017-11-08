package info.wiwitadityasaputra.util.api;

public class ApiPath {

	public final static String API_GITSTATUS = "/api/git-status";
	public final static String API_MOVIEPOSTER = "/api/movie-poster";
	public final static String API_MOVIE = "/api/movie";
	public final static String API_MOVIEGROUP = "/api/movie-group";
	public final static String API_MOVIEFAVORITE = "/api/movie-favorite";
	public final static String API_USER = "/api/user";

	// /api/user/**
	public final static String API_USER_RANDOM = API_USER + "/random";

	// /api/user/auth/**
	public final static String API_USER_AUTH_ME = API_USER + "/auth/me";
	public final static String API_USER_AUTH_SIGNIN = API_USER + "/auth/signin";
	public final static String API_USER_AUTH_SIGNUP = API_USER + "/auth/signup";
	public final static String API_USER_AUTH_SIGNOUT = API_USER + "/auth/signout";

	// /api/movie/**
	public final static String API_MOVIE_BYGROUPNAME = API_MOVIE + "/by-group-name";
	public final static String API_MOVIE_BYMOVIEIDS = API_MOVIE + "/by-movie-ids";
	public final static String API_MOVIE_RANDOMSIXMOVIES = "/random-nine-movies";
	public final static String API_MOVIE_TOP100MOVIES = "/top-100-movies";

}
