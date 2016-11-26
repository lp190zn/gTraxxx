package sk.mlp.ui.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import sk.mlp.util.Constants;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the USERS database table.
 * 
 */
@Entity
@Table(name="USERS")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userGen")
    @SequenceGenerator(name = "userGen", sequenceName = "GPSWEBAPP.USERS_SEQ", allocationSize=1)
	private long userId;

	@Column(name="USER_ACCEPTED")
	@Type(type= "org.hibernate.type.NumericBooleanType")
	private boolean userAccepted;

	@Column(name="USER_ACTIVITY")
	private String userActivity;

	@Column(name="USER_AGE")
	private Integer userAge;

	@Column(name="USER_EMAIL")
	private String userEmail;

	@Column(name="USER_FIRST_NAME")
	private String userFirstName;

	@Column(name="USER_LAST_NAME")
	private String userLastName;

	@Column(name="USER_PASS")
	private String userPass;

	@Column(name="USER_STATUS")
	private String userStatus = Constants.ApplicationRoles.USERS.getValue();

	@Column(name="USER_TOKEN")
	private String userToken;

	//bi-directional many-to-one association to Track
	@OneToMany(mappedBy="user")
	private List<Track> tracks;
	
	@Transient
	private String retypeUserPass;

	public User() {
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean getUserAccepted() {
		return this.userAccepted;
	}

	public void setUserAccepted(boolean userAccepted) {
		this.userAccepted = userAccepted;
	}

	public String getUserActivity() {
		return this.userActivity;
	}

	public void setUserActivity(String userActivity) {
		this.userActivity = userActivity;
	}

	public Integer getUserAge() {
		return this.userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserFirstName() {
		return this.userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return this.userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserPass() {
		return this.userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserToken() {
		return this.userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public List<Track> getTracks() {
		return this.tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	public Track addTrack(Track track) {
		getTracks().add(track);
		track.setUser(this);

		return track;
	}

	public Track removeTrack(Track track) {
		getTracks().remove(track);
		track.setUser(null);

		return track;
	}

	public String getRetypeUserPass() {
		return retypeUserPass;
	}

	public void setRetypeUserPass(String retypeUserPass) {
		this.retypeUserPass = retypeUserPass;
	}

}