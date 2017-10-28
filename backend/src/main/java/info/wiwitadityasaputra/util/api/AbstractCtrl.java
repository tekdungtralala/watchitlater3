package info.wiwitadityasaputra.util.api;

import org.springframework.beans.factory.annotation.Value;

public class AbstractCtrl {
    public final static String API_PATH_GIT_STATUS = "/api/git-status";
    public final static String API_PATH_AUTH = "/api/auth";
    public final static String API_PATH_MOVIE_POSTER = "/api/movie-poster";
    public final static String API_PATH_MOVIE = "/api/movie";
    public final static String API_PATH_MOVIE_GROUP = "/api/movie-group";
    public final static String API_PATH_USER = "/api/user";

    public final static String API_PATH_AUTH_ME = API_PATH_AUTH + "/me";
    public final static String API_PATH_USER_RANDOM = API_PATH_USER + "/random";

    @Value("${git.commit.id}")
    protected String gitId;

    @Value("${git.commit.id.abbrev}")
    protected String gitIdAbbrev;

    @Value("${git.commit.user.name}")
    protected String gitCommitName;

    @Value("${git.commit.message.full}")
    protected String gitCommitMsg;
}
