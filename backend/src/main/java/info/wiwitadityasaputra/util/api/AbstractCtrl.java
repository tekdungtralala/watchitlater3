package info.wiwitadityasaputra.util.api;

import org.springframework.beans.factory.annotation.Value;

public class AbstractCtrl {
	public final static String API_PATH_GIT_STATUS = "/api/git-status";
	public final static String API_PATH_AUTH = "/api/auth";
	public final static String API_PATH_MOVIE_POSTER = "/api/movie-poster";

	@Value("${git.commit.id}")
	protected String gitId;

	@Value("${git.commit.id.abbrev}")
	protected String gitIdAbbrev;

	@Value("${git.commit.user.name}")
	protected String gitCommitName;

	@Value("${git.commit.message.full}")
	protected String gitCommitMsg;
}
