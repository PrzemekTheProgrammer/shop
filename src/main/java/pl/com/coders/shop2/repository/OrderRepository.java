package pl.com.coders.shop2.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.domain.Cart;
import pl.com.coders.shop2.domain.Order;
import pl.com.coders.shop2.domain.OrderItem;
import pl.com.coders.shop2.dto.OrderDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Order createOrderFromCart(Cart cart) {
        Order orderToSave = Order.builder()
                .totalPrice(cart.getTotalPrice())
                .user(cart.getUser())
                .orderItems(new ArrayList<>())
                .build();
        entityManager.persist(orderToSave);
        return orderToSave;
    }

    public List<Order> getUserOrdersByUserId(Long userId) {
        return entityManager.createQuery("SELECT DISTINCT o FROM Order o JOIN FETCH o.user u LEFT JOIN o.orderItems WHERE user_id =: userId", Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
