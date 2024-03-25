package pl.com.coders.shop2.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartItemDTO {

    private String productName;
    private int quantity;
    private BigDecimal totalPrice;
    private int cartIndex;

}
