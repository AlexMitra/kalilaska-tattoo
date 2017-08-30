package by.kalilaska.ktattoo.entity;

public class TattooMasterEntity {
	private int id;
	private String name;
	private String photoUrl;
	private String aboutInfo;	

	public TattooMasterEntity() {		
	}

	public TattooMasterEntity(int id, String name, String photoUrl, String aboutInfo) {
		super();
		this.id = id;
		this.name = name;
		this.photoUrl = photoUrl;
		this.aboutInfo = aboutInfo;		
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public String getAboutInfo() {
		return aboutInfo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public void setAboutInfo(String aboutInfo) {
		this.aboutInfo = aboutInfo;
	}

	@Override
	public String toString() {
		return "TattooMasterEntity [id=" + id + ", name=" + name + ", photoUrl=" + photoUrl + ", aboutInfo=" + aboutInfo
				+ "]";
	}
}
