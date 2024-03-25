package pl.com.coders.shop2.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.com.coders.shop2.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = {"pl.com.coders.shop2.repository"})
public class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private User user;
    private Cart cart;
    private Category category;
    private Product product;


    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("test@email.com")
                .firstName("testowy")
                .lastName("testowe")
                .password("test")
                .build();
        user = userRepository.create(user);
        cart = cartRepository.createCart(user);
        category = Category.builder()
                .name(CategoryType.ELEKTRONIKA.name())
                .build();
        categoryRepository.add(category);
        product = Product.builder()
                .name("Sample Product")
                .description("Sample Description")
                .price(BigDecimal.valueOf(19.99))
                .quantity(10)
                .category(category)
                .build();
        productRepository.add(product);
    }

    @Test
    void updateCartItem() {
        //given
        CartItem cartItem = CartItem.builder()
                .cartItemId(new CartItem.CartItemId(cart,product))
                .totalPrice(BigDecimal.valueOf(10.00))
                .quantity(5)
                .build();
        //when
        CartItem updatedCartItem = cartItemRepository.updateCartItem(cartItem);
        //then
        assertEquals(cartItem,updatedCartItem);
    }

    @Test
    void deleteCartItem() {
        //given
        CartItem cartItem = CartItem.builder()
                .cartItemId(new CartItem.CartItemId(cart,product))
                .totalPrice(BigDecimal.valueOf(10.00))
                .quantity(5)
                .build();
        cartItem = cartItemRepository.updateCartItem(cartItem);
        //when
        Boolean wasDeleted = cartItemRepository.deleteCartItem(cartItem);
        //then
        assertTrue(wasDeleted);
    }

}
