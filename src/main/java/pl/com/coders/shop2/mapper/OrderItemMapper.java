package pl.com.coders.shop2.mapper;

import pl.com.coders.shop2.domain.OrderItem;
import pl.com.coders.shop2.dto.OrderItemDTO;

public class OrderItemMapper implements IMapper<OrderItem, OrderItemDTO> {
    private static OrderItemMapper instance = new OrderItemMapper();

    private OrderItemMapper() {
    }

    public static OrderItemMapper getInstance() {
        return instance;
    }

    @Override
    public OrderItem toEntity(OrderItemDTO orderItemDTO) {
        return null;
    }

    @Override
    public OrderItemDTO toDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .totalPrice(orderItem.getTotalPrice())
                .productName(orderItem.getProduct().getName())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
