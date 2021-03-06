package com.roman14.springbootjpa.entity;

import com.roman14.springbootjpa.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem
{
  @Id @GeneratedValue
  @Column(name = "order_item_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;
  
  /** 주문 당시 가격 */
  private int orderPrice;
  
  /** 주문 수량 */
  private int count;
}
