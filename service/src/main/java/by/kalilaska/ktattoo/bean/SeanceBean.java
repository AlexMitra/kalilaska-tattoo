package by.kalilaska.ktattoo.bean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SeanceBean {
	private final static String DATE_PATTERN = "dd MMMM HH:mm";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
	
	private int id;
	private Date date;
	private byte duration;
	private BigDecimal costPerHour;
	private int clientId;
	private String clientName;
	private int masterId;
	private String masterName;

	public SeanceBean() {		
	}

	public SeanceBean(int id, Date date, byte duration, BigDecimal costPerHour, int clientId, String clientName,
			int masterId, String masterName) {
		super();
		this.id = id;
		this.date = date;
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

	public Date getDate() {
		return date;
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

	public void setDate(Date date) {
		this.date = date;
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
	
	public String writeDate() {
		return dateFormat.format(date);
	}

	@Override
	public String toString() {
		return "SeanceBean [id=" + id + ", date=" + date + ", duration=" + duration + ", costPerHour=" + costPerHour
				+ ", clientId=" + clientId + ", clientName=" + clientName + ", masterId=" + masterId + ", masterName="
				+ masterName + "]";
	}
}
