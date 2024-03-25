package pl.com.coders.shop2.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderItemRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public OrderItem createOrderItem(OrderItem orderItemToSave) {
        entityManager.persist(orderItemToSave);
        return orderItemToSave;
    }
}
