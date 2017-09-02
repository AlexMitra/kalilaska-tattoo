package by.kalilaska.ktattoo.entity;

import java.util.Date;

public class ConsultationEntity {
	private int id;
	private Date dateStart;
	private Date dateEnd;
	private int clientId;
	private String clientName;
	private int masterId;
	private String masterName;
	private boolean isApproved;

	public ConsultationEntity() {		
	}

	public ConsultationEntity(int id, Date dateStart, Date dateEnd, int clientId, String clientName, 
			int masterId, String masterName, boolean isApproved) {
		super();
		this.id = id;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.clientId = clientId;
		this.clientName = clientName;
		this.masterId = masterId;
		this.masterName = masterName;
		this.isApproved = isApproved;
	}
	
	public ConsultationEntity(int id, Date dateStart, int clientId, String clientName, 
			int masterId, String masterName, boolean isApproved) {
		super();
		this.id = id;
		this.dateStart = dateStart;		
		this.clientId = clientId;
		this.clientName = clientName;
		this.masterId = masterId;
		this.masterName = masterName;
		this.isApproved = isApproved;
	}

	public int getId() {
		return id;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public int getClientId() {
		return clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public int getMasterId() {
		return masterId;
	}

	public String getMasterName() {
		return masterName;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	@Override
	public String toString() {
		return "ConsultationEntity [id=" + id + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", clientId="
				+ clientId + ", clientName=" + clientName + ", masterId=" + masterId + ", masterName=" + masterName
				+ ", isApproved=" + isApproved + "]";
	}
}
