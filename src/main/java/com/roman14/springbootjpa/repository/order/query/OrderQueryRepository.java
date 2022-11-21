package com.roman14.springbootjpa.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository
{
  private final EntityManager em;

  public List<OrderQueryDto> findOrderQueryDtos()
  {
    List<OrderQueryDto> orders = findOrders();

    orders.stream()
      .forEach(o -> {;
        o.setOrderItems( findOrderItems(o.getOrderId()) );
      });

    return orders;
  }

  private List<OrderItemQueryDto> findOrderItems(Long orderId)
  {
    return em.createQuery(
      "select new com.roman14.springbootjpa.repository.order.query.OrderItemQueryDto(o.id, i.name, oi.orderPrice, oi.count) " +
        "from OrderItem oi " +
        "join oi.order o " +
        "join oi.item i " +
        "where o.id = :orderId "
        , OrderItemQueryDto.class)
      .setParameter("orderId", orderId)
      .getResultList();
  }

  private List<OrderQueryDto> findOrders()
  {
    return em.createQuery(
        "select new com.roman14.springbootjpa.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, d.address) from Order o " +
          "join o.member m " +
          "join o.delivery d "
        , OrderQueryDto.class)
      .getResultList();
  }

  public List<OrderQueryDto> findAllByDtos()
  {
    List<OrderQueryDto> orders = findOrders();

    List<Long> orderIds = orders.stream()
      .map(o -> o.getOrderId())
      .collect(Collectors.toList());

    List<OrderItemQueryDto> orderItems = em.createQuery(
        "select new com.roman14.springbootjpa.repository.order.query.OrderItemQueryDto(o.id, i.name, oi.orderPrice, oi.count) " +
          "from OrderItem oi " +
          "join oi.order o " +
          "join oi.item i " +
          "where o.id in :orderIds "
        , OrderItemQueryDto.class)
      .setParameter("orderIds", orderIds)
      .getResultList();

    Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
      .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));

    orders.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

    return orders;
  }

  public List<OrderFlatDto> findAllByDtosFlat()
  {
    return em.createQuery(
      "select new com.roman14.springbootjpa.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, d.address, i.name, oi.orderPrice, oi.count) " +
        "from Order o " +
        "join o.member m " +
        "join o.delivery d " +
        "join o.orderItems oi " +
        "join oi.item i "
      , OrderFlatDto.class
    ).getResultList();
  }
}
