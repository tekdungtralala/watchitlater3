package info.wiwitadityasaputra.api;

import org.springframework.beans.factory.annotation.Value;

public class AbstractCtrl {
	@Value("${git.commit.id}")
	protected String gitId;

	@Value("${git.commit.id.abbrev}")
	protected String gitIdAbbrev;

	@Value("${git.commit.user.name}")
	protected String gitCommitName;

	@Value("${git.commit.message.full}")
	protected String gitCommitMsg;
}
