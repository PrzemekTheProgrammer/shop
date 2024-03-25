package pl.com.coders.shop2.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CartDTO {
    private String userName;
    private BigDecimal totalPrice;
    private List<CartItemDTO> cartItems;
    private LocalDateTime created;
    private LocalDateTime updated;
}
