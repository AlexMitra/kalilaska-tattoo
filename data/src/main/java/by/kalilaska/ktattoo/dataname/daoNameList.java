package by.kalilaska.ktattoo.dataname;

public class daoNameList {

	public final static int DEFAULT_ROLE_ID = 3;
	public final static int DEFAULT_CONSULTATION_DURATION = 1;
	public final static boolean DEFAULT_CONSULTATION_IS_APPROVED = false;
	
	public final static String ACCOUNT_DAO_RESULTSET_GET_ID = "id";
	public final static String ACCOUNT_DAO_RESULTSET_GET_NAME = "name";
	public final static String ACCOUNT_DAO_RESULTSET_GET_EMAIL = "email";
	public final static String ACCOUNT_DAO_RESULTSET_GET_PASS = "password";
	public final static String ACCOUNT_DAO_RESULTSET_GET_PHONE = "phone";
	public final static String ACCOUNT_DAO_RESULTSET_GET_PHOTO_URL = "photo_url";
	public final static String ACCOUNT_DAO_RESULTSET_GET_IS_ALLOWED = "is_allowed";
	public final static String ACCOUNT_DAO_RESULTSET_GET_ROLE = "role";
	
	public final static String CONSULTATION_DAO_RESULTSET_GET_ID = "id";
	public final static String CONSULTATION_DAO_RESULTSET_GET_DATE_START = "date_start";
	public final static String CONSULTATION_DAO_RESULTSET_GET_CLIENT_ID = "FK_account_id";
	public final static String CONSULTATION_DAO_RESULTSET_GET_CLIENT_NAME = "client";
	public final static String CONSULTATION_DAO_RESULTSET_GET_MASTER_ID = "FK_master_id";
	public final static String CONSULTATION_DAO_RESULTSET_GET_MASTER_NAME = "master";
	public final static String CONSULTATION_DAO_RESULTSET_GET_IS_APPROVED = "is_approved";
	
	public final static String ROLE_DAO_RESULTSET_GET_ID = "id";
	public final static String ROLE_DAO_RESULTSET_GET_NAME = "name";
	
	public final static String SEANCE_DAO_RESULTSET_GET_ID = "id";
	public final static String SEANCE_DAO_RESULTSET_GET_DATE_START = "date_start";
	public final static String SEANCE_DAO_RESULTSET_GET_DURATION = "duration_hours";
	public final static String SEANCE_DAO_RESULTSET_GET_COST = "cost_per_hour";
	public final static String SEANCE_DAO_RESULTSET_GET_CLIENT_ID = "FK_account_id";
	public final static String SEANCE_DAO_RESULTSET_GET_CLIENT_NAME = "client";
	public final static String SEANCE_DAO_RESULTSET_GET_MASTER_ID = "FK_master_id";
	public final static String SEANCE_DAO_RESULTSET_GET_MASTER_NAME = "master";
	
	public final static String MASTER_DAO_RESULTSET_GET_ID = "id";
	public final static String MASTER_DAO_RESULTSET_GET_NAME = "name";
	public final static String MASTER_DAO_RESULTSET_GET_PHOTO_URL = "photo_url";
	public final static String MASTER_DAO_RESULTSET_GET_ABOUT = "about_info";
	
	public final static String PHOTO_DAO_RESULTSET_GET_ID = "id";
	public final static String PHOTO_DAO_RESULTSET_GET_URL = "url";
	public final static String PHOTO_DAO_RESULTSET_GET_IS_DONE = "is_done";
	public final static String PHOTO_DAO_RESULTSET_GET_MASTER_ID = "FK_master_id";
	
	public final static String STYLE_DAO_RESULTSET_GET_ID = "id";
	public final static String STYLE_DAO_RESULTSET_GET_NAME = "name";
	public final static String STYLE_DAO_RESULTSET_GET_DESCRIPTION = "description";
}
