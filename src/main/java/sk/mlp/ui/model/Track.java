package sk.mlp.ui.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the TRACKS database table.
 * 
 */
@Entity
@Table(name="TRACKS")
@NamedQuery(name="Track.findAll", query="SELECT t FROM Track t")

public class Track implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
	@Column(name="TRACK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "trackGen")
    @SequenceGenerator(name = "trackGen", sequenceName = "GPSWEBAPP.TRACKS_SEQ", allocationSize=1)

	private long trackId;

	@Column(name="TRACK_ACCESS")
	private String trackAccess;

	@Column(name="TRACK_ACTIVITY")
	private String trackActivity;

	@Column(name="TRACK_CREATION_TYPE")
	private String trackCreationType;

	@Column(name="TRACK_DATE_CREATED")
	private Date trackDateCreated;

	@Column(name="TRACK_DATE_UPDATED")
	private Date trackDateUpdated;

	@Column(name="TRACK_DESCRIPTION")
	private String trackDescription;

	@Column(name="TRACK_DURATION")
	private String trackDuration;

	@Column(name="TRACK_END_ADDRESS")
	private String trackEndAddress;

	@Column(name="TRACK_ENDDATE")
	private Date trackEnddate;

	@Column(name="TRACK_FILE")
	private String trackFile;

	@Column(name="TRACK_HEIGHT_DIFF")
	private String trackHeightDiff;

	@Column(name="TRACK_LENGTH_KM")
	private String trackLengthKm;

	@Column(name="TRACK_MAX_ELEVATION")
	private String trackMaxElevation;

	@Column(name="TRACK_MIN_ELEVATION")
	private String trackMinElevation;

	@Column(name="TRACK_NAME")
	private String trackName;

	@Column(name="TRACK_START_ADDRESS")
	private String trackStartAddress;

	@Column(name="TRACK_STARTDATE")
	private Date trackStartdate;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="TRACK_USER_ID")
	private User user;

	public Track() {
	}

	public long getTrackId() {
		return this.trackId;
	}

	public void setTrackId(long trackId) {
		this.trackId = trackId;
	}

	public String getTrackAccess() {
		return this.trackAccess;
	}

	public void setTrackAccess(String trackAccess) {
		this.trackAccess = trackAccess;
	}

	public String getTrackActivity() {
		return this.trackActivity;
	}

	public void setTrackActivity(String trackActivity) {
		this.trackActivity = trackActivity;
	}

	public String getTrackCreationType() {
		return this.trackCreationType;
	}

	public void setTrackCreationType(String trackCreationType) {
		this.trackCreationType = trackCreationType;
	}

	public Date getTrackDateCreated() {
		return this.trackDateCreated;
	}

	public void setTrackDateCreated(Date trackDateCreated) {
		this.trackDateCreated = trackDateCreated;
	}

	public Date getTrackDateUpdated() {
		return this.trackDateUpdated;
	}

	public void setTrackDateUpdated(Date trackDateUpdated) {
		this.trackDateUpdated = trackDateUpdated;
	}

	public String getTrackDescription() {
		return this.trackDescription;
	}

	public void setTrackDescription(String trackDescription) {
		this.trackDescription = trackDescription;
	}

	public String getTrackDuration() {
		return this.trackDuration;
	}

	public void setTrackDuration(String trackDuration) {
		this.trackDuration = trackDuration;
	}

	public String getTrackEndAddress() {
		return this.trackEndAddress;
	}

	public void setTrackEndAddress(String trackEndAddress) {
		this.trackEndAddress = trackEndAddress;
	}

	public Date getTrackEnddate() {
		return this.trackEnddate;
	}

	public void setTrackEnddate(Date trackEnddate) {
		this.trackEnddate = trackEnddate;
	}

	public String getTrackFile() {
		return this.trackFile;
	}

	public void setTrackFile(String trackFile) {
		this.trackFile = trackFile;
	}

	public String getTrackHeightDiff() {
		return this.trackHeightDiff;
	}

	public void setTrackHeightDiff(String trackHeightDiff) {
		this.trackHeightDiff = trackHeightDiff;
	}

	public String getTrackLengthKm() {
		return this.trackLengthKm;
	}

	public void setTrackLengthKm(String trackLengthKm) {
		this.trackLengthKm = trackLengthKm;
	}

	public String getTrackMaxElevation() {
		return this.trackMaxElevation;
	}

	public void setTrackMaxElevation(String trackMaxElevation) {
		this.trackMaxElevation = trackMaxElevation;
	}

	public String getTrackMinElevation() {
		return this.trackMinElevation;
	}

	public void setTrackMinElevation(String trackMinElevation) {
		this.trackMinElevation = trackMinElevation;
	}

	public String getTrackName() {
		return this.trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getTrackStartAddress() {
		return this.trackStartAddress;
	}

	public void setTrackStartAddress(String trackStartAddress) {
		this.trackStartAddress = trackStartAddress;
	}

	public Date getTrackStartdate() {
		return this.trackStartdate;
	}

	public void setTrackStartdate(Date trackStartdate) {
		this.trackStartdate = trackStartdate;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}