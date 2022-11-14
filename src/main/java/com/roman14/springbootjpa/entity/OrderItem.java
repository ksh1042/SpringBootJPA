package com.roman14.springbootjpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roman14.springbootjpa.entity.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem
{
  @Id @GeneratedValue
  @Column(name = "order_item_id")
  private Long id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;
  
  /** 주문 당시 가격 */
  private int orderPrice;
  
  /** 주문 수량 */
  private int count;

  public static OrderItem createOrderItem(Item item, int orderPrice, int count)
  {
    OrderItem orderItem = new OrderItem();
    orderItem.setItem(item);
    orderItem.setOrderPrice(orderPrice);
    orderItem.setCount(count);

    // 실재고 감소
    item.minusStockQuantity(count);

    return orderItem;
  }

  public void cancel()
  {
    // 재고 수량 원복
    getItem().addStockQuantity(count);
  }

  public int getTotalPrice()
  {
    return this.orderPrice * this.count;
  }
}
