package sk.mlp.database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.common.collect.Iterables;

import sk.mlp.logger.FileLogger;
import sk.mlp.ui.model.Track;
import sk.mlp.ui.model.User;
import sk.mlp.util.Constants;

public class DatabaseServices {

	private static final String PERSISTENCE_UNIT_NAME = "GPSWebApp";
	private static EntityManagerFactory entityManagerFactory;
	
	private static final String DEFAULT_HASH_ALGORITHM = "MD5";

	public User findUserById(Integer id) {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager.find(User.class, id.longValue());
	}

	public void createNewUser(String email, String firstName, String lastName, String age, String activity,
			String password, String userToken) {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();

		User user = new User();
		user.setUserEmail(email);
		user.setUserFirstName(firstName);
		user.setUserLastName(lastName);
		user.setUserAge(Integer.valueOf(age));
		user.setUserActivity(activity);
		user.setUserPass(password);
		user.setUserToken(userToken);

		entityManager.persist(user);

		entityManager.getTransaction().commit();
	}
	
	public void createNewUser(User user) {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();
		String hashedPassword = hashPassword(DEFAULT_HASH_ALGORITHM, user.getUserPass());
		user.setUserPass(hashedPassword);
		entityManager.persist(user);

		entityManager.getTransaction().commit();
	}

	public boolean deleteUser(long userId) {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			User user = entityManager.find(User.class, userId);
			entityManager.remove(user);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	public User findUserByEmail(String email) {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<User> users = entityManager.createQuery("SELECT c FROM User c WHERE c.userEmail LIKE :email")
				.setParameter("email", email).getResultList();
		if (users.size() == 1) {
			return Iterables.getOnlyElement(users);
		} else {
			return null;
		}
	}

	public boolean isCorrectLogin(String username, String userpass) throws Exception {
		User user = findUserByEmail(username);
		if (user!=null && user.getUserPass().equals(userpass)) {
			return true;
		} else {
			return false;
		}
	}

	public int updateUserData(String currentEmail, String newEmail, String firstName, String lastName, String activity,
			String oldPassword, String newPassword, int age) {
		try {
			DatabaseServices databaseServices = new DatabaseServices();
			User user = databaseServices.findUserByEmail(newEmail);
			boolean existing = user != null;
			if (oldPassword.equals(user.getUserPass())) {
				if (!existing || currentEmail.equals(newEmail)) {

					entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
					EntityManager entityManager = entityManagerFactory.createEntityManager();

					entityManager.getTransaction().begin();

					user.setUserEmail(newEmail);
					user.setUserFirstName(firstName);
					user.setUserLastName(lastName);
					user.setUserActivity(activity);
					user.setUserAge(age);
					user.setUserPass(newPassword);
					entityManager.merge(user);

					entityManager.getTransaction().commit();
					FileLogger.getInstance().createNewLog("Successfuly updated user data for old user " + currentEmail
							+ " to new email " + newEmail + ".");
					return 0;
				} else {
					FileLogger.getInstance().createNewLog("ERROR: User " + user.getUserEmail()
							+ " has entered existing new email!!! Cannot update user data!!!");
					return 1;
				}
			} else {
				FileLogger.getInstance().createNewLog("ERROR: User " + user.getUserEmail()
						+ " has entered wrong password!!! Cannot update user data!!!");
				return 2;
			}

		} catch (Exception ex) {
			FileLogger.getInstance().createNewLog("ERROR: Cannot connect to DB in DBLoginUpdater!!!");
			return -1;
		}
	}

	public boolean acceptUser(String email, String token) {
		try {
			DatabaseServices databaseServices = new DatabaseServices();
			User user = databaseServices.findUserByEmail(email);
			String userToken = user.getUserToken();
			boolean isUserAccepted = user.getUserAccepted();

			if (isUserAccepted != true && userToken.equals(token)) {
				entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
				EntityManager entityManager = entityManagerFactory.createEntityManager();

				entityManager.getTransaction().begin();

				user.setUserAccepted(true);
				entityManager.merge(user);
				entityManager.getTransaction().commit();
				FileLogger.getInstance().createNewLog("Successfuly ACCEPTED user " + email + ".");
				return true;
			} else {
				FileLogger.getInstance()
						.createNewLog("ERROR: Cannot ACCEPT user " + email + " with userToken " + token + ".");
				return false;
			}
		} catch (Exception ex) {
			FileLogger.getInstance()
					.createNewLog("ERROR: Cannot ACCEPT user " + email + " with userToken " + token + ".");
			return false;
		}
	}

	public void createNewTrack(String trackName, String trackDescr, String trackActivity, String trackPath, int userID,
			Date startDate, Date endDate, String access, String startAddress, String endAddress, String length,
			String minElevation, String maxElevation, String heightDiff, String duration, String creationType) {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			Date date = new Date();
			String filePath = trackPath;
			if (Constants.OPEARTION_SYSTEM.startsWith("Windows")) {
				filePath = trackPath.replaceAll("\\\\", "/");
			}

			entityManager.getTransaction().begin();
			Track track = new Track();
			track.setTrackAccess(access);
			track.setTrackActivity(trackActivity);
			track.setTrackCreationType(creationType);
			track.setTrackDateCreated(date);
			track.setTrackDateUpdated(date);
			track.setTrackDescription(trackDescr);
			track.setTrackDuration(duration);
			track.setTrackEndAddress(endAddress);
			track.setTrackStartAddress(startAddress);
			track.setTrackEnddate(endDate);
			track.setTrackStartdate(startDate);
			track.setTrackFile(filePath);
			track.setTrackHeightDiff(heightDiff);
			track.setTrackLengthKm(length);
			track.setTrackMaxElevation(maxElevation);
			track.setTrackMinElevation(minElevation);
			track.setTrackName(trackName);
			track.setUser(findUserById(userID));
			entityManager.persist(track);

			entityManager.getTransaction().commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Nezapisal som do DB!!!");
			FileLogger.getInstance()
					.createNewLog("ERROR: Cannot create track " + trackName + " !!! The user ID is " + userID + " !!!");
		}
	}

	public List<Track> getUserTracks(String email) {
		return findUserByEmail(email).getTracks();
	}
	
	public Track findTrackById(long id) {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<Track> tracks = entityManager.createQuery("SELECT t FROM Track t WHERE t.id LIKE :id")
				.setParameter("id", id).getResultList();
		if (tracks.size() == 1) {
			return Iterables.getOnlyElement(tracks);
		} else {
			return null;
		}
	}
	

   public List<Track> findPattern(String pattern){
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<Track> tracks = entityManager.createQuery("SELECT t from Track t FULL JOIN t.user u where t.trackAccess='Public' AND(u.userEmail like :pattern OR t.trackName like :pattern OR t.trackDescription like :pattern OR t.trackActivity like :pattern OR t.trackStartAddress like :pattern OR t.trackEndAddress like :pattern)")
				.setParameter("pattern", "%" + pattern + "%").getResultList();
		return tracks;
   }
   

   public List<Track> findNewNTracks(int rowCount){
	   entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<Track> tracks = entityManager.createQuery("SELECT t from Track t WHERE t.trackAccess ='Public' order by t.trackDateCreated").setMaxResults(rowCount).getResultList();
       return tracks;
   }
   
	public boolean deleteTrack(long trackId) {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			Track track = entityManager.find(Track.class, trackId);
			entityManager.remove(track);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			return false;
		}
		return true;

	}
	
	private String hashPassword (String algorithm, String password ) {
		
        MessageDigest md;
        byte byteData[] = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byteData = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return sb.toString();	
	}

}
