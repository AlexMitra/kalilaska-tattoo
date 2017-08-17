package by.kalilaska.ktattoo.bean;

import java.util.Date;

public class ConsultationBean {
	private int id;
	private Date date;
	private int clientId;
	private String clientName;
	private int masterId;
	private String masterName;

	public ConsultationBean() {
		
	}

	public ConsultationBean(int id, Date date, int clientId, String clientName, int masterId, String masterName) {
		super();
		this.id = id;
		this.date = date;
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
		return "ConsultationBean [id=" + id + ", date=" + date + ", clientId=" + clientId + ", clientName=" + clientName
				+ ", masterId=" + masterId + ", masterName=" + masterName + "]";
	}
}
