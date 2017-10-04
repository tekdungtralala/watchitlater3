package info.wiwitadityasaputra.config;

import java.util.Date;

public class AuthModel {

	private static int ACTIVE_HOUR = 2;
	private static int ACTIVE_SECOND = 10;
	private static int ACTIVE_MINUTE = 5;

	private String userId;
	private String emailAddress;
	private Date createdDate;

	public AuthModel() {
	}

	public AuthModel(String userId, String emailAddress) {
		this.userId = userId;
		this.emailAddress = emailAddress;
		this.createdDate = new Date();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void checkAuthModel() throws AuthExpiredException {
		Date currentDate = new Date();
		long secs = (currentDate.getTime() - this.createdDate.getTime()) / 1000;
		int minutes = (int) (secs / 60);
		int hours = (int) (secs / 3600);
		if (ACTIVE_HOUR <= hours)
			throw new AuthExpiredException("Auth is expired, less than " + ACTIVE_HOUR + " hour");
		// if (ACTIVE_MINUTE <= minutes)
		// throw new AuthExpiredException("Auth is expired, less than " + ACTIVE_MINUTE
		// + " minute");
		// if (ACTIVE_SECOND <= secs)
		// throw new AuthExpiredException("Auth is expired, less than " + ACTIVE_SECOND
		// + " second");
	}
}
