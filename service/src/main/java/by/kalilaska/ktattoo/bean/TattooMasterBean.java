package by.kalilaska.ktattoo.bean;

import java.util.List;

public class TattooMasterBean {
	private int id;
	private String name;
	private String masterPhotoUrl;
	private String aboutInfo;
	List<TattooPhotoBean> photos;
	List<String> styleNames;
	
	public TattooMasterBean() {		
	}

	public TattooMasterBean(int id, String name, String masterPhotoUrl, String aboutInfo) {
		super();
		this.id = id;
		this.name = name;
		this.masterPhotoUrl = masterPhotoUrl;
		this.aboutInfo = aboutInfo;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getMasterPhotoUrl() {
		return masterPhotoUrl;
	}

	public String getAboutInfo() {
		return aboutInfo;
	}

	public List<TattooPhotoBean> getPhotos() {
		return photos;
	}

	public List<String> getStyleNames() {
		return styleNames;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMasterPhotoUrl(String masterPhotoUrl) {
		this.masterPhotoUrl = masterPhotoUrl;
	}

	public void setAboutInfo(String aboutInfo) {
		this.aboutInfo = aboutInfo;
	}

	public void setPhotos(List<TattooPhotoBean> photos) {
		this.photos = photos;
	}

	public void setStyleNames(List<String> styleNames) {
		this.styleNames = styleNames;
	}

	@Override
	public String toString() {
		return "TattooMasterBean [id=" + id + ", name=" + name + ", masterPhotoUrl=" + masterPhotoUrl + ", aboutInfo="
				+ aboutInfo + ", photos=" + photos + ", styleNames=" + styleNames + "]";
	}
}
