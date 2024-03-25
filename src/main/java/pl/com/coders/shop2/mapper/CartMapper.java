package pl.com.coders.shop2.mapper;

import pl.com.coders.shop2.domain.Cart;
import pl.com.coders.shop2.dto.CartDTO;

public class CartMapper implements IMapper<Cart, CartDTO> {
    private static CartMapper instance = new CartMapper();

    private CartMapper() {
    }

    public static CartMapper getInstance() {
        return instance;
    }

    @Override
    public Cart toEntity(CartDTO cartDTO) {
        return null;
    }

    @Override
    public CartDTO toDTO(Cart cart) {
        return CartDTO.builder()
                .totalPrice(cart.getTotalPrice())
                .userName(cart.getUser().getFirstName() + " " + cart.getUser().getLastName())
                .created(cart.getCreated())
                .updated(cart.getUpdated())
                .cartItems(cart.getCartItems().stream().map(cartItem -> CartItemMapper.getInstance().toDTO(cartItem)).toList())
                .build();
    }
}
