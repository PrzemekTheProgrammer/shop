package pl.com.coders.shop2.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.domain.Cart;
import pl.com.coders.shop2.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public class CartRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Cart createCart(User user) {
        Cart newCart = Cart.builder()
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .user(user)
                .totalPrice(BigDecimal.valueOf(0))
                .cartItems(new ArrayList<>())
                .build();
        entityManager.persist(newCart);
        return newCart;
    }

    public Optional<Cart> getCartForUser(User user) {
        return entityManager.createQuery("SELECT c FROM Cart c JOIN FETCH c.user u WHERE user_id =: userId", Cart.class)
                .setParameter("userId", user.getId())
                .getResultList().stream().findFirst();
    }

    @Transactional
    public Cart updateCart(Cart cart) {
        return entityManager.merge(cart);
    }

    @Transactional
    public Boolean deleteCart(Cart cart) {
        entityManager.createQuery("DELETE FROM Cart c WHERE c.id =: cartId")
                .setParameter("cartId", cart.getId())
                .executeUpdate();
        return true;
    }
}
