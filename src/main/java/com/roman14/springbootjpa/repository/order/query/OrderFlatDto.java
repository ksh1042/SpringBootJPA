package com.roman14.springbootjpa.repository.order.query;

import com.roman14.springbootjpa.entity.Address;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderFlatDto
{
  // Order
  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private Address address;

  // OrderItem
  private String itemName;
  private int orderPrice;
  private int count;

  public OrderFlatDto(Long orderId, String name, LocalDateTime orderDate, Address address, String itemName, int orderPrice, int count)
  {
    this.orderId = orderId;
    this.name = name;
    this.orderDate = orderDate;
    this.address = address;
    this.itemName = itemName;
    this.orderPrice = orderPrice;
    this.count = count;
  }
}
