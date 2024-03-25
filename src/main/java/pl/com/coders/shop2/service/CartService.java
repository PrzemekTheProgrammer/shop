package pl.com.coders.shop2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.Exceptions.CartNotExistsException;
import pl.com.coders.shop2.Exceptions.NotEnoughProductsException;
import pl.com.coders.shop2.domain.Cart;
import pl.com.coders.shop2.domain.CartItem;
import pl.com.coders.shop2.domain.Product;
import pl.com.coders.shop2.domain.User;
import pl.com.coders.shop2.dto.CartDTO;
import pl.com.coders.shop2.mapper.CartMapper;
import pl.com.coders.shop2.repository.CartItemRepository;
import pl.com.coders.shop2.repository.CartRepository;
import pl.com.coders.shop2.repository.ProductRepository;
import pl.com.coders.shop2.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public CartDTO addToCart(String productTitle, int productAmount, String userEmail) {
        User activeUser = userRepository.getByEmail(userEmail);
        Product item = productRepository.getByName(productTitle);
        Cart cart = createUpdateCartForUser(activeUser, item, productAmount);
        CartItem cartItem = createUpdateCardItem(item, cart, productAmount);
        return CartMapper.getInstance().toDTO(cart);
    }

    @Transactional
    public Boolean deleteCartItemByIndex(Integer index, String userEmail) {
        User activeUser = userRepository.getByEmail(userEmail);
        Optional<Cart> cart = cartRepository.getCartForUser(activeUser);
        if (cart.isPresent()) {
            Cart receivedCart = cart.get();
            CartItem cartItem = searchForCartItemWithIndex(receivedCart, index);
            updateCartAfterItemDelete(receivedCart, cartItem);
            updateCartItemIndexAfterDelete(receivedCart.getCartItems(), index);
            cartItemRepository.deleteCartItem(cartItem);
        }
        return true;
    }

    private void updateCartItemIndexAfterDelete(List<CartItem> cartItems, Integer fromIndex) {
        cartItems.stream()
                .filter(cartItem -> cartItem.getCartIndex() > fromIndex)
                .toList()
                .forEach(cartItem -> {
                    cartItem.setCartIndex(cartItem.getCartIndex() - 1);
                    cartItemRepository.updateCartItem(cartItem);
                });
    }

    private void updateCartAfterItemDelete(Cart cart, CartItem deletedCartItem) {
        cart.setTotalPrice(cart.getTotalPrice().subtract(deletedCartItem.getTotalPrice()));
        cartRepository.updateCart(cart);
    }

    private CartItem searchForCartItemWithIndex(Cart cart, Integer index) {
        CartItem cItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getCartIndex() == index)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nie ma w koszyku produktu o takim indeksie"));
        cart.getCartItems().remove(cItem);
        return cItem;
    }

    private CartItem createUpdateCardItem(Product item, Cart cart, int productAmount) {
        CartItem cartItem = cart.getCartItems().stream()
                .filter(cItem -> cItem.getCartItemId()
                        .getProduct().getName().equals(item.getName()))
                .findAny()
                .orElseGet(() -> {
                    CartItem createdCartItem = CartItem
                            .builder()
                            .cartItemId(new CartItem.CartItemId(cart, item))
                            .quantity(0)
                            .totalPrice(BigDecimal.valueOf(0))
                            .cartIndex(cart.getCartItems().size() + 1)
                            .build();
                    cart.getCartItems().add(createdCartItem);
                    return createdCartItem;
                });
        cartItem.setQuantity(cartItem.getQuantity() + productAmount);
        if (item.getQuantity() < cartItem.getQuantity()) {
            throw new NotEnoughProductsException("Żądana ilość produktów ponad stan.");
        }
        cartItem.setTotalPrice(item.getPrice().
                multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        cartItemRepository.updateCartItem(cartItem);
        return cartItem;
    }

    private Cart createUpdateCartForUser(User user, Product item, int productAmount) {
        Cart cart = cartRepository.getCartForUser(user).orElseGet(() -> cartRepository.createCart(user));
        cart.setTotalPrice(cart.getTotalPrice().add(item.getPrice().multiply(BigDecimal.valueOf(productAmount))));
        cart = cartRepository.updateCart(cart);
        return cart;
    }

    public CartDTO getCartByUserEmail(String userEmail) {
        User user = userRepository.getByEmail(userEmail);
        Optional<Cart> optionalCart = cartRepository.getCartForUser(user);
        if (optionalCart.isPresent()) {
            return CartMapper.getInstance().toDTO(optionalCart.get());
        } else {
            throw new CartNotExistsException("Koszyk dla zadanego użytkownika nie istnieje.");
        }
    }
}
