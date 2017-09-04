package by.kalilaska.ktattoo.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SeanceEntity {
	
	private int id;
	private Date dateStart;
	private Date dateEnd;
	private byte duration;
	private BigDecimal costPerHour;
	private int clientId;
	private String clientName;
	private int masterId;
	private String masterName;

	public SeanceEntity() {		
	}

	public SeanceEntity(int id, Date dateStart, Date dateEnd, byte duration, BigDecimal costPerHour, int clientId, String clientName,
			int masterId, String masterName) {		
		this.id = id;
		this.dateStart = dateStart;
		this.dateStart = dateEnd;
		this.duration = duration;
		this.costPerHour = costPerHour;
		this.clientId = clientId;
		this.clientName = clientName;
		this.masterId = masterId;
		this.masterName = masterName;
	}
	
	public SeanceEntity(int id, Date dateStart, byte duration, BigDecimal costPerHour, int clientId, String clientName,
			int masterId, String masterName) {		
		this.id = id;
		this.dateStart = dateStart;		
		this.duration = duration;
		this.costPerHour = costPerHour;
		this.clientId = clientId;
		this.clientName = clientName;
		this.masterId = masterId;
		this.masterName = masterName;
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

	public byte getDuration() {
		return duration;
	}

	public BigDecimal getCostPerHour() {
		return costPerHour;
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

	public void setId(int id) {
		this.id = id;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setDuration(byte duration) {
		this.duration = duration;
	}

	public void setCostPerHour(BigDecimal costPerHour) {
		this.costPerHour = costPerHour;
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

	@Override
	public String toString() {
		return "SeanceEntity [id=" + id + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", duration=" + duration
				+ ", costPerHour=" + costPerHour + ", clientId=" + clientId + ", clientName=" + clientName
				+ ", masterId=" + masterId + ", masterName=" + masterName + "]";
	}
}
