package pl.com.coders.shop2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.Exceptions.CartNotExistsException;
import pl.com.coders.shop2.domain.*;
import pl.com.coders.shop2.dto.OrderDTO;
import pl.com.coders.shop2.mapper.OrderMapper;
import pl.com.coders.shop2.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;


    @Transactional
    public OrderDTO createOrderByEmail(String userEmail) {
        User user = userRepository.getByEmail(userEmail);
        Optional<Cart> optionalCart = cartRepository.getCartForUser(user);
        if (ifCartIsPresentAndHasItems(optionalCart)) {
            Cart cart = optionalCart.get();
            Order order = orderRepository.createOrderFromCart(cart);
            List<OrderItem> orderItems = createOrderItemsFromCartItems(order, cart.getCartItems());
            deleteCart(cart);
            order.setOrderItems(orderItems);
            return OrderMapper.getInstance().toDTO(order);
        } else {
            throw new CartNotExistsException("Nie można utworzyć zamówienia ponieważ koszyk jest pusty");
        }
    }

    private void deleteCart(Cart cart) {
        cart.getCartItems().forEach(cartItemRepository::deleteCartItem);
        cartRepository.deleteCart(cart);
    }

    private List<OrderItem> createOrderItemsFromCartItems(Order order, List<CartItem> cartItems) {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            substractProductQuantity(cartItem.getCartItemId().getProduct(), cartItem.getQuantity());
            OrderItem orderItemToSave = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getCartItemId().getProduct())
                    .quantity(cartItem.getQuantity())
                    .totalPrice(cartItem.getTotalPrice())
                    .build();
            orderItemRepository.createOrderItem(orderItemToSave);
            orderItems.add(orderItemToSave);

        }
        return orderItems;
    }

    private void substractProductQuantity(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.createOrUpdate(product);
    }

    private boolean ifCartIsPresentAndHasItems(Optional<Cart> optionalCart) {
        return optionalCart.filter(cart -> cart.getCartItems().size() > 0).isPresent();
    }

    public List<OrderDTO> getOrdersByEmail(String userEmail) {
        User user = userRepository.getByEmail(userEmail);
        return orderRepository.getUserOrdersByUserId(user.getId())
                .stream()
                .map(order -> OrderMapper.getInstance().toDTO(order))
                .toList();
    }
}
