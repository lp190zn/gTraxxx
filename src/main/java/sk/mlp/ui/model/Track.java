package sk.mlp.ui.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
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
	@Column(name="IDENT")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "trackGen")
    @SequenceGenerator(name = "trackGen", sequenceName = "GTRAXXX.TRACKS_SEQ", allocationSize=1)
	private long ident;

	@Column(name="\"ACCESS\"")
	private String access;

	@Column(name="ACTIVITY")
	private String activity;

	@Column(name="CREATED")
	@Temporal(TemporalType.DATE)
	private Date created;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="DURATION")
	private String duration;

	@Column(name="END_ADDRESS")
	private String endAddress;

	@Column(name="ENDDATE")
	@Temporal(TemporalType.DATE)
	private Date enddate;

	@Column(name="\"FILE\"")
	private String file;

	@Column(name="LENGTH_KM")
	private BigDecimal lengthKm;

	@Column(name="MAX_ELEVATION")
	private BigDecimal maxElevation;

	@Column(name="MIN_ELEVATION")
	private BigDecimal minElevation;

	@Column(name="NAME")
	private String name;

	@Column(name="START_ADDRESS")
	private String startAddress;

	@Column(name="STARTDATE")
	@Temporal(TemporalType.DATE)
	private Date startdate;

	@Column(name="HEIGHT_DIFF")
	private BigDecimal heightDiff;

	@Column(name="TYPE")
	private String type;

	@Column(name="UPDATED")
	@Temporal(TemporalType.DATE)
	private Date updated;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;

	public Track() {
	}

	public long getIdent() {
		return this.ident;
	}

	public void setIdent(long ident) {
		this.ident = ident;
	}

	public String getAccess() {
		return this.access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getActivity() {
		return this.activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getEndAddress() {
		return this.endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public BigDecimal getLengthKm() {
		return this.lengthKm;
	}

	public void setLengthKm(BigDecimal lengthKm) {
		this.lengthKm = lengthKm;
	}

	public BigDecimal getMaxElevation() {
		return this.maxElevation;
	}

	public void setMaxElevation(BigDecimal maxElevation) {
		this.maxElevation = maxElevation;
	}

	public BigDecimal getMinElevation() {
		return this.minElevation;
	}

	public void setMinElevation(BigDecimal minElevation) {
		this.minElevation = minElevation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartAddress() {
		return this.startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public BigDecimal getHeightDiff() {
		return this.heightDiff;
	}

	public void setHeightDiff(BigDecimal trackHeightDiff) {
		this.heightDiff = trackHeightDiff;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}