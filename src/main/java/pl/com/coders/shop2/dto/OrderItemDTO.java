package pl.com.coders.shop2.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDTO {

    private String productName;
    private int quantity;
    private BigDecimal totalPrice;

}
