package com.roman14.springbootjpa.service;

import com.roman14.springbootjpa.entity.Delivery;
import com.roman14.springbootjpa.entity.Member;
import com.roman14.springbootjpa.entity.Order;
import com.roman14.springbootjpa.entity.OrderItem;
import com.roman14.springbootjpa.entity.item.Item;
import com.roman14.springbootjpa.repository.ItemRepository;
import com.roman14.springbootjpa.repository.MemberRepository;
import com.roman14.springbootjpa.repository.OrderRepository;
import com.roman14.springbootjpa.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService
{
  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;

  @Transactional
  public Long order(Long memberId, Long itemId, int count)
  {
    Member member = memberRepository.findOne(memberId);
    Item item = itemRepository.findOne(itemId);

    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());

    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

    Order order = Order.createOrder(member, delivery, orderItem);

    orderRepository.save(order);

    return order.getId();
  }

  @Transactional
  public void cancel(Long orderId)
  {
    Order order = orderRepository.findOne(orderId);

    order.cancel();
  }

  public Order findOne(Long orderId)
  {
    Order findOrder = orderRepository.findOne(orderId);

    return findOrder;
  }

  public List<Order> findOrders(OrderSearch orderSearch)
  {
    return orderRepository.findAllByString(orderSearch);
  }
}
