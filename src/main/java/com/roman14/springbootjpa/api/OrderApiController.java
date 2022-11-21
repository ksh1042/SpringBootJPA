package com.roman14.springbootjpa.api;

import com.roman14.springbootjpa.api.response.CommonResponse;
import com.roman14.springbootjpa.entity.Address;
import com.roman14.springbootjpa.entity.Order;
import com.roman14.springbootjpa.entity.OrderItem;
import com.roman14.springbootjpa.entity.OrderStatus;
import com.roman14.springbootjpa.repository.OrderRepository;
import com.roman14.springbootjpa.repository.OrderSearch;
import com.roman14.springbootjpa.repository.order.query.OrderFlatDto;
import com.roman14.springbootjpa.repository.order.query.OrderQueryDto;
import com.roman14.springbootjpa.repository.order.query.OrderQueryRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  private final OrderQueryRepository orderQueryRepository;

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

  @GetMapping("/v3/list")
  public CommonResponse<List<OrderDto>> ordersV3()
  {
    List<Order> orderList = orderRepository.findAllWithItems();

    List<OrderDto> results = orderList.stream()
      .map(OrderDto::from)
      .collect(Collectors.toList());

    return CommonResponse.success(results);
  }

  @GetMapping("/v3.1/list")
  public CommonResponse<List<OrderDto>> ordersV31(
    @RequestParam(value = "offset", defaultValue = "0") int offset,
    @RequestParam(value = "limit", defaultValue = "0") int limit
  )
  {
    List<Order> orderList = orderRepository.findAllWithMemberDelivery(offset, limit);

    List<OrderDto> results = orderList.stream()
      .map(OrderDto::from)
      .collect(Collectors.toList());

    return CommonResponse.success(results);
  }

  @GetMapping("/v4/list")
  public CommonResponse<List<OrderQueryDto>> ordersV4()
  {
    return CommonResponse.success(orderQueryRepository.findOrderQueryDtos());
  }

  @GetMapping("/v5/list")
  public CommonResponse<List<OrderQueryDto>> ordersV5()
  {
    return CommonResponse.success(orderQueryRepository.findAllByDtos());
  }

  @GetMapping("/v6/list")
  public CommonResponse<List<OrderFlatDto>> ordersV6()
  {
    // 페이징 가능하나, 페이징이 order 기준이 아닌 orderItem 기준으로 되는 문제가 발생
    return CommonResponse.success(orderQueryRepository.findAllByDtosFlat());
  }

  @Getter
  @Builder
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

  @Getter
  @Builder
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
