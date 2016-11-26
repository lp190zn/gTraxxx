package sk.mlp.ui.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import sk.mlp.util.Constants;

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
	@Column(name="IDENT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userGen")
    @SequenceGenerator(name = "userGen", sequenceName = "GTRAXXX.USERS_SEQ", allocationSize=1)
	private long ident;

	@Column(name="ACCEPTED")
	@Type(type= "org.hibernate.type.NumericBooleanType")
	private boolean accepted;

	@Column(name="ACTIVITY")
	private String activity;

	@Column(name="AGE")
	private Integer age;

	@Column(name="EMAIL")
	private String email;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="PASS")
	private String pass;

	@Column(name="ROLE")
	private String role = Constants.ApplicationRoles.USERS.getValue();

	@Column(name="TOKEN")
	private String token;
	
	@Transient
	private String retypeUserPass;

	//bi-directional many-to-one association to Track
	@OneToMany(mappedBy="user")
	private List<Track> tracks;

	public User() {
	}

	public long getIdent() {
		return this.ident;
	}

	public void setIdent(long ident) {
		this.ident = ident;
	}

	public boolean getAccepted() {
		return this.accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public String getActivity() {
		return this.activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRetypeUserPass() {
		return retypeUserPass;
	}

	public void setRetypeUserPass(String retypeUserPass) {
		this.retypeUserPass = retypeUserPass;
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

}