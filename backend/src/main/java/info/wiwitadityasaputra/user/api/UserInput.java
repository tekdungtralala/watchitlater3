package info.wiwitadityasaputra.user.api;

public class UserInput {

	private String initial;
	private String base64ProfilePicture;
	private String fileType;

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public String getBase64ProfilePicture() {
		return base64ProfilePicture;
	}

	public void setBase64ProfilePicture(String base64ProfilePicture) {
		this.base64ProfilePicture = base64ProfilePicture;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
