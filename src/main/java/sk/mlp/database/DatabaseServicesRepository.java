package sk.mlp.database;


import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;

import sk.mlp.ui.model.User;
import sk.mlp.util.Constants;

@Repository
@Transactional
public class DatabaseServicesRepository implements Serializable {
    private static final long serialVersionUID = 324589274837L;

    @PersistenceContext
    private EntityManager em;
    
	public User findUserByEmail(String email) {
		List<User> users = em.createQuery("SELECT c FROM User c WHERE c.email LIKE :email")
				.setParameter("email", email).getResultList();
		if (users.size() == 1) {
			return Iterables.getOnlyElement(users);
		} else {
			return null;
		}
	}
    
    
}