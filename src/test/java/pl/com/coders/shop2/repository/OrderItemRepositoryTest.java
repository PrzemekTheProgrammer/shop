package pl.com.coders.shop2.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.com.coders.shop2.domain.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = {"pl.com.coders.shop2.repository"})
public class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void createOrderItemTest() {
        //given
        Category category = createSampleCategory(CategoryType.ELEKTRONIKA.name());
        Product product = createSampleProduct("Testowy produkt", category);
        User user = createSampleUser("Testowe", "Testowy", "pass");
        Order order = createSampleOrder(user);
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .totalPrice(BigDecimal.valueOf(0.00))
                .quantity(10)
                .build();
        //when
        orderItem = orderItemRepository.createOrderItem(orderItem);
        //then
        assertEquals(order, orderItem.getOrder());
        assertEquals(product, orderItem.getProduct());
        assertEquals(BigDecimal.valueOf(0.00), orderItem.getTotalPrice());
        assertEquals(10, orderItem.getQuantity());
    }

    private Category createSampleCategory(String name) {
        return categoryRepository.add(Category.builder()
                .name(name)
                .build());
    }

    private Product createSampleProduct(String productName, Category category) {
        return productRepository.add(Product.builder()
                .name(productName)
                .quantity(10)
                .price(BigDecimal.valueOf(49.99))
                .description("Description")
                .category(category)
                .build());
    }

    private Order createSampleOrder(User user) {
        return orderRepository.createOrderFromCart(Cart.builder()
                .user(user)
                .totalPrice(BigDecimal.valueOf(0.00))
                .build());
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
