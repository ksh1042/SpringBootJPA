package com.roman14.springbootjpa.repository;

import com.roman14.springbootjpa.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch
{
  private String memberName;
  private OrderStatus orderStatus;
}
