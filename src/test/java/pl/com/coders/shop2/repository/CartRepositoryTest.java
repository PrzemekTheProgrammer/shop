package pl.com.coders.shop2.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.com.coders.shop2.domain.Cart;
import pl.com.coders.shop2.domain.User;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = {"pl.com.coders.shop2.repository"})
public class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void createCartTest() {
        //given
        User user = createSampleUser("testowe", "testowy", "test");
        //when
        Cart cart = cartRepository.createCart(user);
        //then
        assertEquals(user, cart.getUser());
    }

    @Test
    void getCartForUserTestPositive() {
        //given
        User user = createSampleUser("testowe", "testowy", "test");
        Cart cart = cartRepository.createCart(user);
        //when
        Optional<Cart> optionalCart = cartRepository.getCartForUser(user);
        //then
        assertTrue(optionalCart.isPresent());
    }

    @Test
    void getCartForUserTestNegative() {
        //given
        User user = createSampleUser("testowe", "testowy", "test");
        //when
        Optional<Cart> optionalCart = cartRepository.getCartForUser(user);
        //then
        assertTrue(optionalCart.isEmpty());
    }

    @Test
    void updateCartTest() {
        //given
        User user = createSampleUser("testowe", "testowy", "test");
        Cart cart = cartRepository.createCart(user);
        //when
        cart.setTotalPrice(BigDecimal.valueOf(50.49));
        Cart updatedCart = cartRepository.updateCart(cart);
        //then
        assertEquals(BigDecimal.valueOf(50.49), updatedCart.getTotalPrice());
    }

    @Test
    void deleteCartTest() {
        //given
        User user = createSampleUser("testowe", "testowy", "test");
        Cart cart = cartRepository.createCart(user);
        //when
        Boolean isDeleted = cartRepository.deleteCart(cart);
        //then
        assertTrue(isDeleted);
    }

    private User createSampleUser(String firstName, String lastName, String pass) {
        User user = User.builder()
                .password(pass)
                .lastName(lastName)
                .firstName(firstName)
                .build();
        user = userRepository.create(user);
        return user;
    }

}
