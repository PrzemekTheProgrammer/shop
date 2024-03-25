package pl.com.coders.shop2.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.com.coders.shop2.domain.Cart;
import pl.com.coders.shop2.domain.Order;
import pl.com.coders.shop2.domain.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = {"pl.com.coders.shop2.repository"})
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createOrderFromCartTest() {
        //given
        User user = createSampleUser("Testowe", "Testowy", "pass");
        Cart cart = Cart.builder()
                .user(user)
                .totalPrice(BigDecimal.valueOf(0.00))
                .build();
        //when
        Order receivedOrder = orderRepository.createOrderFromCart(cart);
        //then
        assertEquals(user,receivedOrder.getUser());
        assertEquals(BigDecimal.valueOf(0.00),receivedOrder.getTotalPrice());
    }

    @Test
    void getUserOrdersByUserIdTest() {
        //given
        User user = createSampleUser("Testowe", "Testowy", "pass");
        User user2 = createSampleUser("Testowe2", "Testowy2", "pass2");
        Cart cart = Cart.builder()
                .user(user)
                .totalPrice(BigDecimal.valueOf(0.00))
                .build();
        Cart cart2 = Cart.builder()
                .user(user2)
                .totalPrice(BigDecimal.valueOf(0.00))
                .build();
        Order order1 = orderRepository.createOrderFromCart(cart);
        Order order2 = orderRepository.createOrderFromCart(cart);
        Order order3 = orderRepository.createOrderFromCart(cart2);
        //when
        List<Order> orderList = orderRepository.getUserOrdersByUserId(user.getId());
        //then
        assertEquals(2,orderList.size());
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
