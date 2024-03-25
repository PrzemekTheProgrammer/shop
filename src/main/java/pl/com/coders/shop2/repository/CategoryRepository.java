package pl.com.coders.shop2.repository;

import org.springframework.stereotype.Repository;
import pl.com.coders.shop2.domain.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CategoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Category getByName(String name) {
        return entityManager
                .createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Category add(Category category) {
        category.setCreated(LocalDateTime.now());
        category.setUpdated(LocalDateTime.now());
        entityManager.persist(category);
        return category;
    }

    public List<Category> getCategories() {

        return entityManager.createQuery("Select c from Category c", Category.class)
                .getResultList();

    }

}
