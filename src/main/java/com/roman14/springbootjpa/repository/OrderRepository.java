package com.roman14.springbootjpa.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.roman14.springbootjpa.entity.*;
import com.roman14.springbootjpa.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static com.roman14.springbootjpa.entity.QMember.member;
import static com.roman14.springbootjpa.entity.QOrder.order;

@Repository
@RequiredArgsConstructor
public class OrderRepository
{
  private final EntityManager em;

  public void save(Order order)
  {
    em.persist(order);
  }

  public Order findOne(Long id)
  {
    return em.find(Order.class, id);
  }

  public List<Order> findAllByString(OrderSearch orderSearch)
  {
    JPAQueryFactory query = new JPAQueryFactory(em);

    return query.query()
      .select(order)
      .from(order)
      .join(order.member, member)
      .where( orderStatusEq(orderSearch.getOrderStatus()), memberNameLike(orderSearch.getMemberName()) )
      .limit(1000)
      .fetch();
//    CriteriaBuilder cb = em.getCriteriaBuilder();
//    CriteriaQuery<Order> cq = cb.createQuery(Order.class);
//    Root<Order> o = cq.from(Order.class);
//    Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
//    List<Predicate> criteria = new ArrayList<>();
//    //주문 상태 검색
//    if (orderSearch.getOrderStatus() != null) {
//      Predicate status = cb.equal(o.get("status"),
//        orderSearch.getOrderStatus());
//      criteria.add(status);
//    }
//    //회원 이름 검색
//    if ( StringUtils.hasText(orderSearch.getMemberName())) {
//      Predicate name =
//        cb.like(m.<String>get("name"), "%" +
//          orderSearch.getMemberName() + "%");
//      criteria.add(name);
//    }
//    cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
//    TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
//    return query.getResultList();
  }

  private static BooleanExpression memberNameLike(String memberName)
  {
    if(memberName == null) return null;
    return member.name.like(memberName);
  }

  private static BooleanExpression orderStatusEq(OrderStatus status)
  {
    if(status == null) return null;
    return order.status.eq(status);
  }

  public List<Order> findAllWithMemberDelivery()
  {
    return em.createQuery("select o from Order o " +
        "join fetch o.member m " +
        "join fetch o.delivery d", Order.class)
      .getResultList();
  }

  public List<Order> findAllWithMemberDelivery(int offset, int limit)
  {
    List<Order> resultList = em.createQuery("select o from Order o " +
        "join fetch o.member m " +
        "join fetch o.delivery d", Order.class)
      .setFirstResult(offset)
      .setMaxResults(limit)
      .getResultList();

    return resultList;
  }

  public List<Order> findAllWithItems()
  {
    return em.createQuery("select distinct o from Order o " +
          "join fetch o.member m " +
          "join fetch o.delivery d " +
          "join fetch o.orderItems oi " +
          "join fetch oi.item i "
        , Order.class)
      .getResultList();
  }
}
