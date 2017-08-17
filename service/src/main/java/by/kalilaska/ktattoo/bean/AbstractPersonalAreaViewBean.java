package by.kalilaska.ktattoo.bean;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPersonalAreaViewBean {
	private int id;
	private String name;
	private String email;	
	private String phone;
	private String photoURL;
	private boolean isAllowed;
	private String role;
	private List<ConsultationBean> consultations;
	private List<SeanceBean> seances;

	public AbstractPersonalAreaViewBean() {
		consultations = new LinkedList<>();
		seances = new LinkedList<>();
	}	
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public boolean isAllowed() {
		return isAllowed;
	}

	public String getRole() {
		return role;
	}

	public List<ConsultationBean> getConsultations() {
		return consultations;
	}

	public List<SeanceBean> getSeances() {
		return seances;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public void setAllowed(boolean isAllowed) {
		this.isAllowed = isAllowed;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setConsultations(List<ConsultationBean> consultations) {
		if(consultations != null) {
			this.consultations.addAll(consultations);
		}		
	}
	
	public void updateConsultations(List<ConsultationBean> consultations) {
		if(consultations != null) {
			this.consultations.clear();
			this.consultations.addAll(consultations);
		}	
	}

	public void setSeances(List<SeanceBean> seances) {
		if(seances != null) {
			this.seances.addAll(seances);
		}		
	}
	
	public void updateSeances(List<SeanceBean> seances) {
		if(seances != null) {
			this.seances.clear();
			this.seances.addAll(seances);
		}
	}

	public void addConsultation(ConsultationBean consultation) {
		if(consultation != null && !consultations.contains(consultation)) {
			consultations.add(consultation);
		}		
	}
	
	public void removeConsultation(ConsultationBean consultation) {
		if(consultation != null) {
			consultations.remove(consultation);
		}
	}
	
	public void addSeance(SeanceBean seance) {
		if(seance != null && !seances.contains(seance)) {
			seances.add(seance);
		}
	}
	
	public void removeSeance(SeanceBean seance) {
		if(seance != null) {
			seances.remove(seance);
		}
	}

	@Override
	public String toString() {
		return "AbstractPersonalAreaViewBean [id=" + id + ", name=" + name + ", email=" + email + ", isAllowed="
				+ isAllowed + ", role=" + role + ", consultations=" + consultations + ", seances=" + seances
				+ "]";
	}

}
