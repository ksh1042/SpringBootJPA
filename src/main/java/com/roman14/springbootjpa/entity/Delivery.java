package com.roman14.springbootjpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery
{
  @Id @GeneratedValue
  @Column(name = "delivery_id")
  private Long id;

  @OneToOne(mappedBy = "delivery")
  private Order order;

  @Embedded
  private Address address;

  public static Delivery from(Order order, Address address)
  {
    Delivery delivery = new Delivery();
    delivery.setOrder(order);
    delivery.setAddress(address);

    return delivery;
  }
  public static Delivery from(Address address)
  {
    Delivery delivery = new Delivery();
    delivery.setAddress(address);

    return delivery;
  }
}
