package pl.com.coders.shop2.mapper;

import pl.com.coders.shop2.domain.CartItem;
import pl.com.coders.shop2.dto.CartItemDTO;

public class CartItemMapper implements IMapper<CartItem, CartItemDTO> {
    private static CartItemMapper instance = new CartItemMapper();

    private CartItemMapper() {
    }

    public static CartItemMapper getInstance() {
        return instance;
    }


    @Override
    public CartItem toEntity(CartItemDTO cartItemDTO) {
        return null;
    }

    @Override
    public CartItemDTO toDTO(CartItem cartItem) {
        return CartItemDTO.builder()
                .cartIndex(cartItem.getCartIndex())
                .productName(cartItem.getCartItemId().getProduct().getName())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .build();
    }
}
