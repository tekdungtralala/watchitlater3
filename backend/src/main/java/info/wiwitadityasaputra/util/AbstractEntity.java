package info.wiwitadityasaputra.util;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_dt")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDt;

	@Column(name = "last_updated_by")
	private String lastUpdatedBy;

	@Column(name = "last_updated_dt")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedDt;

	@JsonIgnore
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@JsonIgnore
	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	@JsonIgnore
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@JsonIgnore
	public Date getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Date lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@PrePersist
	public void onCreate() {
		this.setCreatedDt(new Date());
		this.setCreatedBy("system");
	}

	@PreUpdate
	public void onUpdate() {
		this.setLastUpdatedDt(new Date());
		this.setLastUpdatedBy("system");
	}
}
