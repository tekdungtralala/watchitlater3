package info.wiwitadityasaputra.moviegroup;

public class MovieGroupCtrlOutput {

	private String groupName;
	private String firstDayOfWeek;
	private String lastDayOfWeek;
	private boolean isAvailable;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getFirstDayOfWeek() {
		return firstDayOfWeek;
	}

	public void setFirstDayOfWeek(String firstDayOfWeek) {
		this.firstDayOfWeek = firstDayOfWeek;
	}

	public String getLastDayOfWeek() {
		return lastDayOfWeek;
	}

	public void setLastDayOfWeek(String lastDayOfWeek) {
		this.lastDayOfWeek = lastDayOfWeek;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
}
