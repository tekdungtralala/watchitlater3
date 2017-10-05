package info.wiwitadityasaputra.config;

import java.util.Date;

public class AuthModel {

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
}
