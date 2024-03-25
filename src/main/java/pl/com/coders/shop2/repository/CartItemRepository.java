package pl.com.coders.shop2.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.domain.CartItem;
import pl.com.coders.shop2.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CartItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public CartItem updateCartItem(CartItem cartItem) {
        return entityManager.merge(cartItem);
    }

    @Transactional
    public Boolean deleteCartItem(CartItem toDelete) {
        CartItem forDelete = entityManager.createQuery("SELECT ci FROM CartItem ci WHERE cart_id =: cartId AND product_id =: productId", CartItem.class)
                .setParameter("cartId", toDelete.getCartItemId().getCart().getId())
                .setParameter("productId", toDelete.getCartItemId().getProduct().getId())
                .getSingleResult();
        entityManager.remove(forDelete);
        return true;
    }
}
