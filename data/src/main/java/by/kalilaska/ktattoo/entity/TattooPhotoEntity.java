package by.kalilaska.ktattoo.entity;

public class TattooPhotoEntity {
	private int id;
	private String photoUrl;
	boolean done;
	private int masterId;

	public TattooPhotoEntity() {		
	}

	public TattooPhotoEntity(int id, String photoUrl, boolean done, int masterId) {
		super();
		this.id = id;
		this.photoUrl = photoUrl;
		this.done = done;
		this.masterId = masterId;
	}

	public int getId() {
		return id;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public boolean isDone() {
		return done;
	}

	public int getMasterId() {
		return masterId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}

	@Override
	public String toString() {
		return "TattooPhotoEntity [id=" + id + ", photoUrl=" + photoUrl + ", done=" + done + ", masterId="
				+ masterId + "]";
	}
}
