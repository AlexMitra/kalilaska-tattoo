package by.kalilaska.ktattoo.bean;

import java.util.List;

public class MasterPersonalAreaViewBean extends AbstractPersonalAreaViewBean{
	private final static String STYLE_LIST_DELIITER = ", ";
	private String aboutInfo;
	private List<TattooPhotoBean> photos;
	private List<TattooStyleBean> styles;

	public MasterPersonalAreaViewBean() {
	}

	public String getAboutInfo() {
		return aboutInfo;
	}

	public List<TattooPhotoBean> getPhotos() {
		return photos;
	}

	public List<TattooStyleBean> getStyles() {
		return styles;
	}
	
	public String writeStyles() {
		StringBuilder result = new StringBuilder();
		if(styles != null && !styles.isEmpty()) {
			for (int i=0; i< styles.size(); i++) {
				if(i > 0) {
					result.append(STYLE_LIST_DELIITER);
				}
				result.append(styles.get(i).getName());
			}
		}
		
		return result.toString();
	}

	public void setAboutInfo(String aboutInfo) {
		this.aboutInfo = aboutInfo;
	}

	public void setPhotos(List<TattooPhotoBean> photos) {		
		this.photos = photos;		
	}

	public void setStyles(List<TattooStyleBean> styles) {		
		this.styles = styles;
				
	}
	
	public void addPhoto(TattooPhotoBean photo) {
		if(photo != null && !photos.contains(photo)) {
			photos.add(photo);
		}		
	}
	
	public void removePhoto(TattooPhotoBean photo) {
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
