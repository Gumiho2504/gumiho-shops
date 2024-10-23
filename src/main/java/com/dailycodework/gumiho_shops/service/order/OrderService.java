package com.dailycodework.gumiho_shops.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dailycodework.gumiho_shops.dto.OrderDto;
import com.dailycodework.gumiho_shops.enums.OrderStatus;
import com.dailycodework.gumiho_shops.exception.ResourceNotFoundException;
import com.dailycodework.gumiho_shops.model.Cart;
import com.dailycodework.gumiho_shops.model.Order;
import com.dailycodework.gumiho_shops.model.OrderItem;
import com.dailycodework.gumiho_shops.model.Product;
import com.dailycodework.gumiho_shops.repository.OrderRepository;
import com.dailycodework.gumiho_shops.repository.ProductRepository;
import com.dailycodework.gumiho_shops.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {

        Cart cart = cartService.getCartByUserId(userId);

        Order order = creatOrder(cart);

        List<OrderItem> orderItems = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItems));

        order.setOrderTotalAmount(caculateTotalAmount(orderItems));

        Order saveOrder = orderRepository.save(order);

        cartService.cleanCart(cart.getId());

        return saveOrder;
    }

    private Order creatOrder(Cart cart) {
        Order order = new Order();

        // set the user
        order.setUser(cart.getUser());

        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());

        return order;

    }

    private BigDecimal caculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream()
                .map(item -> {
                    Product product = item.getProduct();
                    product.setInventory(product.getInventory() - item.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order, product, item.getUnitPrice(), item.getQuantity());
                }).toList();
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream().map(this::convertToDto).toList();

    }

    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

}
