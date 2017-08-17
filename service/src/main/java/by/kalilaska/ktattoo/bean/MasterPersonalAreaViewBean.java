package by.kalilaska.ktattoo.bean;

import java.util.LinkedList;
import java.util.List;

public class MasterPersonalAreaViewBean extends AbstractPersonalAreaViewBean{
	private String aboutInfo;
	private List<TattooWorkPhotoBean> photos;
	private List<TattooStyleBean> styles;

	public MasterPersonalAreaViewBean() {
		photos = new LinkedList<>();
		styles = new LinkedList<>();
	}

	public String getAboutInfo() {
		return aboutInfo;
	}

	public List<TattooWorkPhotoBean> getPhotos() {
		return photos;
	}

	public List<TattooStyleBean> getStyles() {
		return styles;
	}

	public void setAboutInfo(String aboutInfo) {
		this.aboutInfo = aboutInfo;
	}

	public void setPhotos(List<TattooWorkPhotoBean> photos) {
		if(photos != null) {
			this.photos.addAll(photos);
		}		
	}

	public void setStyles(List<TattooStyleBean> styles) {
		if(styles != null) {
			this.styles.addAll(styles);
		}		
	}
	
	public void addPhoto(TattooWorkPhotoBean photo) {
		if(photo != null && !photos.contains(photo)) {
			photos.add(photo);
		}		
	}
	
	public void removePhoto(TattooWorkPhotoBean photo) {
		if(photo != null) {
			photos.remove(photo);
		}		
	}
	
	public void addStyle(TattooStyleBean style) {
		if(style != null && !styles.contains(style)) {
			styles.add(style);
		}		
	}
	
	public void removeStyle(TattooStyleBean style) {
		if(style != null) {
			styles.remove(style);
		}		
	}

	@Override
	public String toString() {
		return "MasterPersonalAreaViewBean [aboutInfo=" + aboutInfo + ", photos=" + photos + ", styles=" + styles
				+ ", getId()=" + getId() + ", getName()=" + getName() + ", getEmail()=" + getEmail() + ", isAllowed()="
				+ isAllowed() + ", getRole()=" + getRole() + ", getConsultations()=" + getConsultations()
				+ ", getSeances()=" + getSeances() + "]";
	}

}
