package pl.com.coders.shop2.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderDTO {
    private String userName;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> orderItems;
}
