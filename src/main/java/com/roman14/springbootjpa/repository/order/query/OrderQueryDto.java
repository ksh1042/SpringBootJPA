package com.roman14.springbootjpa.repository.order.query;

import com.roman14.springbootjpa.entity.Address;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderQueryDto
{
  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private Address address;
  private List<OrderItemQueryDto> orderItems;

  public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, Address address)
  {
    this.orderId = orderId;
    this.name = name;
    this.orderDate = orderDate;
    this.address = address;
//    this.orderItems = orderItems;
  }

  public void setOrderItems(List<OrderItemQueryDto> orderItems)
  {
    this.orderItems = orderItems;
  }
}
