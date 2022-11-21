package com.roman14.springbootjpa.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemQueryDto
{
  @JsonIgnore
  private Long orderId;
  private String itemName;
  private int orderPrice;
  private int count;

  public OrderItemQueryDto(Long orderId, String itemName, int orderPrice, int count)
  {
    this.orderId = orderId;
    this.itemName = itemName;
    this.orderPrice = orderPrice;
    this.count = count;
  }
}
