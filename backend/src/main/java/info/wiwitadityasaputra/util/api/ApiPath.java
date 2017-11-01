package info.wiwitadityasaputra.util.api;

public class ApiPath {

	public final static String API_PATH_GIT_STATUS = "/api/git-status";
	public final static String API_PATH_MOVIE_POSTER = "/api/movie-poster";
	public final static String API_PATH_MOVIE = "/api/movie";
	public final static String API_PATH_MOVIE_GROUP = "/api/movie-group";
	public final static String API_PATH_MOVIE_FAVORITE = "/api/movie-favorite";
	public final static String API_PATH_USER = "/api/user";

	// /api/user/**
	public final static String API_PATH_USER_RANDOM = API_PATH_USER + "/random";

	// /api/user/auth/**
	public final static String PATH_USER_AUTH_ME = API_PATH_USER + "/auth/me";
	public final static String PATH_USER_AUTH_SIGNIN = API_PATH_USER + "/auth/signin";
	public final static String PATH_USER_AUTH_SIGNUP = API_PATH_USER + "/auth/signup";
	public final static String PATH_USER_AUTH_SIGNOUT = API_PATH_USER + "/auth/signout";

	// /api/movie/**
	public final static String API_PATH_MOVIE_BY_GROUP_NAME = API_PATH_MOVIE + "/by-group-name";
	public final static String API_PATH_MOVIE_BY_MOVIEIDS = API_PATH_MOVIE + "/by-movie-ids";
	public final static String PATH_MOVIE_RANDOM_SIX_MOVIES = "/random-nine-movies";
	public final static String PATH_MOVIE_TOP100_MOVIES = "/top-100-movies";

}
