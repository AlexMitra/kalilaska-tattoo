package by.kalilaska.ktattoo.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsultationBean {
	private final static String DATE_PATTERN = "dd MMMM HH:mm";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
	private int id;
	private Date date;
	private int clientId;
	private String clientName;
	private int masterId;
	private String masterName;
	private boolean isApproved;

	public ConsultationBean() {		
	}

	public ConsultationBean(int id, Date date, int clientId, String clientName, int masterId, String masterName, boolean isApproved) {
		super();
		this.id = id;
		this.date = date;
		this.clientId = clientId;
		this.clientName = clientName;
		this.masterId = masterId;
		this.masterName = masterName;
		this.isApproved = isApproved;
	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
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

	public void setDate(Date date) {
		this.date = date;
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
	
	public String writeDate() {
		return dateFormat.format(date);
	}

	@Override
	public String toString() {
		return "ConsultationBean [id=" + id + ", date=" + date + ", clientId=" + clientId + ", clientName=" + clientName
				+ ", masterId=" + masterId + ", masterName=" + masterName + ", isApproved=" + isApproved + "]";
	}
}
