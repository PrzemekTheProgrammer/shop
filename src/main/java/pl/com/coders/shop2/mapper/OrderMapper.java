package pl.com.coders.shop2.mapper;

import pl.com.coders.shop2.domain.Order;
import pl.com.coders.shop2.dto.OrderDTO;

public class OrderMapper implements IMapper<Order, OrderDTO> {
    private static OrderMapper instance = new OrderMapper();

    private OrderMapper() {
    }

    public static OrderMapper getInstance() {
        return instance;
    }

    @Override
    public Order toEntity(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .totalPrice(order.getTotalPrice())
                .userName(order.getUser().getFirstName() + " " + order.getUser().getLastName())
                .orderItems(order.getOrderItems().stream().map(
                        orderItem -> OrderItemMapper.getInstance().toDTO(orderItem)
                ).toList())
                .build();
    }
}
