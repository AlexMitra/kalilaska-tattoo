package by.kalilaska.ktattoo.bean;

public class TattooWorkPhotoBean {
	private int id;
	private String url;
	private boolean isDone;
	//private String masterName;

	public TattooWorkPhotoBean() {
		
	}
	public TattooWorkPhotoBean(int id, String url, boolean isDone) {
		super();
		this.id = id;
		this.url = url;
		this.isDone = isDone;
	}
	public int getId() {
		return id;
	}
	public String getUrl() {
		return url;
	}
	public boolean isDone() {
		return isDone;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	@Override
	public String toString() {
		return "TattooWorkPhotoBean [id=" + id + ", url=" + url + ", isDone=" + isDone + "]";
	}	
}
