package com.roman14.springbootjpa.api;

import com.roman14.springbootjpa.api.response.CommonResponse;
import com.roman14.springbootjpa.entity.Address;
import com.roman14.springbootjpa.entity.Order;
import com.roman14.springbootjpa.entity.OrderItem;
import com.roman14.springbootjpa.entity.OrderStatus;
import com.roman14.springbootjpa.repository.OrderRepository;
import com.roman14.springbootjpa.repository.OrderSearch;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderApiController
{
  private final OrderRepository orderRepository;

  @GetMapping("/v1/list")
  public CommonResponse<List<Order>> ordersV1()
  {
    List<Order> orderList = orderRepository.findAllByString(new OrderSearch());

    orderList.stream()
      .forEach(order -> {
        order.getMember().getName();
        order.getDelivery().getAddress();
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.stream()
          .forEach(orderItem -> {
            orderItem.getItem().getName();
          });
      });

    return CommonResponse.success(orderList);
  }

  @GetMapping("/v2/list")
  public CommonResponse<List<OrderDto>> ordersV2()
  {
    List<Order> orderList = orderRepository.findAllByString(new OrderSearch());

    List<OrderDto> results = orderList.stream()
      .map(OrderDto::from)
      .collect(Collectors.toList());

    return CommonResponse.success(results);
  }

  @Getter @Builder
  static class OrderDto
  {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public static OrderDto from(Order order)
    {
      return OrderDto.builder()
        .orderId(order.getId())
        .name(order.getMember().getName())
        .orderDate(order.getOrderDate())
        .orderStatus(order.getStatus())
        .address(order.getDelivery().getAddress())
        .orderItems(order.getOrderItems().stream()
          .map(OrderItemDto::from)
          .collect(Collectors.toList()))
        .build();
    }
  }

  @Getter @Builder
  static class OrderItemDto
  {
    private String itemName;
    private int orderPrice;
    private int count;

    public static OrderItemDto from(OrderItem orderItem)
    {
      return OrderItemDto.builder()
        .itemName(orderItem.getItem().getName())
        .orderPrice(orderItem.getOrderPrice())
        .count(orderItem.getCount())
        .build();
    }
  }
}
