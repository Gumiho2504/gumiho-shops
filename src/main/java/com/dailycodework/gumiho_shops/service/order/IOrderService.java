package com.dailycodework.gumiho_shops.service.order;

import java.util.List;

import com.dailycodework.gumiho_shops.dto.OrderDto;
import com.dailycodework.gumiho_shops.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
