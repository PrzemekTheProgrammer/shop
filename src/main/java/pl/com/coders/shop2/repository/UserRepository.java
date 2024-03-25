package pl.com.coders.shop2.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public User create(User user) {
        return entityManager.merge(user);

    }

    public User getByEmail(String email) {
        return entityManager.createQuery("Select u FROM User u WHERE u.email =: email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

}
