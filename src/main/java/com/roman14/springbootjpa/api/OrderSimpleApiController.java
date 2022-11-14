package com.roman14.springbootjpa.api;

import com.roman14.springbootjpa.api.response.CommonResponse;
import com.roman14.springbootjpa.entity.Address;
import com.roman14.springbootjpa.entity.Order;
import com.roman14.springbootjpa.repository.OrderRepository;
import com.roman14.springbootjpa.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Order
 * Order -> Member (ManyToOne)
 * Order -> Delivery (OneToOne)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/simple-orders")
public class OrderSimpleApiController
{
  private final OrderRepository orderRepository;

  @Deprecated
  @GetMapping("/v1")
  public List<Order> ordersV1()
  {
    // 재귀 참조 문제로 무한 루프가 발생한다.
    return orderRepository.findAllByString(new OrderSearch());
  }

  @GetMapping("/v2")
  public CommonResponse<List<SimpleOrdersResponseDto>> ordersV2()
  {
    List<Order> orderList = orderRepository.findAllByString(new OrderSearch());

    List<SimpleOrdersResponseDto> results = orderList.stream()
      .map(SimpleOrdersResponseDto::from)
      .collect(Collectors.toList());

    return CommonResponse.success(results);
  }

  @GetMapping("/v3")
  public CommonResponse<List<SimpleOrdersResponseDto>> ordersV3()
  {
    List<Order> orderList = orderRepository.findAllWithMemberDelivery();

    List<SimpleOrdersResponseDto> results = orderList.stream()
      .map(SimpleOrdersResponseDto::from)
      .collect(Collectors.toList());

    return CommonResponse.success(results);
  }

  @Data
  static class SimpleOrdersResponseDto {
    private Long orderId;
    private String name;
    private Address address;
    private LocalDateTime orderDate;

    public static SimpleOrdersResponseDto from(Order order)
    {
      SimpleOrdersResponseDto dto = new SimpleOrdersResponseDto();
      dto.setOrderId(order.getId());
      dto.setName(order.getMember().getName());
      dto.setAddress(order.getDelivery().getAddress());
      dto.setOrderDate(order.getOrderDate());
      return dto;
    }
  }
}
