package sk.mlp.util;

import java.util.Optional;

public class Constants {

	public static final String USER_DATA_STORAGE = Optional.ofNullable(System.getenv("DATA_STORAGE")).orElseThrow(() -> new IllegalArgumentException("DATA_STORAGE property is not set in the environment"));
    public static final String OPEARTION_SYSTEM = System.getProperty("os.name");
    public static final String LOGGER_FILE_NAME = "gTraxxx.log";
    
    public enum Status {
    	USER,
    	ADMIN;
    	
    	public String getValue () {
    		return this.name().toLowerCase();
    	}
    }
    
	public static enum ApplicationRoles {
		ADMINISTRATORS("ROLE_ADMIN"),
		USERS("ROLE_USER");

		private String roleName;

		private ApplicationRoles(String roleName) {
			this.roleName = roleName;
		}

		public String getValue() {
			return roleName;
		}

	}
}
